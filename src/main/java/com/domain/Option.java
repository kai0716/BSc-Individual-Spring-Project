package com.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="choices")
public class Option {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "OPTION_ID")
	private int optionID;

	@Column(name = "Choice")
	private String choice;

	@ManyToOne
	@JoinColumn(name = "QUESTION_ID_FK")
	private Question questionID;
	
	
	public Option() {
		
	}
	
	public Option(Question questionID, String choice) {
		this.questionID =questionID;
		this.choice =choice;
	}
	
	// -----------getter and setter-------------------------
	public int getOptionId() {
		return optionID;
	}

	public void setOptionId(int optionId) {
		this.optionID = optionId;
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
	
}
