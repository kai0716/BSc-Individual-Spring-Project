package com.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.domain.Answer;
import com.domain.Dto;
import com.domain.Exam;
import com.domain.Module;
import com.domain.Option;
import com.domain.Question;
import com.domain.Users;
import com.repositories.AnswerRepo;
import com.repositories.ExamRepo;
import com.repositories.ModuleRepo;
import com.repositories.OptionRepo;
import com.repositories.QuestionRepo;
import com.repositories.UserRepo;

@Controller
@RequestMapping("/teacher")
public class EditDBQuestionController {
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

	//-----------------------------Modify Database question---------------------------------------------------	
		@RequestMapping(value = "/Exam/ModifyDatabaseQuestion", method = RequestMethod.GET)
		public String load_Modify_Database_Question_page( @ModelAttribute("dto") Dto dto, Model model) {
			
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Users users = userRepo.findByEmail(user.getUsername());
			List <Question> question_in_db = (List<Question>) questionRepo.findAll();
			List <Question> question = new ArrayList<>();
			
			for(Question q: question_in_db) {
				for(Module m: users.getModuleList()) {
					String moduleCode =m.getModuleCode();
					if(q.getModule().getModuleCode().equals(moduleCode)) {
						question.add(q);
					}
				}
			}
			model.addAttribute("Teacher_Question", question);
			return "modifyDatabaseQuestion";
		}
		@RequestMapping(value = "/Exam/ModifyDatabaseQuestion/Edit", method = RequestMethod.POST)
		public String Modify_Database_Question_Edit( @ModelAttribute("dto") Dto dto, Model model) {
			
			List <Question> question_in_db = (List<Question>) questionRepo.findAll();
			for(Question q: question_in_db) {
				if(dto.getQuestionId() -q.getQuestionID() ==0) {
					String Question = q.getQuestion();
					List <Option> optionList = q.getChoiceList();
					List <Answer> answerList = q.getAnswer();
				}
			}
			return "modifyDatabaseQuestion";
		}
		
		
		//------------------------------------Delete------------------------------------
//		@RequestMapping(value = "/Exam/ModifyDatabaseQuestion/Delete", method = RequestMethod.POST)
//		public String Delete_Question_In_Exam( @ModelAttribute("dto") Dto dto, Model model) {		
//			//-----------Code for Delete---------------------
//			Question toRemove = new Question();
//			List<Question> repoQuestion = (List<Question>) questionRepo.findAll();
//			Question question =   questionRepo.findByQuestionID(dto.getQuestionId_for_delete());
//			question.getChoiceList().clear();//remove choice 
//			question.getAnswer().clear();//remove answer 
//			question.getExamList().clear();
//			questionRepo.delete(toRemove);
//			
//			
//			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			Users users = userRepo.findByEmail(user.getUsername());
//			List <Question> question_in_db = (List<Question>) questionRepo.findAll();
//			List <Question> allQuestion = new ArrayList<>();
//			
//			for(Question q: question_in_db) {
//				for(Module m: users.getModuleList()) {
//					String moduleCode =m.getModuleCode();
//					if(q.getModule().getModuleCode().equals(moduleCode)) {
//						allQuestion.add(q);
//					}
//				}
//			}
//			model.addAttribute("Teacher_Question", allQuestion);
//			
//			return "modifyExamQuestion";
//		}	
}
