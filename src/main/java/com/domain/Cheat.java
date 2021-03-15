package com.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name="Cheat")
public class Cheat {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="Cheat_ID")
	private int cheatID =-1;

	@Column(name="User_Email")
	private String userEmail;
	
	@Column(name="User_Name")
	private String userName;
	
	@Column(name="Date")
	private Date cheatTime;
	
	@Column(name="Cheat_count")
	private int cheatCount;
	
	@ManyToOne
	@JoinColumn(name = "Exam_Id_FK", referencedColumnName ="EXAM_ID")
	private  Exam exam; 
	
	public Cheat() {
		
	}



	public int getCheatID() {
		return cheatID;
	}


	public void setCheatID(int cheatID) {
		this.cheatID = cheatID;
	}


	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCheatTime() {
		return cheatTime;
	}

	public void setCheatTime(Date cheatTime) {
		this.cheatTime = cheatTime;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}



	public int getCheatCount() {
		return cheatCount;
	}



	public void setCheatCount(int cheatCount) {
		this.cheatCount = cheatCount;
	}
	
	
}

