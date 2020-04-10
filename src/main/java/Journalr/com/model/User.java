package Journalr.com.model;

import javax.persistence.*;

/*
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; */
//import javax.persistence.Inheritance;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	@Id
 	@GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "email_address")
	private String email;

	@Column(name = "is_active")
	private boolean active;
	
	@Column(name = "role_type")			// Might need to change this to roles instead
	private String roles;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	
	public User() {
		
	}
	
	public User(String userName, String firstName, String lastName, String email, String password) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public User(User aUser) {
		this.userId = aUser.getUserId();
		this.userName = aUser.getUserName();
		this.password = aUser.getPassword();
		this.email = aUser.getEmail();
		this.active = aUser.isActive();
		this.roles = aUser.getRoles();
		this.firstName = aUser.getFirstName();
		this.lastName = aUser.getLastName();
	}


	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
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
	
	public void setFirstName(String name) {
		this.firstName = name;
	}

	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String name) {
		this.lastName = name;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

	@Override
	public String toString() {
		return "User{" + "id= " + userId + ", " + "Name= " + firstName + " " + lastName + ", " + "Email= " + email + "}";
	}


	public void copyValues(User user) {
        this.setUserName(user.getUserName());
        this.setPassword(user.getPassword());
        this.setEmail(user.getEmail());
        this.setActive(user.isActive());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
    }

}
