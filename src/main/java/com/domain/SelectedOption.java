package com.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="SelectedOptions")
public class SelectedOption {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "SELETED_OPTION_ID")
	private int optionID=-1;

	@ManyToOne
	@JoinColumn(name = "Exam_ID_FK")
	private Exam exam;
	
	@ManyToOne
	@JoinColumn(name = "QUESTION_ID_FK")
	private Question questionID;
	
	@Column(name = "Choice")
	private String choice;
	
	@ManyToOne
	@JoinColumn(name = "User_ID_FK")
	private Users user;
	
	public SelectedOption() {
		
	}

	public int getOptionID() {
		return optionID;
	}

	public void setOptionID(int optionID) {
		this.optionID = optionID;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public Question getQuestionID() {
		return questionID;
	}

	public void setQuestionID(Question questionID) {
		this.questionID = questionID;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	
}
