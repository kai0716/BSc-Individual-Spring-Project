package com.controllers;

import static org.mockito.Matchers.intThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.Answer;
import com.domain.Dto;
import com.domain.Exam;
import com.domain.Mark;
import com.domain.Module;
import com.domain.Option;
import com.domain.Question;
import com.domain.SelectedOption;
import com.domain.Users;
import com.mysql.jdbc.Security;
import com.repositories.AnswerRepo;
import com.repositories.ExamRepo;
import com.repositories.MarkRepo;
import com.repositories.ModuleRepo;
import com.repositories.OptionRepo;
import com.repositories.QuestionRepo;
import com.repositories.SelectedOptionRepo;
import com.repositories.UserRepo;

@Controller
@RequestMapping("/")
public class ExamController {
	@Autowired
	private QuestionRepo questionRepo;
	@Autowired
	private OptionRepo optionRepo;
	@Autowired
	private AnswerRepo answerRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private SelectedOptionRepo soRepo;
	@Autowired
	private ExamRepo examRepo;
	@Autowired
	private ModuleRepo moduleRepo;
	@Autowired
	private MarkRepo markRepo;

	private List<Answer> answerList;
	private List<Question> questionList;
	private Module module;
	private int count = 0;
	private List<SelectedOption> selectedOption = new ArrayList<>(); // selectedOption contain all the selected option
																		// from all question
	// ---------------------------------------------------------Student Exam
	// Controller-------------------------------------------------------

	@RequestMapping(value = "/student/Exam/start", method = RequestMethod.GET)
	public String loadExamPage(@ModelAttribute("dto") Dto dto, Model model) {
		// find current user
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		Exam exam = examRepo.findByExamID(dto.getExam());
		model.addAttribute("Module", exam.getModule().getModuleCode());
		model.addAttribute("ExamName", exam.getExamName());

		if (!users.getExamList().contains(exam)) {
			users.getExamList().add(exam);
			userRepo.save(users);
		}
		// in the first run, questionList is empty, second run is not empty// reset for
		// second run
		// forget all the selected option, so when user retake exam, they cannot see the
		// checked option
		if (questionList != null) {
			selectedOption = new ArrayList<>();
			questionList = exam.getQuestionList();

			for (Question q : questionList) {
				List<SelectedOption> soList = new ArrayList<>();
				for (SelectedOption so : q.getSelectedChoiceList()) {
					if (!so.getUser().equals(users) && so.getExam().getExamID() != dto.getExam()) {
						soList.add(so);
					}
				}
				for (SelectedOption so : q.getSelectedChoiceList()) {
					if (so.getUser().equals(users) && so.getExam().getExamID() == dto.getExam()) {
						q.setSelectedChoiceList(soList);
					}
				}
			}
			questionRepo.save(questionList);

			// ----------------------------Remove all the selected option of all question
			// from database when user retake the exam----------------------------
			List<SelectedOption> selectedOption_database = (List<SelectedOption>) soRepo.findAll();

			List<SelectedOption> toRemove = new ArrayList<>();

			for (SelectedOption so : selectedOption_database) {
				if (so.getUser().equals(users) && so.getExam().getExamID() == dto.getExam()) {
					toRemove.add(so);
				}
			}

			users.getSelectedChoiceList().removeAll(toRemove);
			exam.getSelectedOptionList().removeAll(toRemove);
			userRepo.save(users);
			examRepo.save(exam);
			soRepo.delete(toRemove);
		}
		// ----------------------------Display exam question--------------------------

		for (Module m : users.getModuleList()) {
			if (m.getModuleCode().equals(dto.getModule())) {
				module = m;
			}
		}

		questionList = exam.getQuestionList();

		count = 0;

		Collections.shuffle(questionList);

		Question questions = questionList.get(0);
		String currentQuestion = questions.getQuestion();
		int answer = questions.getAnswer().size();

		model.addAttribute("Question", currentQuestion);
		model.addAttribute("AnswerSize", answer);
		model.addAttribute("QuestionNumber", (questionList.indexOf(questions) + 1) + "/" + questionList.size());
		model.addAttribute("Previous", (questionList.indexOf(questions) + 1));

		// get the corresponding options to the question
		List<Option> options = questions.getChoiceList();
		Collections.shuffle(options);
		model.addAttribute("OptionList", options);

		model.addAttribute("Exam", exam);
		int examdur = (int) (exam.getDuration() * 3600);
		model.addAttribute("Duration", examdur);

		// Send user detail
		model.addAttribute("User", users);

		count++;
		return "startExam";
	}

