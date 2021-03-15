package com.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name="Mark")
public class Mark {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="ID")
	private int id =-1;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "EXAM_ID_FK")
	private Exam exam_Id;
	
	@Column(name="mark")
	private double mark;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	private Users user_Id;
	

	
	public Mark() {
		
	}
//------------------------------------Getter && setter--------------------------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getMark() {
		return mark;
	}

	public void setMark(double mark) {
		this.mark = mark;
	}

	public Users getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(Users user_Id) {
		this.user_Id = user_Id;
	}

	public Exam getExam_Id() {
		return exam_Id;
	}

	public void setExam_Id(Exam exam_Id) {
		this.exam_Id = exam_Id;
	}
	
}
