package com.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.domain.Answer;
import com.domain.Category;
import com.domain.Module;
import com.domain.Option;
import com.domain.Question;
import com.domain.Role;
import com.domain.Users;
import com.repositories.AnswerRepo;
import com.repositories.CategoryRepo;
import com.repositories.ModuleRepo;
import com.repositories.OptionRepo;
import com.repositories.QuestionRepo;
import com.repositories.UserRepo;

@Controller
@RequestMapping("/")
public class FileUploadController {
	@Autowired
	QuestionRepo questionRepo;
	@Autowired
	OptionRepo optionRepo;
	@Autowired
	AnswerRepo answerRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	ModuleRepo moduleRepo;
	@Autowired
	CategoryRepo categoryRepo;
    @Autowired
    public JavaMailSender emailSender;
	
	private String fileLocation;

	@PostMapping("/uploadExcelFile")
	public String uploadFile(Model model, MultipartFile file) throws IOException {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users users = userRepo.findByEmail(user.getUsername());
		model.addAttribute("User", users.getFirstName()+" "+ users.getLastName());
		
		
	/*	*    Title: File import source code
		*    Author: Baeldung
		*    Date: 2019
		*    Availability: https://www.baeldung.com/spring-mvc-excel-files 	*/  
		
		InputStream in = file.getInputStream();
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
		FileOutputStream f = new FileOutputStream(fileLocation);
		int ch = 0;
		while ((ch = in.read()) != -1) {
			f.write(ch);
		}
		f.flush();
		f.close();
		model.addAttribute("message", "File: " + file.getOriginalFilename() + " has been uploaded successfully!");

		// get current path
		String currentPath = path + "/" + file.getOriginalFilename();
		try {
			readFile(currentPath);
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Upload Filed" );
		}
		return "fileUploadSuccess";
	}