	@RequestMapping(value = "/student/Exam/start", params = "next", method = RequestMethod.POST)
	public String NextQuestion(@ModelAttribute("dto") Dto dto, Model model) {
		List<String> optionString = new ArrayList<>();
		// find the answered question and its option, so can be saved
		Question answeredQuestion = questionList.get(count - 1);
		List<SelectedOption> toRemove = new ArrayList<>();

		for (SelectedOption so : selectedOption) {
			if (so.getQuestionID() == answeredQuestion) {
				toRemove.add(so);
			}
		}
		selectedOption.removeAll(toRemove);

		if (dto.getOptionList() != null) {
			optionString = Arrays.asList(dto.getOptionList().split(","));

			for (String s : optionString) {
				SelectedOption option = new SelectedOption();
				option.setChoice(s);
				option.setQuestionID(answeredQuestion);
				selectedOption.add(option);
			}
		}
		// save selected option to the current question
		List<SelectedOption> selectedOption_currentQuestion = new ArrayList<>();
		for (int i = 0; i < selectedOption.size(); i++) {
			if (selectedOption.get(i).getQuestionID() == answeredQuestion) {
				selectedOption_currentQuestion.add(selectedOption.get(i));
			}
		}
		answeredQuestion.setSelectedChoiceList(selectedOption_currentQuestion);
		questionRepo.save(answeredQuestion);

		// --------------------------------------'Next'
		// function--------------------------------------
		Question questions = questionList.get(count);
		String nextQuestion = questions.getQuestion();
		model.addAttribute("Question", nextQuestion);
		model.addAttribute("QuestionNumber", (questionList.indexOf(questions) + 1) + "/" + questionList.size());
		model.addAttribute("Previous", (questionList.indexOf(questions) + 1));

		int answer = questions.getAnswer().size();
		model.addAttribute("AnswerSize", answer);

		// get the corresponding options to the question
		List<Option> options = questions.getChoiceList();

		if (selectedOption_currentQuestion.isEmpty()) {
			Collections.shuffle(options);
		}
		model.addAttribute("OptionList", options);

		// get the selected option of the next question and send to jsp
		List<String> soStringList = new ArrayList<>();
		for (SelectedOption so : questions.getSelectedChoiceList()) {
			String selectedChioce = so.getChoice();
			soStringList.add(selectedChioce);
		}
		model.addAttribute("SOList", soStringList);

		count++;
		try {
			questionList.get(count);

		} catch (IndexOutOfBoundsException e) {
			List<SelectedOption> toRemove1 = new ArrayList<>();
			for (SelectedOption so : selectedOption) {
				if (so.getQuestionID() == answeredQuestion) {
					toRemove1.add(so);
				}
			}
			selectedOption.removeAll(toRemove);

			// send exam ID back
			Exam exam = examRepo.findByExamID(dto.getExam());
			model.addAttribute("Exam", exam);

			// send user detail
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Users users = userRepo.findByEmail(user.getUsername());
			model.addAttribute("User", users);

			model.addAttribute("Submit", "submit");
			model.addAttribute("Duration", dto.getDuration());

			model.addAttribute("Module", exam.getModule().getModuleCode());
			model.addAttribute("ExamName", exam.getExamName());

			return "startExam";
		}
		// send time left
		model.addAttribute("Duration", dto.getDuration());

		// send user detail
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users);

