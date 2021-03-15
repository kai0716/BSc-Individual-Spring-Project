package com.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.Answer;
import com.domain.Category;
import com.domain.Cheat;
import com.domain.Dto;
import com.domain.Exam;
import com.domain.JsonResponse;
import com.domain.Mark;
import com.domain.Module;
import com.domain.Option;
import com.domain.Question;
import com.domain.SelectedOption;
import com.domain.Users;
import com.repositories.AnswerRepo;
import com.repositories.CategoryRepo;
import com.repositories.CheatRepo;
import com.repositories.ExamRepo;
import com.repositories.MarkRepo;
import com.repositories.ModuleRepo;
import com.repositories.OptionRepo;
import com.repositories.QuestionRepo;
import com.repositories.UserRepo;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping("/teacher")
public class TeacherPageController {
	public static final long HOUR = 3600 * 1000; // in milli-seconds.
	@Autowired
	private QuestionRepo questionRepo;
	@Autowired
	private OptionRepo optionRepo;
	@Autowired
	private AnswerRepo answerRepo;
	@Autowired
	private ModuleRepo moduleRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ExamRepo examRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private CheatRepo cheatRepo;
	@Autowired
	private MarkRepo markRepo;

	@RequestMapping("/")
	public String openTeacherPage(@ModelAttribute("dto") Dto dto, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());

		List<Exam> examList = new ArrayList<>();

		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());
		model.addAttribute("ModuleList", users.getModuleList());
		return "teacher";
	}

	//Display average result
	@RequestMapping("/result{moduleCode}")
	public @ResponseBody Object ResultAjax(@PathVariable String moduleCode) {

		Module module = moduleRepo.findByModuleCode(moduleCode);

		JSONObject json = new JSONObject();
		JSONArray allDataArray = new JSONArray();

		for (Exam e : module.getExam()) {
			int studentNumber=0;
			double totalStudentMark = 0;
			e.getExamName();

			// Get total mark of an exam
			double totalExamMark = 0;
			for (Question q : e.getQuestionList()) {
				totalExamMark = totalExamMark + q.getQuestion_marks();
			}

			for (Mark m : e.getMarkList()) {
				totalStudentMark = totalStudentMark + (m.getMark() / totalExamMark);
				studentNumber= studentNumber+1;
			}
			json.put("exam", e.getExamName());
			json.put("mark", totalStudentMark / e.getMarkList().size() * 100);
			json.put("id", e.getExamID());
			json.put("studentNumber", studentNumber);
		}
		allDataArray.add(json);

		return allDataArray;
	}
	@RequestMapping("/release")
	public @ResponseBody String release(@RequestParam("num") String num, @RequestParam("examId") int examId) {
		String failed="Failed";
		int number = Integer.parseInt(num);
		Exam exam = examRepo.findByExamID(examId);
		if(number ==1) {
			exam.setRelease(1);
			examRepo.save(exam);
			return "Released";
		}
		return failed;
	}
	
	@RequestMapping(value = "/addQuestion", method = RequestMethod.GET)
	public String OpenAddQuestionPage(@ModelAttribute("AddQuestion") Dto dto, BindingResult result, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());

		List<String> module = new ArrayList<>();
		for (Module m : users.getModuleList()) {
			String moduleCode = m.getModuleCode();
			module.add(moduleCode);
		}

		model.addAttribute("Module", module);
		return "addQuestion";
	}

	@RequestMapping(value = "/addQuestion", params = "add", method = RequestMethod.POST)
	public String AddQuestion(@ModelAttribute("AddQuestion") Dto dto, BindingResult result, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());

		return "teacher";
	}

	@RequestMapping(value = "/question", method = RequestMethod.POST)
	public @ResponseBody String addNewModule(@ModelAttribute("addNewUser") Dto dto, BindingResult result, Model model) {
		String res = "Success";

		List<Question> qs = (List<Question>) questionRepo.findAll();
		for (Question q : qs) {
			if (q.getQuestion().equals(dto.getQuestion())) {
				return "QuestionExist";
			}
		}

		Question question = new Question();
		question.setQuestion(dto.getQuestion());

		// Split the string with the ",,," to read and get each option.
		List<String> optionList = Arrays.asList(dto.getChoice().split(",,,"));
		// Store these options into a List
		List<Option> newOptionList = new ArrayList<Option>();

		for (int i = 0; i < optionList.size(); i++) {

			Option options = new Option(question, optionList.get(i));
			// add all the options into newOptionList
			newOptionList.add(options);
		}

		List<String> answerList = Arrays.asList(dto.getAnswer().split(",,,"));
		List<Answer> newAnswerList = new ArrayList<Answer>();
		for (int i = 0; i < answerList.size(); i++) {

			Answer answer = new Answer(question, answerList.get(i));
			// add all the answer into newAnswerList
			newAnswerList.add(answer);
			question.setAnswer(newAnswerList);
		}
		question.setQuestion_marks(dto.getMark());
		for (Module m : moduleRepo.findAll()) {
			if (m.getModuleCode().equals(dto.getModule())) {
				question.setModule(m);
			}
		}
		questionRepo.save(question);
		optionRepo.save(newOptionList);
		answerRepo.save(newAnswerList);
		return res;
	}
