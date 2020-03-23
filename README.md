UserController
The following is how to test the endpoints of the UserController class.  All of these commands
should be run while the application is running.  So open up a seperate terminal window and do the
following commands:
		
		Add User:
		
		curl localhost:8080/add -d userName=user1 -d firstName=John -d lastName=Smith
		
		Delete User:
		
		curl localhost:8080/delete -d id=1
		
		Update User:
		
		curl localhost:8080/update/{id}
		
		"where {id} = 3 or some other number.  It is the id of the user that we wish to update.
		If that user does not exist, it will just create a new user."
		
		Show All Users:
		
		curl localhost:8080/all
		
		Search Users By First Name:
		
		curl localhost:8080/user/{firstName}
		
		"Where {firstName} = John or something other name.  It will return all users whose first name is John"
		
