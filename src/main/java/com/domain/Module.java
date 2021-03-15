package com.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity(name="Module")
public class Module {
	
	@Id
	@Column(name="MODULE_CODE", unique = true, nullable=false)
	private String moduleCode;
	
	@Column(name="MODULE_NAME")
	private String moduleName;
	
	@OneToMany(mappedBy ="module")
	private List<Exam> examList;
	
	@ManyToMany(mappedBy="moduleList")
    private List<Users> userList = new ArrayList<Users>();
	
	@OneToMany(mappedBy="module")
	private List<Question> questionList = new ArrayList<>();
	
	public Module() {
		
	}
	public Module(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public Module(String moduleCode, String moduleName) {
		this.moduleCode = moduleCode;
		this.moduleName = moduleName;
	}
	
	
// ------------getter and setter------------------
	

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<Exam> getExam() {
		return examList;
	}

	public void setExam(List<Exam> exam) {
		this.examList = exam;
	}

	public List<Users> getUser() {
		return userList;
	}

	public void setUser(List<Users> user) {
		this.userList = user;
	}
	public List<Exam> getExamList() {
		return examList;
	}
	public void setExamList(List<Exam> examList) {
		this.examList = examList;
	}
	public List<Users> getUserList() {
		return userList;
	}
	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}
	public List<Question> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}
	
}
