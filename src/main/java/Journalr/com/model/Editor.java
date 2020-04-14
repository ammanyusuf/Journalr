package Journalr.com.model;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "editor_ID")
public class Editor extends User{

    public Editor() {}

	public Editor(String userName, String firstName, String lastName, String email, String password) {
		super(userName, firstName, lastName, email, password);
	}

    public Editor(User user) {
        super(user);
    }

}