		// send exam ID back
		Exam exam = examRepo.findByExamID(dto.getExam());
		model.addAttribute("Exam", exam);
		return "startExam";
	}

	@RequestMapping(value = "/student/Exam/start", params = "previous", method = RequestMethod.POST)
	public String PreviousQuestion(@ModelAttribute("dto") Dto dto, Model model) {
		List<String> optionString = new ArrayList<>();
		// find the answered question and its option, so can be saved
		Question answeredQuestion = questionList.get(count - 1);

		List<SelectedOption> toRemove = new ArrayList<>();

		for (SelectedOption so : selectedOption) {
			if (so.getQuestionID() == answeredQuestion) {
				toRemove.add(so);
			}
		}
		selectedOption.removeAll(toRemove);

		if (dto.getOptionList() != null) {
			optionString = Arrays.asList(dto.getOptionList().split(","));

			for (String s : optionString) {
				SelectedOption option = new SelectedOption();
				option.setChoice(s);
				option.setQuestionID(answeredQuestion);
				// selectedOption contain all the selected option from all question
				selectedOption.add(option);
			}
		}
		// save selected option to the current question
		List<SelectedOption> selectedOption_currentQuestion = new ArrayList<>();
		for (int i = 0; i < selectedOption.size(); i++) {
			if (selectedOption.get(i).getQuestionID() == answeredQuestion) {
				selectedOption_currentQuestion.add(selectedOption.get(i));
			}
		}
		answeredQuestion.setSelectedChoiceList(selectedOption_currentQuestion);
		questionRepo.save(answeredQuestion);

		// 'Previous' function
		Question questions = questionList.get(count - 2);
		String currentQuestion = questions.getQuestion();
		model.addAttribute("Question", currentQuestion);
		model.addAttribute("QuestionNumber", (questionList.indexOf(questions) + 1) + "/" + questionList.size());
		model.addAttribute("Previous", (questionList.indexOf(questions) + 1));

		int answer = questions.getAnswer().size();
		model.addAttribute("AnswerSize", answer);

		// get the corresponding options to the question
		List<Option> options = questions.getChoiceList();

		model.addAttribute("OptionList", options);
		// back to previous question index
		count--;

		// get the selected option of the previous question and send to jsp
		List<String> soStringList = new ArrayList<>();
		for (SelectedOption so : questions.getSelectedChoiceList()) {
			String selectedChioce = so.getChoice();
			soStringList.add(selectedChioce);
		}
		// send time left
		model.addAttribute("Duration", dto.getDuration());

		model.addAttribute("SOList", soStringList);

		// send user detail
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users);

		// send exam ID back
		Exam exam = examRepo.findByExamID(dto.getExam());
		model.addAttribute("Exam", exam);
		model.addAttribute("Module", exam.getModule().getModuleCode());
		model.addAttribute("ExamName", exam.getExamName());

		return "startExam";

	}

	@RequestMapping(value = "/student/Exam/start", params = "submit", method = RequestMethod.POST)
	public String SubmitQuestion(@ModelAttribute("dto") Dto dto, Model model) {
		Question answeredQuestion = questionList.get(questionList.size() - 1);

		List<String> optionString = new ArrayList<>();

		List<SelectedOption> toRemove = new ArrayList<>();

		for (SelectedOption so : selectedOption) {
			if (so.getQuestionID() == answeredQuestion) {
				toRemove.add(so);
			}
		}
		selectedOption.removeAll(toRemove);

		// save the last selected option // the last option will not save in the 'next'
		// function as the 'next' button changed to 'submit'
		if (dto.getOptionList() != null) {
			optionString = Arrays.asList(dto.getOptionList().split(","));
		}

		for (String s : optionString) {
			SelectedOption option = new SelectedOption();
			option.setChoice(s);
			option.setQuestionID(answeredQuestion);
			selectedOption.add(option);
		}
		// save selected option to the current question
		List<SelectedOption> selectedOption_currentQuestion = new ArrayList<>();
		for (int i = 0; i < selectedOption.size(); i++) {
			if (selectedOption.get(i).getQuestionID() == answeredQuestion) {
				selectedOption_currentQuestion.add(selectedOption.get(i));
			}
		}
		answeredQuestion.setSelectedChoiceList(selectedOption_currentQuestion);
		questionRepo.save(answeredQuestion);

		// find current user
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());

		double mark = 0;

		// go through each question
		for (Question q : questionList) {
			List<String> solist = new ArrayList<>();
			List<String> answerList = new ArrayList<>();

			// find the answers of each question
			for (Answer a : q.getAnswer()) {
				String answerString = a.getAnswer();
				answerList.add(answerString);
			}

			double mark_added = 0;
			// find all the selected options of each question
			for (SelectedOption so : q.getSelectedChoiceList()) {
				String choiceString = so.getChoice();
				solist.add(choiceString);

				if (answerList.contains(choiceString)) {
					double mark_of_each_option = q.getQuestion_marks() / answerList.size();
					mark = mark + mark_of_each_option;

					mark_added = mark_added + mark_of_each_option;
				}
			}
			// if selected option is more than answer do not give mark for this quesiton
			if (solist.size() > answerList.size()) {
				mark = mark - mark_added;
			}
		}
		// save to repositories
		Exam exam = examRepo.findByExamID(dto.getExam());
		model.addAttribute("Exam", exam);

		for (SelectedOption so : selectedOption) {
			so.setExam(exam);
			so.setUser(users);
		}

		List<Mark> markList = (List<Mark>) markRepo.findAll();

		if (markList.size() == 0) {
			Mark student_mark = new Mark();

			student_mark.setExam_Id(exam);
			student_mark.setUser_Id(users);
			student_mark.setMark(mark);
			model.addAttribute("Mark", mark);

			users.getMarkList().add(student_mark);
			exam.getMarkList().add(student_mark);

			users.setSelectedChoiceList(selectedOption);
			exam.setSelectedOptionList(selectedOption);

		}
		for (Mark m : markList) {

			if (m.getExam_Id().getExamID() == dto.getExam() && m.getUser_Id().getUserId() == users.getUserId()) {
				m.setMark(mark);
				markRepo.save(m);
				model.addAttribute("Mark", mark);
				// mark and selected option will be save at the same time

			} else {
				Mark student_mark = new Mark();

				student_mark.setExam_Id(exam);
				student_mark.setUser_Id(users);
				student_mark.setMark(mark);
				model.addAttribute("Mark", mark);

				users.getMarkList().add(student_mark);
				exam.getMarkList().add(student_mark);

				users.setSelectedChoiceList(selectedOption);
				exam.setSelectedOptionList(selectedOption);
			}
		}

		// save all selected Option
		soRepo.save(selectedOption);
		userRepo.save(users);
		examRepo.save(exam);

		model.addAttribute("Mark", mark);
		return "examComplete";
	}

	// ---------------------------------------------------------function------------------------------------------------------
}