//-----------------------------------------------------------Teacher Add/ Modify Exam--------------------------------------------------------------------------	
	//find all exam belong to teacher
	//load intial exam modify page
	@RequestMapping(value = "/Exam", method = RequestMethod.GET)
	public String Exam( @ModelAttribute("dto") Dto dto, Model model) {
		List<Module> modules = new ArrayList<>();
		List<Exam> exams = new ArrayList<>();
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName()+" "+ users.getLastName());
		
		for(Module m: users.getModuleList()) {
			//find all the module that have exam
			if(m.getExam().size()>0) {
				modules.add(m);
				
				//get all exam
				for(Exam e: m.getExam()) {
					exams.add(e);
					
					//Auto open and close exam
					Date current_date = new Date();
					Date start_date = e.getStartTime();
					Date end_date = new Date(start_date.getTime() + HOUR); // +e.getDuration
					if(current_date.before(start_date)) {
						e.setStatus(0);
					}
					if(current_date.after(start_date)) {
						e.setStatus(1);
					}
					if(current_date.after(end_date)) {
						e.setStatus(0); 
					}

				}
			}
		}
		model.addAttribute("Modules", modules);
		model.addAttribute("Exams", exams);
		
		return "teacherExamModiPage";
	}
//---------------------------------Cheating function------------------------------------------	
	@RequestMapping(value = "/Exam/ExamList", method = RequestMethod.GET)
	public String Exam_List(@ModelAttribute("dto") Dto dto, Model model) {
		List<Module> modules = new ArrayList<>();
		List<Exam> exams = new ArrayList<>();

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());

		for (Module m : users.getModuleList()) {
			// find all the module that have exam
			if (m.getExam().size() > 0) {
				modules.add(m);

				// get all exam
				for (Exam e : m.getExam()) {
					exams.add(e);

					// Auto open and close exam
					Date current_date = new Date();
					Date start_date = e.getStartTime();
					Date end_date = new Date(start_date.getTime() + HOUR); // +e.getDuration
					if (current_date.before(start_date)) {
						e.setStatus(0);
					}
					if (current_date.after(start_date)) {
						e.setStatus(1);
					}
					if (current_date.after(end_date)) {
						e.setStatus(0);
					}

				}
			}
		}
		model.addAttribute("Modules", modules);
		model.addAttribute("Exams", exams);

		return "viewCheat";
	}

	@RequestMapping(value = "/Exam/ExamList/viewCheat", method = RequestMethod.POST)
	public String cheatInfo(@ModelAttribute("dto") Dto dto, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());

		Exam exams = examRepo.findByExamID(dto.getExam());

		model.addAttribute("CheatList", exams.getCheatList());
		model.addAttribute("Exam", exams);

		return "viewCheat_cheatList";
	}

	@RequestMapping(value = "/Exam/ExamList/viewCheat/Punish", method = RequestMethod.POST)
	public String Punish(@ModelAttribute("dto") Dto dto, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());

		Users student = userRepo.findByEmail(dto.getEmail());
		for (Exam e : student.getExamList()) {
			if (e.getExamID() == dto.getExam()) {

				for (Mark m : e.getMarkList()) {
					if (m.getUser_Id().getEmail().equals(dto.getEmail())) {
						m.setMark(0);
						markRepo.save(m);
					}
				}
			}
		}

		return "viewCheat_cheatList";
	}
	
