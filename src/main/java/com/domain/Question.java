package com.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="Question")
public class Question {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="QUESTION_ID")
	private int questionID;
	
	@Column(name="QUESTION")
	private String question;
	
	@Column(name="QUESTION_Mark")
	private int question_marks;
	
	@Column(name="QUESTION_Usage")
	private int usage =0;
	
	@OneToMany(mappedBy="questionID")
	private List<Option> choiceList;
	
	@OneToMany(mappedBy="questionID")
	private List<SelectedOption> selectedChoiceList;
	
	@OneToMany(mappedBy="questionID")
	private List<Answer> answerList;
	
	@ManyToMany(mappedBy="questionList", cascade = CascadeType.ALL)
    private List<Exam> examList = new ArrayList<Exam>();
	
	@ManyToMany(mappedBy="questionList", cascade = CascadeType.ALL)
    private List<Category> categoryList = new ArrayList<Category>();
	
	@ManyToOne
	@JoinColumn(name = "Module_CODE_FK", referencedColumnName ="MODULE_CODE")
	private  Module module; 
	
	public Question() {
		
	}
	
	public Question(String question) {
		this.question = question;
	}

	// -----------getter and setter-------------------------

	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	public List<Answer> getAnswer() {
		return answerList;
	}

	public void setAnswer(List<Answer> answer) {
		this.answerList = answer;
	}


	public List<Exam> getExamList() {
		return examList;
	}

	public void setExamList(List<Exam> examList) {
		this.examList = examList;
	}

	public List<Option> getChoiceList() {
		return choiceList;
	}

	public void setChoiceList(List<Option> choiceList) {
		this.choiceList = choiceList;
	}

	public List<SelectedOption> getSelectedChoiceList() {
		return selectedChoiceList;
	}

	public void setSelectedChoiceList(List<SelectedOption> selectedChoiceList) {
		this.selectedChoiceList = selectedChoiceList;
	}

	public int getQuestion_marks() {
		return question_marks;
	}

	public void setQuestion_marks(int question_marks) {
		this.question_marks = question_marks;
	}

	public int getUsage() {
		return usage;
	}

	public void setUsage(int usage) {
		this.usage = usage;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	
}
