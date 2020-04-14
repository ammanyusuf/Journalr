# In this file, we will populate our database with data

# Inserting data into USER table
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
(114,'user114','password','Tom','Nook','user114@hotmail.com',true,'ROLE_AUTHOR');

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

# Inserting data into AUTHOR table
INSERT INTO `db_journalr`.`author` (`author_ID`) VALUES (111);
INSERT INTO `db_journalr`.`author` (`author_ID`) VALUES (114);
INSERT INTO `db_journalr`.`author` (`author_ID`) VALUES (119);

# Inserting data into REVIEWER table
INSERT INTO `db_journalr`.`reviewer`
(`reviewer_ID`,`favourite_topic`,`affiliation`)
VALUES
(113,'cpsc','University of Calgary');

INSERT INTO `db_journalr`.`reviewer`
(`reviewer_ID`,`favourite_topic`,`affiliation`)
VALUES
(116,'fiction','University of Calgary');

INSERT INTO `db_journalr`.`reviewer`
(`reviewer_ID`,`favourite_topic`,`affiliation`)
VALUES
(117,'math','University of Calgary');

# Inserting data into EDITOR table
INSERT INTO `db_journalr`.`editor` (`editor_ID`) VALUES (112);

# Inserting data into PAPER table
INSERT INTO `db_journalr`.`paper`
(`paper_ID`,`author_ID`,`title`,`file_name`,`submission_date`,`topic`,`submission_deadline`, approved)
VALUES
(111,111,'Regular Expressions','regularExpressions.txt','2020-3-28','math','2020-5-28',false); #GOOD

INSERT INTO `db_journalr`.`paper`
(`paper_ID`,`author_ID`,`title`,`file_name`,`submission_date`,`topic`,`submission_deadline`, approved)
VALUES
(112,111,'The adventure of the Cat','catPaper.txt','2020-3-28','fiction','2020-5-28',false);

INSERT INTO `db_journalr`.`paper`
(`paper_ID`,`author_ID`,`title`,`file_name`,`submission_date`,`topic`,`submission_deadline`, approved)
VALUES
(113,114,'Quantum Theory','theory.pdf','2020-3-28','cpsc','2020-5-28',false);

INSERT INTO `db_journalr`.`paper`
(`paper_ID`,`author_ID`,`title`,`file_name`,`submission_date`,`topic`,`submission_deadline`, approved)
VALUES
(114,114,'The balance of life','life.txt','2020-3-28','academics','2020-5-28',false);

INSERT INTO `db_journalr`.`paper`
(`paper_ID`,`author_ID`,`title`,`file_name`,`submission_date`,`topic`,`submission_deadline`, approved)
VALUES
(115,111,'Corgi goes Skiing','skiing.txt','2020-3-28','non-fiction','2020-5-28',false);

INSERT INTO `db_journalr`.`paper`
(`paper_ID`,`author_ID`,`title`,`file_name`,`submission_date`,`topic`,`submission_deadline`, approved)
VALUES
(116,111,'Computer Graphics','graphics.txt','2020-3-28','cpsc','2020-5-28',false);

# Inserting data into REVIEW_PAPER table
INSERT INTO `db_journalr`.`review_paper`
(`reviewer_ID`,`paper_ID`,major_rev,minor_rev,accept,reject,able_to_review)
VALUES
(113,111,true,true,false,false,true);

INSERT INTO `db_journalr`.`review_paper`
(`reviewer_ID`,`paper_ID`,major_rev,minor_rev,accept,reject,able_to_review)
VALUES
(113,112,true,false,false,false,true);

INSERT INTO `db_journalr`.`review_paper`
(`reviewer_ID`,`paper_ID`,major_rev,minor_rev,accept,reject,able_to_review)
VALUES
(116,111,false,true,false,false,true);

INSERT INTO `db_journalr`.`review_paper`
(`reviewer_ID`,`paper_ID`,major_rev,minor_rev,accept,reject,able_to_review)
VALUES
(116,112,false,false,false,false,false);

INSERT INTO `db_journalr`.`review_paper`
(`reviewer_ID`,`paper_ID`,major_rev,minor_rev,accept,reject,able_to_review)
VALUES
(117,111,true,true,true,false,true);

INSERT INTO `db_journalr`.`review_paper` 
(`reviewer_ID`, `paper_ID`, `major_rev`, `minor_rev`, `accept`, `reject`, `able_to_review`)
VALUES 
('113', '113', '1', '1', '1', '0', '1');


# Inserting data into COMMENT table
# For math paper by author 111
INSERT INTO `db_journalr`.`comment`
(`comment_ID`, `comment`,`reviewer_ID`,`paper_ID`,`comment_date`,`topic`)
VALUES
(111, "Good job! Complex subject, but you made it understandable",113,111,'2020-3-30',"major_rev"); # GOOD

INSERT INTO `db_journalr`.`comment`
(`comment_ID`, `comment`,`reviewer_ID`,`paper_ID`,`comment_date`,`topic`)
VALUES
(112, "Good job!",113,111,'2020-3-30',"minor_rev"); # GOOD

INSERT INTO `db_journalr`.`comment`
(`comment_ID`, `comment`,`reviewer_ID`,`paper_ID`,`comment_date`,`topic`)
VALUES
(113, "I liked your way of explaining by showing examples!",113,112,'2020-3-30',"major_rev");

INSERT INTO `db_journalr`.`comment`
(`comment_ID`, `comment`,`reviewer_ID`,`paper_ID`,`comment_date`,`topic`)
VALUES
(114, "Loved how you explained it with pictures and colors!",116,111,'2020-3-30',"minor_rev");

# For cpsc paper by author 111

INSERT INTO `db_journalr`.`comment`
(`comment_ID`, `comment`,`reviewer_ID`,`paper_ID`,`comment_date`,`topic`)
VALUES
(116, "Awesome job!",117,111,'2020-3-30',"major_rev");

INSERT INTO `db_journalr`.`comment`
(`comment_ID`, `comment`,`reviewer_ID`,`paper_ID`,`comment_date`,`topic`)
VALUES
(117, "Fantastic job!",117,111,'2020-3-30',"minor_rev");

INSERT INTO `db_journalr`.`comment` 
(`comment_ID`, `comment`, `reviewer_ID`, `paper_ID`, `comment_date`, `topic`) 
VALUES ('118', 'Good job! Well explained!', '113', '113', '2020-03-30', 'major_rev');

INSERT INTO `db_journalr`.`comment` 
(`comment_ID`, `comment`, `reviewer_ID`, `paper_ID`, `comment_date`, `topic`) 
VALUES ('119', 'A 10/10', '113', '113', '2020-03-30', 'minor_rev');
