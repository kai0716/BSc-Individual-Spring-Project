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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name="Exam")
public class Exam {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="EXAM_ID", nullable=false)
	private int examID=-1;
	
	@Column(name="EXAM_NAME")
	private String examName;
	
	@Column(name="DURATION")
	private double duration;
	
	@Column(name = "START_TIME")
	private Date startTime;
	
	@Column(name = "exam_status")
	private int status=0;
	
	@Column(name = "TOTLE_MARK")
	private int totalMark;
	
	@Column(name ="NUMBER_OF_QUESTION")
	private int numberOfQuestion;
	
	@Column(name ="Release_Status")
	private int release=0;
	

	@ManyToOne
	@JoinColumn(name = "MODULE_ID_FK")
	private Module module;

	
	@ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="Exam_Question",
			   joinColumns = {@JoinColumn(name ="exam_id", referencedColumnName ="EXAM_ID")},
			   inverseJoinColumns = {@JoinColumn(name ="question_code", referencedColumnName ="QUESTION_ID")}
			   )
	private List<Question> questionList = new ArrayList<>();
	
	@OneToMany(mappedBy="exam_Id", cascade = CascadeType.ALL) 
	private List<Mark> markList = new ArrayList<>();
	
	@OneToMany(mappedBy="exam")
	private List<SelectedOption> selectedOptionList = new ArrayList<>();
	
	@OneToMany(mappedBy="exam")
	private List<Cheat> cheatList = new ArrayList<>();
	
	public Exam() {
		
	}
	
	public Exam(String examName, long duration, Date startTime, int numberOfQuestion) {
		this.examName = examName;
		this.duration = duration;
		this.startTime = startTime;
		this.numberOfQuestion = numberOfQuestion;
	}

//--------------getter and setter--------------------	
	public int getExamID() {
		return examID;
	}

	public void setExamID(int examID) {
		this.examID = examID;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getNumberOfQuestion() {
		return numberOfQuestion;
	}

	public void setNumberOfQuestion(int numberOfQuestion) {
		this.numberOfQuestion = numberOfQuestion;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}


	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}	
	
	public int getTotalMark() {
		return totalMark;
	}

	public void setTotalMark(int totalMark) {
		this.totalMark = totalMark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Mark> getMarkList() {
		return markList;
	}

	public void setMarkList(List<Mark> markList) {
		this.markList = markList;
	}

	public List<SelectedOption> getSelectedOptionList() {
		return selectedOptionList;
	}

	public void setSelectedOptionList(List<SelectedOption> selectedOptionList) {
		this.selectedOptionList = selectedOptionList;
	}

	public List<Cheat> getCheatList() {
		return cheatList;
	}

	public void setCheatList(List<Cheat> cheatList) {
		this.cheatList = cheatList;
	}

	public int getRelease() {
		return release;
	}

	public void setRelease(int release) {
		this.release = release;
	}
	
	
}
