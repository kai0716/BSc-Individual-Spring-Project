package com.domain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

//import com.domain.Role;


@Entity(name="user")
public class Users {

	public Users() {
	
	}	
	
	public Users(String email, String password, String firstName, String lastName, List<Module> moduleList) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.moduleList = moduleList;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="USER_ID", nullable=false)
	private int userId=-1;
	
	@Column(name="Email",unique=true, nullable=false)
    private String email;

	@Column(name="Password", nullable=false)
    private String password;
	
	@Column(name="FirstName")
    private String firstName;
	
	@Column(name="LastName")
    private String lastName;
	

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	//The name of the column referenced by this foreign key column.
	@JoinColumn(name="Roles", referencedColumnName="id")
	private Role role;
	
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="USERS_MODULE",
			   joinColumns = {@JoinColumn(name ="user_id", referencedColumnName ="user_id")},
			   inverseJoinColumns = {@JoinColumn(name ="module_code", referencedColumnName ="MODULE_CODE")}
			   )
	private List<Module> moduleList = new ArrayList<Module>();
	
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="USERS_EXAM",
			   joinColumns = {@JoinColumn(name ="user_id", referencedColumnName ="user_id")},
			   inverseJoinColumns = {@JoinColumn(name ="ExamID", referencedColumnName ="EXAM_ID")}
			   )
	private List<Exam> examList = new ArrayList<Exam>();
	
	@OneToMany(mappedBy="user")
	private List<SelectedOption> selectedChoiceList;
	
    @OneToMany(mappedBy = "user_Id", cascade = CascadeType.ALL)
    private List<Mark> markList;
	
	private boolean enabled;
	

// ---------------Getter and setter----------------------------	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

//	public String getUserType() {
//		return userType;
//	}
//
//	public void setUserType(String userType) {
//		this.userType = userType;
//	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	public List<Module> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<Module> moduleList) {
		this.moduleList = moduleList;
	}

	public List<Exam> getExamList() {
		return examList;
	}

	public void setExamList(List<Exam> examList) {
		this.examList = examList;
	}


	public List<Mark> getMarkList() {
		return markList;
	}

	public void setMarkList(List<Mark> markList) {
		this.markList = markList;
	}

	public List<SelectedOption> getSelectedChoiceList() {
		return selectedChoiceList;
	}

	public void setSelectedChoiceList(List<SelectedOption> selectedChoiceList) {
		this.selectedChoiceList = selectedChoiceList;
	}

	// -----------------------functions-------------------------------------------------------
	public String generateRandomPassword() {
		List rules = Arrays.asList(new CharacterRule(EnglishCharacterData.UpperCase, 1),
				new CharacterRule(EnglishCharacterData.LowerCase, 1), new CharacterRule(EnglishCharacterData.Digit, 1),new CharacterRule(EnglishCharacterData.Special, 1));

		PasswordGenerator generator = new PasswordGenerator();
		 password = generator.generatePassword(8, rules);
		return password;
	}
}

