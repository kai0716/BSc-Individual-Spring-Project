package com.domain;

import java.util.Date;
import java.util.List;

public class Dto {

	public Dto() {
		
	}
	
	private String email;
	
	private String firstName;
	private String lastName;
	private String module;
	private String userType;
	private int exam;
	private int questionId_for_delete;
	private int questionId;
	private String moduleName;
	private String examName;
	private Date startTime;
	private String userName;
	private String cheatDate;
	private int count;
	
	private String question;
	private String choice;
	private String answer;
	private int mark;
	private String open_close;
	private double duration;
	
	private List<Answer> answerList;
	private List<Question> questionList;
	private String optionList;
	private String exam_Qestion_List;
	private List<Question> previousQuestion;
	
	private String password;
	private String password1;
	private String oldPassword;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public int getExam() {
		return exam;
	}
	public void setExam(int exam) {
		this.exam = exam;
	}
	
	
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getCheatDate() {
		return cheatDate;
	}
	public void setCheatDate(String cheatDate) {
		this.cheatDate = cheatDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public String getOpen_close() {
		return open_close;
	}
	public void setOpen_close(String open_close) {
		this.open_close = open_close;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getQuestionId_for_delete() {
		return questionId_for_delete;
	}
	public void setQuestionId_for_delete(int questionId_for_delete) {
		this.questionId_for_delete = questionId_for_delete;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public List<Answer> getAnswerList() {
		return answerList;
	}
	public void setAnswerList(List<Answer> answerList) {
		this.answerList = answerList;
	}
	public List<Question> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}
	public String getOptionList() {
		return optionList;
	}
	public void setOptionList(String optionList) {
		this.optionList = optionList;
	}
	public List<Question> getPreviousQuestion() {
		return previousQuestion;
	}
	public void setPreviousQuestion(List<Question> previousQuestion) {
		this.previousQuestion = previousQuestion;
	}
	public String getExam_Qestion_List() {
		return exam_Qestion_List;
	}
	public void setExam_Qestion_List(String exam_Qestion_List) {
		this.exam_Qestion_List = exam_Qestion_List;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword1() {
		return password1;
	}
	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	
}