//------------------------------Add and Delete Exam----------------------------------------------
	@RequestMapping(value = "/Exam/addExam", method = RequestMethod.GET)
	public String Load_AddExam_page(@ModelAttribute("dto") Dto dto, BindingResult result, Model model) {

		List<Module> modules = new ArrayList<>();
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());

		for (Module m : users.getModuleList()) {
			modules.add(m);
		}

		model.addAttribute("Modules", modules);

		return "addExam";
	}

	@RequestMapping(value = "/Exam/addExam", params = "add", method = RequestMethod.POST)
	public String AddExam_Add(@ModelAttribute("dto") Dto dto, BindingResult result, Model model) {

		Exam exam = new Exam();

		Module module = moduleRepo.findByModuleCode(dto.getModule());

		exam.setExamName(dto.getExamName());
		exam.setDuration(dto.getDuration());
		exam.setStartTime(dto.getStartTime());
		exam.setModule(module);

		module.getExam().add(exam);
		examRepo.save(exam);
		moduleRepo.save(module);

		return "redirect:/teacher/Exam/ModifyExamQuestion?exam=" + examRepo.count();
	}

	@RequestMapping(value = "/Exam/status", method = RequestMethod.POST)
	public String Check_Status(@ModelAttribute("dto") Dto dto, Model model) throws ParseException {
		Exam exam = examRepo.findByExamID(dto.getExam());

		if (dto.getOpen_close().equals("open")) {
			// 0= close 1= open
			exam.setStatus(0);
		} else {
			// 0= close 1= open
			exam.setStatus(1);
			Date startTime = new Date();
			exam.setStartTime(startTime);
		}
		examRepo.save(exam);

		List<Module> modules = new ArrayList<>();
		List<Exam> exams = new ArrayList<>();

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());

		for (Module m : users.getModuleList()) {
			// find all the module that have exam
			if (m.getExam().size() > 0) {
				modules.add(m);

				// get all exam
				for (Exam e : m.getExam()) {
					exams.add(e);
				}
			}
		}

		model.addAttribute("Modules", modules);
		model.addAttribute("Exams", exams);
		return "teacherExamModiPage";
	}
