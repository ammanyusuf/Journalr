UserController
The following is to show how to get the recently added admin functionalitites to work.  Follow in order:
1)  Remove rows.  If you have any rows added from last time, remove them for now, as they might give you an
    SQL error citing duplicate rows.  This may be because of the auto-key generation will start at 1 if you
    set the "spring.jpa.hibernate.ddl-auto" to "create."  Basically, you can run the sql script file
    that we have in the Discord and then set "spring.jpa.hibernate.ddl-auto" to update.

2)  Add an Admin:
	
	INSERT INTO db_journalr.user VALUES (0,1,'john@gmail.com','John','Smith','password','ROLE_ADMIN','admin');

3) Login using the user name "admin" and password "password."

4) Add User: On the admin page, click on the hyperlink "Add New User"
	a) Fill out the table as you please, and click "save" when satisifed.
	
5) Edit User: On the admin page, click on the "edit" button of the user you wish you edit.  Similar configuration
   the add user method.

6) Delete User: On the admin page, click on the "delete" button of the user you wish to delete.
