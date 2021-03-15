package com.domain;

import java.util.ArrayList;
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

@Entity(name="Category")
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="CATEGORY_ID")
	private int categoryId =-1;

	@Column(name="CATEGORY_Name")
	private String categoryName;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="Question_Category",
			   joinColumns = {@JoinColumn(name ="category_id", referencedColumnName ="CATEGORY_ID")},
			   inverseJoinColumns = {@JoinColumn(name ="question_id", referencedColumnName ="QUESTION_ID")}
			   )
	private List<Question> questionList = new ArrayList<>();
	
	public Category() {
		
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}
	
	
}

