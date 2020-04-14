# Creating a script to initialize tables, attributes, and data into
# database: journalr. Follow these commands in the order below:
# Created by: Isabella Guimet. Date: March 24th, 2020

# Note if you already have the tables in your database, skip the "CREATE TABLE"
# statements as it will give you errors if these tables already exist
# There are 8 tables to create

CREATE SCHEMA db_journalr;

#MAKE SURE admin1 exists as a database user.  With password password.
#In case it doesn't exist, run the following line before running line 14
#create user 'admin1'@'localhost' identified by 'password';
GRANT ALL ON db_journalr.* to 'admin1'@'localhost';

CREATE TABLE user (
	user_ID int not null auto_increment PRIMARY KEY,
	user_name varchar(25) unique, 
	password varchar(25),
    first_name varchar(25),
    last_name varchar(25),
	email_address varchar(50),
	is_active boolean,
	role_type varchar(25)
);

CREATE TABLE author (
	author_ID int not null auto_increment PRIMARY KEY,
	FOREIGN KEY(author_ID)
		REFERENCES user(user_ID)
        ON UPDATE CASCADE
);

CREATE TABLE editor (
	editor_ID int not null auto_increment PRIMARY KEY,
	FOREIGN KEY(editor_ID)
		REFERENCES user(user_ID)
        ON UPDATE CASCADE
);

CREATE TABLE reviewer (
	reviewer_ID int not null auto_increment PRIMARY KEY,
	FOREIGN KEY(reviewer_ID)
		REFERENCES user(user_ID)
        ON UPDATE CASCADE,
	favourite_topic varchar(100),
    affiliation varchar(200)
);

CREATE TABLE paper (
	paper_ID int not null auto_increment PRIMARY KEY,
	author_ID int
		REFERENCES author(author_ID)
        ON UPDATE CASCADE,
	title varchar(100),
	file_name varchar(500),
	submission_date date,
	topic varchar(50),
    submission_deadline date,
    approved boolean,
    file_type varchar(100),
    data longblob
);

CREATE TABLE review_paper (
	reviewer_ID int not null,
    paper_ID int not null,
    PRIMARY KEY(reviewer_ID, paper_ID),
	FOREIGN KEY(reviewer_ID)
		REFERENCES reviewer(reviewer_ID)
        ON UPDATE CASCADE,
	FOREIGN KEY(paper_ID)
		REFERENCES paper(paper_ID)
        ON UPDATE CASCADE,
	major_rev boolean,
    minor_rev boolean,
    accept boolean,
    reject boolean,
	able_to_review boolean
);

CREATE TABLE comment (
	comment_ID int PRIMARY KEY auto_increment,
    comment varchar(5000),
	reviewer_ID int not null,
    paper_ID int not null,
	comment_date date,
    topic varchar(100)
);

# By now, you should have created all the tables in this journalr database
# You can hit the 'refresh' button beside "SCHEMAS" to see the creation 
# of your tables. 

# Now we will populate our tables with data. Note: only 3 tuples per table 
# will be added. You can change the data to insert by modifying the code in the "values" part.
# If you wish to add more, go ahead by following the structure presented.

# You can also add values by going to the appropiate table by right clicking on it and 
# selecting "Select rows - limit 1000". Add your values and then hit "Apply changes".

# To insert a tuple (row of data), right-click on a table and choose: 'Send to SQL Editor'.
# Then choose 'Insert Statement'. Lastly, fill in your values. 

# The population file is in a different file