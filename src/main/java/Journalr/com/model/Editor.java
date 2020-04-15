package Journalr.com.model;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "editor_ID")
public class Editor extends User{

    /**
     * The default contructor of the editor class
     */
    public Editor() {

    }

    /**
	 * A basic constructor of the editor class.  It calls the super constructor for the user class
	 * @param userName The username of the editor
	 * @param firstName The firstname of the editor
	 * @param lastName The last name of the editor
	 * @param email The email of the editor
	 * @param password The password of the editor
	 */
	public Editor(String userName, String firstName, String lastName, String email, String password) {
		super(userName, firstName, lastName, email, password);
	}

    /**
	 * A constructor that creates a editor given a user input
	 * Calls the super constructor.
	 * @param user The user that we wish to assign as an editor to
	 */
    public Editor(User user) {
        super(user);
    }

}