	public void readFile(String path) throws Exception {
		try {
			List<Question> ql= (List<Question>) questionRepo.findAll();
			FileInputStream excelFile = new FileInputStream(new File(path));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();

			XSSFRow getTitle = (XSSFRow) iterator.next();
			String title = getTitle.getCell(0).getStringCellValue();

			if (title.equals("Question")) {
				while (iterator.hasNext()) {
					XSSFRow row = (XSSFRow) iterator.next();

					Question question = new Question();
					List<Option> optionList = new ArrayList<>();

					// Read and save Question and quesiton marks

							question.setQuestion(row.getCell(0).getStringCellValue());
							
							if(row.getCell(9) != null) {
								int question_mark= (int) row.getCell(9).getNumericCellValue();
								question.setQuestion_marks(question_mark);
							}
							if(row.getCell(10) != null) {
								List<Module> moduleList =new ArrayList<Module>();
									for(Module m: moduleRepo.findAll()) {
										if(m.getModuleCode().equals(row.getCell(10).getStringCellValue())) {
										question.setModule(m);
										}
									}
							}	
							questionRepo.save(question);
							
							
							// Read and save all the options
							for (int i = 1; i <= 5; i++) {
								// Avoid reading empty cell
								if (row.getCell(i) != null) {
									Option option = new Option();
									String choices = row.getCell(i).getStringCellValue();
									option.setChoice(choices);
									option.setQuestionID(question);
									optionList.add(option);
								}
							}
							optionRepo.save(optionList);

							// Read and save all the answers
							List<Answer> answerList = new ArrayList<Answer>();
							for (int i = 6; i < 9; i++) {
								// Avoid reading empty cell
								if (row.getCell(i) != null) {
									String answers = row.getCell(i).getStringCellValue();

									Answer answer = new Answer(question, answers); 
									answerList.add(answer);
									answer.setQuestionID(question);
								}
							}
							answerRepo.save(answerList);
							// Read and save all the category
							for (int i = 11; i <= 13; i++) {
								// Avoid reading empty cell
								if (row.getCell(i) != null) {
									
									Category category = categoryRepo.findByCategoryName(row.getCell(i).getStringCellValue());
									
									if(category != null) {
										category.getQuestionList().add(question);
										categoryRepo.save(category);
									}
									else {
										Category new_category = new Category();
										new_category.setCategoryName(row.getCell(i).getStringCellValue());
										new_category.getQuestionList().add(question);
										categoryRepo.save(new_category);
									}		
								}
							}
							
						}
//					}


				}
//			}
			
			if(title.equals("Student Email")) {
				while (iterator.hasNext()) {
					XSSFRow row = (XSSFRow) iterator.next();

					Users user = new Users();
					String email = row.getCell(0).getStringCellValue();
					String firstName = row.getCell(1).getStringCellValue();
					String lastName =row.getCell(2).getStringCellValue();
					Role role = new Role(2,"STUDENT");
					List<Module> moduleList =new ArrayList<Module>();
					
					BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
					String randomPass = user.generateRandomPassword();
					String password = pe.encode(randomPass);
					
					user.setEmail(email);
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setRole(role);
					user.setPassword(password);
					
					//send email
					SimpleMailMessage message = new SimpleMailMessage(); 
			        message.setTo(email);
			        message.setFrom("kevin-zhang95@hotmail.com");
			        message.setSubject("Your password for the online exam"); 
			        message.setText(randomPass);
			        emailSender.send(message);
					
					for (int i = 4; i < 8; i++) {
						// Avoid reading empty cell
						if (row.getCell(i) != null) {
							String moduleCode = row.getCell(i).getStringCellValue();

							if (moduleRepo.findByModuleCode(moduleCode) != null) {
								Module module = new Module(moduleCode);
								moduleList.add(module);
								user.setModuleList(moduleList);
							}
							else {
								throw new Exception(" Module: " + moduleCode + " not Found");
							}
						}

					}
					userRepo.save(user);
				}	
			}
			
			if(title.equals("Teacher Email")) {
				while (iterator.hasNext()) {
					XSSFRow row = (XSSFRow) iterator.next();

					Users user = new Users();
					String email = row.getCell(0).getStringCellValue();
					String firstName = row.getCell(1).getStringCellValue();
					String lastName =row.getCell(2).getStringCellValue();
					Role role = new Role(3,"TEACHER");
					List<Module> moduleList =new ArrayList<Module>();
					
					BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
					String randomPass = user.generateRandomPassword();
					String password = pe.encode(randomPass);
					
					user.setEmail(email);
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setRole(role);
					user.setPassword(password);
					
					//send email
					SimpleMailMessage message = new SimpleMailMessage(); 
			        message.setTo(email);
			        message.setFrom("kevin-zhang95@hotmail.com");
			        message.setSubject("Your password for the online exam"); 
			        message.setText(randomPass);
			        emailSender.send(message);

					for (int i = 4; i < 8; i++) {
						// Avoid reading empty cell
						if (row.getCell(i) != null) {
							String moduleCode = row.getCell(i).getStringCellValue();

							if (moduleRepo.findByModuleCode(moduleCode) != null) {
								Module module = new Module(moduleCode, moduleRepo.findByModuleCode(moduleCode).getModuleName());
								moduleList.add(module);
								user.setModuleList(moduleList);
							}
							else {
								throw new Exception(" Module: " + moduleCode + " not Found");
							}
						}
					}
					userRepo.save(user);
				}	
			}

			if (title.equals("Module Code")) {
				while (iterator.hasNext()) {
					XSSFRow row = (XSSFRow) iterator.next();

					Module module = new Module();
					String moduleCode = row.getCell(0).getStringCellValue();
					String moduleName = row.getCell(1).getStringCellValue();
					
					if(moduleRepo.findByModuleCode(moduleCode)==null) {
						module.setModuleCode(moduleCode);
						module.setModuleName(moduleName);
						
						moduleRepo.save(module);
					}
					else {
						throw new Exception(" Module: " + moduleCode + "Is already exist in database");
					}

				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}