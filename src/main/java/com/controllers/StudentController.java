package com.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.domain.Cheat;
import com.domain.Dto;
import com.domain.Module;
import com.domain.Question;
import com.domain.Users;
import com.domain.Exam;
import com.domain.Mark;
import com.repositories.CheatRepo;
import com.repositories.ExamRepo;
import com.repositories.ModuleRepo;
import com.repositories.UserRepo;

@Controller
@RequestMapping("/student")
public class StudentController {
	public static final long HOUR = 3600 * 1000; // in milli-seconds.

	@Autowired
	private ExamRepo examRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CheatRepo cheatRepo;

	@RequestMapping("/")
	public String openStudentPage(@ModelAttribute("dto") Dto dto, Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());
		return "student";
	}

	@RequestMapping(value = "/Exam", method = RequestMethod.GET)
	public String Exam(@ModelAttribute("dto") Dto dto, Model model) {
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

					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date current_date = new Date();
					Date start_date = e.getStartTime();
					Date end_date = new Date(start_date.getTime() + HOUR * 200); // +e.getDuration
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
		return "exam";
	}

	@RequestMapping(value = "/Exam/ExamList/saveCheat", method = RequestMethod.POST)
	public String cheatInfo(@ModelAttribute("dto") Dto dto, Model model) throws ParseException {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());

		Exam exams = examRepo.findByExamID(dto.getExam());
		if (exams.getCheatList().size() == 0) {
			Cheat cheat = new Cheat();

			long jsDate = Long.parseLong(dto.getCheatDate());
			Date javaDate = new Date(jsDate);

			System.out.println(dto.getEmail());
			cheat.setCheatTime(javaDate);
			cheat.setUserEmail(dto.getEmail());
			cheat.setUserName(dto.getUserName());
			cheat.setExam(exams);
			cheat.setCheatCount(dto.getCount());
			cheatRepo.save(cheat);

			exams.getCheatList().add(cheat);
			examRepo.save(exams);

			model.addAttribute("CheatList", exams.getCheatList());
		} else {
			for (Cheat c : exams.getCheatList()) {
				if (c.getUserEmail().equals(dto.getEmail()) && c.getExam().getExamID() == dto.getExam()) {
					c.setCheatCount(dto.getCount());
					long jsDate = Long.parseLong(dto.getCheatDate());
					Date javaDate = new Date(jsDate);
					c.setCheatTime(javaDate);
					cheatRepo.save(c);
				}
			}
			model.addAttribute("CheatList", exams.getCheatList());
		}

		model.addAttribute("CheatList", exams.getCheatList());

		// data is from Ajax, so will not return to any page//
		return "addusererror";
	}

	@RequestMapping(value = "/ExamList", method = RequestMethod.GET)
	public String ExamList(@ModelAttribute("dto") Dto dto, Model model) throws ParseException {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());

		model.addAttribute("ExamList", users.getExamList());
		model.addAttribute("UserId", users.getUserId());
		return "student_examList";
	}

	@RequestMapping(value = "/ExamList/Result", method = RequestMethod.POST)
	public String Result(@ModelAttribute("dto") Dto dto, Model model) throws ParseException {
		Exam exam = examRepo.findByExamID(dto.getExam());

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName() + " " + users.getLastName());

		for (Mark m : exam.getMarkList()) {
			if (m.getUser_Id().equals(users)) {
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
		return "student_result";
	}

}