//----------------------------	Modify Exam Question----------------------------------------------
	@RequestMapping(value = "/Exam/ModifyExamQuestion", method = RequestMethod.GET)
	public String Modify_Question(@ModelAttribute("dto") Dto dto, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());

		List<Question> exist_question = new ArrayList<>();

		Exam exam = examRepo.findByExamID(dto.getExam());
		Module module = exam.getModule();
		for (Question q : exam.getQuestionList()) {
			exist_question.add(q);
		}
		model.addAttribute("Exam_Question_List", exist_question);

		List<Question> question_for_adding = module.getQuestionList();
		question_for_adding.removeAll(exist_question);

		model.addAttribute("All_Question", question_for_adding);
		model.addAttribute("Exam", exam);
		return "modifyExamQuestion";
	}

	@RequestMapping(value = "/Exam/ModifyExamQuestion/Add", method = RequestMethod.POST)
	public String Add_Question_To_Exam(@ModelAttribute("dto") Dto dto, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());

		// get the selected question that want to add to the exam
		List<String> question_List = Arrays.asList(dto.getExam_Qestion_List().split(","));
		List<Question> selected_Question = new ArrayList<>();
		List<Question> question_in_db = (List<Question>) questionRepo.findAll();
		for (String s : question_List) {
			for (Question q : question_in_db) {
				// get the question q that match the question s selected by user
				if (q.getQuestion().equals(s)) {
					selected_Question.add(q);
					q.setUsage(q.getUsage() + 1);
				}
			}
		}
		// add these question to the exam
		Exam exam = examRepo.findByExamID(dto.getExam());
		List<Question> current_Questions = exam.getQuestionList();
		List<Question> questionList = new ArrayList<>();
		current_Questions.removeAll(selected_Question);
		current_Questions.addAll(selected_Question);
		exam.setQuestionList(current_Questions);

		//
		for (Question q : exam.getQuestionList()) {
			questionList.add(q);
		}

		exam.setQuestionList(questionList);
		System.out.println(questionList.size() + "A");
		examRepo.save(exam);
		model.addAttribute("Exam", exam);
		model.addAttribute("Exam_Question_List", questionList);
		return "modifyExamQuestion";
	}

	@RequestMapping(value = "/Exam/ModifyExamQuestion/Delete", method = RequestMethod.POST)
	public String Delete_Question_In_Exam(@ModelAttribute("dto") Dto dto, Model model) {	
		//-----------Code for Delete---------------------
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());

		Exam exam = examRepo.findByExamID(dto.getExam());
		Question toRemove = new Question();
		for (Question q : exam.getQuestionList()) {
			if (dto.getQuestionId_for_delete() - q.getQuestionID() == 0) {
				toRemove = q;
			}
		}
		exam.getQuestionList().remove(toRemove);
		examRepo.save(exam);
		System.out.println(exam.getQuestionList().size() + "B");
		model.addAttribute("Exam_Question_List", exam.getQuestionList());
		
		// --------------Get question for adding------------
		List<Question> exist_question = new ArrayList<>();
		exam = examRepo.findByExamID(dto.getExam());

		for (Question q : exam.getQuestionList()) {
			exist_question.add(q);
		}
		Module module = exam.getModule();
		List<Question> question_for_adding = module.getQuestionList();
		question_for_adding.removeAll(exist_question);

		model.addAttribute("All_Question", question_for_adding);

		model.addAttribute("Exam", exam);

		return "modifyExamQuestion";
	}

	@RequestMapping(value = "/Exam/ModifyExamQuestion/Edit", method = RequestMethod.POST)
	public String Edit_Exam_Info(@ModelAttribute("dto") Dto dto, Model model) {
		List<Module> modules = new ArrayList<>();
		List<Exam> exams = new ArrayList<>();
		
		//--------------------Code for edit-----------------------------------
		System.out.println(dto.getExam());
		Exam exam = examRepo.findByExamID(dto.getExam());

		exam.setExamName(dto.getExamName());
		exam.setDuration(dto.getDuration());
		if(dto.getStartTime()!=null) {
		exam.setStartTime(dto.getStartTime());
		}
		examRepo.save(exam);
		
		
		//--------------------code for loading teacherExamModiPage data-------------------------------------
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());

		for (Module m : users.getModuleList()) {
			// find all the module that have exam
			if (m.getExam().size() > 0) {
				modules.add(m);

				// get all exam
				for (Exam e : m.getExam()) {
					exams.add(e);

					// Auto open and close exam
					Date current_date = new Date();
					Date start_date = e.getStartTime();
					Date end_date = new Date(start_date.getTime() + HOUR); // +e.getDuration
					if (current_date.before(start_date)) {
						e.setStatus(0);
					}
					if (current_date.after(start_date)) {
						e.setStatus(1);
					}
					if (current_date.after(end_date)) {
						e.setStatus(0);
					}
				}
			}
		}
		model.addAttribute("Modules", modules);
		model.addAttribute("Exams", exams);

		return "teacherExamModiPage";
	}

	@RequestMapping(value = "/Exam/ModifyExamQuestion/Edit{examID}", method = RequestMethod.GET)
	public @ResponseBody JSONObject edit(@PathVariable String examID) {
		JSONObject json = new JSONObject();
		Exam exam = examRepo.findByExamID(Integer.valueOf(examID));

		json.put("examID", exam.getExamID());
		json.put("examName", exam.getExamName());
		json.put("examDuration", exam.getDuration());
		return json;
	}

	@RequestMapping(value = "/Exam/ModifyExamQuestion/Category", method = RequestMethod.GET)
	public @ResponseBody List<String> category(@RequestParam("search") String category_message, @RequestParam("examId") int examID) {
		System.out.println(examID + "GG");
		List<String> questionList_inString = new ArrayList<>();
		Category category = categoryRepo.findByCategoryName(category_message);

		if (categoryRepo.findByCategoryName(category_message) != null) {
			List<Question> questionList = category.getQuestionList();

			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Users users = userRepo.findByEmail(user.getUsername());
			String moudleCode = new String();
			for (Module m : users.getModuleList()) {
				// find all the module that have exam
				for (Exam e : m.getExam()) {
					if (e.getExamID() == examID) {
						moudleCode = m.getModuleCode();
					}
				}
			}

			for (Question q : questionList) {
				String question = q.getQuestion();
				if (q.getModule().getModuleCode() == moudleCode) {
					questionList_inString.add(question);
				}
			}

		}
		return questionList_inString;
	}
