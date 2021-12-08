create table user ( 
	userId varchar(255) NOT NULL,
	name varchar(255), 
	email varchar(255),
	bio varchar(255),
	phoneNumber varchar(255),
	lastActive varchar(255),
	isOldUser varchar(255),
	isOnline boolean,
	profileImage varchar(255),
	lastMessage varchar(255),
	referrerId varchar(255),
	PRIMARY KEY (userId)
);


-- add column
ALTER TABLE user ADD COLUMN authToken varchar(255);

-- change column 

ALTER TABLE user CHANGE COLUMN authToken authToken  VARCHAR(1000); 

-- referrer column
CREATE TABLE referrer (
	userId varchar(255),
	referrerId varchar(255),
	PRIMARY KEY(userId, referrerId)
); 

INSERT INTO referrer (userId, referrerId) VALUES ("Gh7nBTyyEnY5llnDBzSNLpJU6Ph1", "zFHNviE4zRRltJkwPO5lmCliXsu1");


UPDATE user set profileImage = "https://www.whatsappimages.in/wp-content/uploads/2021/01/Boys-Feeling-Very-Sad-Images-Pics-Downlaod.jpg" WHERE userId = "Gh7nBTyyEnY5llnDBzSNLpJU6Ph1";

UPDATE user set bio = "I am software engineer" WHERE userId = "Gh7nBTyyEnY5llnDBzSNLpJU6Ph1";