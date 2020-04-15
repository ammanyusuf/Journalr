package Journalr.com.model;

import javax.persistence.*;

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
	
	@Column(name = "role_type")			
	private String roles;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	/**
	 * The basic constructor of the user
	 */
	public User() {
		
	}
	
	/**
	 * A basic constructor of the user class.
	 * @param userName The username of the user
	 * @param firstName The firstname of the user
	 * @param lastName The last name of the user
	 * @param email The email of the user
	 * @param password The password of the user
	 */
	public User(String userName, String firstName, String lastName, String email, String password) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	/**
	 * A copy constructor that creates a new User by copying the values of 
	 * the passed in user
	 * @param aUser the user we wish to copy
	 */
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

	// Setters and getters

	/**
	 * This method gets the user id of the user
	 * @return the user id
	 */
	public int getUserId() {
		return userId;
	}
	
	/**
	 * This method sets the user id of the user
	 * @param userId the user id of the user
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/**
	 * This method gets the username of the user
	 * @return the username
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * This method sets the username of the user
	 * @param userName the username
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * This method gets the password of the user
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * This method sets the password of the user
	 * @param password the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * This method gets the first name of the user
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * This method sets the first name of the user
	 * @param name the first name
	 */
	public void setFirstName(String name) {
		this.firstName = name;
	}

	/**
	 * This method gets the last name of the user
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * This method sets the last name of the user
	 * @param name The last name
	 */
	public void setLastName(String name) {
		this.lastName = name;
	}

	/**
	 * This method gets the email of the user
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * This method sets the email of the user
	 * @param email the email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * This method gets the active attribute of the user
	 * @return the active attribute, true or false
	 */
	public boolean isActive() {
        return active;
    }

	/**
	 * This method sets the active attribute
	 * @param active the active attribute of the user, either true or false
	 */
    public void setActive(boolean active) {
        this.active = active;
    }

	/**
	 * This method gets the role of the user
	 * @return The role of the user
	 */
    public String getRoles() {
        return roles;
    }

	/**
	 * This method sets the role of the user
	 * @param roles The role
	 */
    public void setRoles(String roles) {
        this.roles = roles;
    }

	/**
	 * This method is a basic to string method for the user
	 */
	@Override
	public String toString() {
		return "User{" + "id= " + userId + ", " + "Name= " + firstName + " " + lastName + ", " + "Email= " + email + "}";
	}

	/**
	 * This method copies the values of a passed in user
	 * @param user The user we want to copy values into
	 */
	public void copyValues(User user) {
        this.setUserName(user.getUserName());
        this.setPassword(user.getPassword());
        this.setEmail(user.getEmail());
        this.setActive(user.isActive());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
    }

}
