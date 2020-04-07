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
);

CREATE TABLE editor (
	editor_ID int not null auto_increment PRIMARY KEY,
	FOREIGN KEY(editor_ID)
		REFERENCES user(user_ID)
);

CREATE TABLE reviewer (
	reviewer_ID int not null auto_increment PRIMARY KEY,
	FOREIGN KEY(reviewer_ID)
		REFERENCES user(user_ID),
	favourite_topic varchar(100),
    affiliation varchar(200)
);

CREATE TABLE paper (
	paper_ID int not null auto_increment PRIMARY KEY,
	author_ID int
		REFERENCES author(author_ID),
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
		REFERENCES reviewer(reviewer_ID),
	FOREIGN KEY(paper_ID)
		REFERENCES paper(paper_ID),
	major_rev boolean,
    minor_rev boolean,
    accept boolean,
	able_to_review boolean
);

CREATE TABLE comment (
	comment_ID int PRIMARY KEY auto_increment,
    comment varchar(5000),
	reviewer_ID int not null,
    paper_ID int not null,
	comment_date date
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

INSERT INTO `db_journalr`.`user`
(`user_ID`,`user_name`,`password`,`first_name`,`last_name`,`email_address`,`is_active`,`role_type`)
VALUES
(0,'admin','password','Joey','Bananas','admin@hotmail.com',true,'ROLE_ADMIN');

INSERT INTO `db_journalr`.`user`
(`user_ID`,`user_name`,`password`,`first_name`,`last_name`,`email_address`,`is_active`,`role_type`)
VALUES
(111,'user111','password','Aaron','Mario','user111@hotmail.com',true,'ROLE_AUTHOR');

INSERT INTO `db_journalr`.`user`
(`user_ID`,`user_name`,`password`,`first_name`,`last_name`,`email_address`,`is_active`,`role_type`)
VALUES
(119,'user119','password','Ham','Hamman','user119@hotmail.com',true,'ROLE_AUTHOR');

INSERT INTO `db_journalr`.`user`
(`user_ID`,`user_name`,`password`,`first_name`,`last_name`,`email_address`,`is_active`,`role_type`)
VALUES
(120,'user120','password','Larry','Lobster','user120@hotmail.com',true,'ROLE_AUTHOR');

INSERT INTO `db_journalr`.`user`
(`user_ID`,`user_name`,`password`,`first_name`,`last_name`,`email_address`,`is_active`,`role_type`)
VALUES
(112,'user112','password','Yoshi','Birdo','user112@hotmail.com',true,'ROLE_EDITOR');

INSERT INTO `db_journalr`.`user`
(`user_ID`,`user_name`,`password`,`first_name`,`last_name`,`email_address`,`is_active`,`role_type`)
VALUES
(113,'user113','password','Luigi','Waluigi','user113@hotmail.com',true,'ROLE_REVIEWER');

INSERT INTO `db_journalr`.`user`
(`user_ID`,`user_name`,`password`,`first_name`,`last_name`,`email_address`,`is_active`,`role_type`)
VALUES
(116,'user116','password','John','Johnmane','user116@hotmail.com',true,'ROLE_REVIEWER');

INSERT INTO `db_journalr`.`user`
(`user_ID`,`user_name`,`password`,`first_name`,`last_name`,`email_address`,`is_active`,`role_type`)
VALUES
(117,'user117','password','Man','Mannson','user117@hotmail.com',true,'ROLE_REVIEWER');

INSERT INTO `db_journalr`.`user`
(`user_ID`,`user_name`,`password`,`first_name`,`last_name`,`email_address`,`is_active`,`role_type`)
VALUES
(114,'user114','password','Tom','Nook','user114@hotmail.com',true,'ROLE_AUTHOR');

INSERT INTO `db_journalr`.`author`
(`author_ID`)
VALUES
(111);

INSERT INTO `db_journalr`.`author`
(`author_ID`)
VALUES
(114);

INSERT INTO `db_journalr`.`author`
(`author_ID`)
VALUES
(119);

INSERT INTO `db_journalr`.`author`
(`author_ID`)
VALUES
(120);

INSERT INTO `db_journalr`.`editor`
(`editor_ID`)
VALUES
(112);

INSERT INTO `db_journalr`.`reviewer`
(`reviewer_ID`,`favourite_topic`,`affiliation`)
VALUES
(113,'fiction','University of Calgary');

INSERT INTO `db_journalr`.`reviewer`
(`reviewer_ID`,`favourite_topic`,`affiliation`)
VALUES
(116,'fiction','University of Calgary');

INSERT INTO `db_journalr`.`reviewer`
(`reviewer_ID`,`favourite_topic`,`affiliation`)
VALUES
(117,'fiction','University of Calgary');

INSERT INTO `db_journalr`.`paper`
(`paper_ID`,`author_ID`,`title`,`file_name`,`submission_date`,`topic`,`submission_deadline`,approved)
VALUES
(111,111,'The adventure of the Corgi','corgiPaper.txt','2020-3-28','fantasy','2020-5-28',false);

INSERT INTO `db_journalr`.`paper`
(`paper_ID`,`author_ID`,`title`,`file_name`,`submission_date`,`topic`,`submission_deadline`,approved)
VALUES
(112,111,'The adventure of the Cat','catPaper.txt','2020-3-28','fiction','2020-5-28',false);

INSERT INTO `db_journalr`.`review_paper`
(`reviewer_ID`,`paper_ID`,major_rev,minor_rev,accept,able_to_review)
VALUES
(113,111,false,false,false,false);

INSERT INTO `db_journalr`.`review_paper`
(`reviewer_ID`,`paper_ID`,major_rev,minor_rev,accept,able_to_review)
VALUES
(116,111,false,false,false,false);

INSERT INTO `db_journalr`.`review_paper`
(`reviewer_ID`,`paper_ID`,major_rev,minor_rev,accept,able_to_review)
VALUES
(117,111,false,false,false,false);

INSERT INTO `db_journalr`.`comment`
(`comment`,`reviewer_ID`,`paper_ID`,`comment_date`)
VALUES
("Good job!",113,111,'2020-3-30');

# DONE - March 28th, 2020


