package com;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.domain.*;
import com.repositories.AnswerRepo;
import com.repositories.CategoryRepo;
import com.repositories.ExamRepo;
import com.repositories.ModuleRepo;
import com.repositories.OptionRepo;
import com.repositories.QuestionRepo;
import com.repositories.RoleRepository;
import com.repositories.UserRepo;

@SpringBootApplication
public class OnlineExamApp extends WebMvcConfigurerAdapter implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	OptionRepo optionRepo;
	@Autowired
	QuestionRepo questionRepo;
	@Autowired
	ModuleRepo moduleRepo;
	@Autowired
	ExamRepo examRepo;
	@Autowired
	AnswerRepo answerRepo;
	@Autowired
	CategoryRepo categoryRepo;
	
	public final static int ADMIN = 1;
	public final static int STUDENT = 2;
	public final static int TEACHER = 3;

	public static void main(String[] args) {
		SpringApplication.run(OnlineExamApp.class, args);
	}

	public void run(String... args) throws ParseException {

		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();

		//create modules.
		List<Module> moduleList = new ArrayList<Module>();
		Module module = new Module("CO3015", "Individual project");
		Module module1 = new Module("CO3098", "Web technology");
		Module module2 = new Module("CO3000", "C++");

	
		Category category = new Category();
		category.setCategoryName("ABC");
		categoryRepo.save(category);
		// predefine a new student account
		Role ROLE_STUDENT = roleRepo.save(new Role(STUDENT, "STUDENT"));
		Users student = new Users();
		student.setEmail("student@gmail.com");
		student.setPassword(pe.encode("a"));
		student.setFirstName("Kevin");
		student.setLastName("Zhang");
		student.setRole(ROLE_STUDENT);
		student.setModuleList(moduleList);


		// predefine a new teacher account
		Role ROLE_TEACHER = roleRepo.save(new Role(TEACHER, "TEACHER"));
		Users teacher = new Users();
		teacher.setEmail("teacher@gmail.com");
		teacher.setPassword(pe.encode("a"));
		teacher.setRole(ROLE_TEACHER);
		teacher.setFirstName("Jon");
		teacher.setLastName("Java");
		moduleList.remove(module1);
		teacher.setModuleList(moduleList);
		

		// predefine a new Admin account
		Role ROLE_ADMIN = roleRepo.save(new Role(ADMIN, "ADMIN"));
		Users admin = new Users();
		admin.setEmail("admin@gmail.com");
		admin.setPassword(pe.encode("a"));
		admin.setRole(ROLE_ADMIN);
		admin.setFirstName("Admin");
		admin.setLastName("Exam");

		

		//Create questions
		List<Answer> answers = new ArrayList<Answer>();
		
		Question question = new Question("what is your name");		
		Option option = new Option(question, "kevin");
		Option option1 = new Option(question, "ben");
		Option option2 = new Option(question, "alex");
		Answer answer = new Answer(question, "kevin");
		
		Question question1 = new Question("who are you");		
		 Option options = new Option(question1, "john");
		 Option options1 = new Option(question1, "jack");
		 Option options2 = new Option(question1, "james");
		Answer answer1 = new Answer(question1, "john");
		
		Question question2 = new Question("2+2 =");		
		 Option options3 = new Option(question2, "3");
		 Option options4 = new Option(question2, "5");
		 Option options5 = new Option(question2, "4");
		Answer answer2 = new Answer(question2, "4");
		
		answers.add(answer);
		question.setAnswer(answers);
		
		question.setModule(module);
		question1.setModule(module);
		question2.setModule(module2);
		
		question.setQuestion_marks(1);
		question1.setQuestion_marks(2);
		question2.setQuestion_marks(1);
		
		question.setUsage(question.getUsage());
		question1.setUsage(question1.getUsage()+1);
		question2.setUsage(question2.getUsage()+2);
		
		//first exam
		List <Question> questionList = new ArrayList<>();
		Exam exam = new Exam();
		exam.setExamID(1);
		exam.setStatus(1);
		questionList.add(question);
		questionList.add(question1);
		exam.setExamName("Test1");
		exam.setDuration(2);
//		exam.setQuestionList(questionList);
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-04-20 00:00:59");
		exam.setStartTime(date);
		
		//second exam
		Exam exam1 = new Exam();
		exam1.setExamID(2);
		exam1.setStatus(0);
		exam1.setExamName("Mock1");
		exam1.setDuration(1);
		Date date1 = new Date();
		exam1.setStartTime(date1);
		
		
		moduleList.add(module);
		moduleList.add(module1);
		moduleList.add(module2);
		moduleRepo.save(moduleList);
		
		userRepo.save(student);
		userRepo.save(teacher);
		userRepo.save(admin);

		
		exam.setModule(module);
		exam1.setModule(module1);
		examRepo.save(exam);
		examRepo.save(exam1);

		questionRepo.save(question);
		questionRepo.save(question1);
		questionRepo.save(question2);
		
		optionRepo.save(option); 
		optionRepo.save(option1);
		optionRepo.save(option2);
		
		optionRepo.save(options);
		optionRepo.save(options1);
		optionRepo.save(options2);
		
		optionRepo.save(options3);
		optionRepo.save(options4);
		optionRepo.save(options5);
		
		answerRepo.save(answer);
		answerRepo.save(answer1);
		answerRepo.save(answer2);
	}
}