/** ---------------------------------------- View Student Result in Teacher page---------------------------------------------------    **/
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public String ViewStudent(@ModelAttribute("dto") Dto dto, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());

		Set<Users> studentList = new HashSet<Users>();
		List<Module> moduleList = users.getModuleList();

		// teacher moduleList
		for (Module teacherModule : moduleList) {
			for (Users u : teacherModule.getUserList()) {
				// 2 = student
				if (u.getRole().getId() == 2) {
					studentList.add(u);
				}
			}
		}
		List<Users> studentArrayList = new ArrayList<Users>(studentList);
		model.addAttribute("Student", studentArrayList);

		return "teacher_student";
	}

	@RequestMapping(value = "/student/exam", method = RequestMethod.POST)
	public String ViewStudentExam(@ModelAttribute("dto") Dto dto, Model model) {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(currentUser.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());

		Users user = userRepo.findByEmail(dto.getEmail());
		if (user.getExamList().size() != 0) {
			List<Exam> examList = user.getExamList();
			model.addAttribute("ExamList", examList);
		}
		model.addAttribute("UserId", user.getUserId());
		model.addAttribute("Email", dto.getEmail());
		String studentName = user.getFirstName() + " " + user.getLastName();
		model.addAttribute("Name", studentName);
		return "teacher_exam";
	}

	@RequestMapping(value = "/student/exam/result", method = RequestMethod.POST)
	public String ViewStudentExamResult(@ModelAttribute("dto") Dto dto, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());

		Exam exam = examRepo.findByExamID(dto.getExam());
		Users student = userRepo.findByEmail(dto.getEmail());

		for (Mark m : exam.getMarkList()) {
			if (m.getUser_Id().equals(student)) {
				m.getMark();
				model.addAttribute("MarkGet", m.getMark());
			}
		}
		int totalMark = 0;
		for (Question q : exam.getQuestionList()) {
			totalMark = totalMark + q.getQuestion_marks();
		}
		model.addAttribute("QuestionList", exam.getQuestionList());
		model.addAttribute("TotalMark", totalMark);
		model.addAttribute("Name", dto.getUserName());
		return "teacher_student_result";
	}

	@RequestMapping(value = "/module", method = RequestMethod.POST)
	public String module_overallResult(@ModelAttribute("dto") Dto dto, Model model) {

		return "teacher_module";
	}
}
