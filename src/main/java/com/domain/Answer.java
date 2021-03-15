package com.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="Answer")
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "Answer_ID")
	private int answerID=-1;

	@Column(name="ANSWER")
	private String answer;

	@ManyToOne
	@JoinColumn(name = "QUESTION_ID_FK")
	private Question questionID;
	
	public Answer() {
		
	}
	
	public Answer(Question questionID, String answer) {
		this.questionID =questionID;
		this.answer =answer;
	}

	
	// -----------getter and setter-------------------------
	public int getAnswerID() {
		return answerID;
	}

	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Question getQuestionID() {
		return questionID;
	}

	public void setQuestionID(Question questionID) {
		this.questionID = questionID;
	}

}
