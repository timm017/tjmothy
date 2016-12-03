use stats;

CREATE TABLE users
( id int(10) NOT NULL auto_increment,
	phone_number VARCHAR(10) NOT NULL,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	team_id INT(10) NOT NULL,
	PRIMARY KEY(id) );

CREATE TABLE leagues
( id int(3) NOT NULL auto_increment, 
	league_name VARCHAR(255) NOT NULL,
	PRIMARY KEY(id) );

CREATE TABLE teams
( id int(10) NOT NULL auto_increment,
    school_name VARCHAR(255) NOT NULL,
	team_name VARCHAR(255) NOT NULL,
	league_id INT(3),
	PRIMARY KEY(id) );

CREATE TABLE players
( id int(10) NOT NULL auto_increment, 
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	team_id INT(10),
	PRIMARY KEY(id) );
	
CREATE TABLE player_points
( id int(10) NOT NULL auto_increment, 
	schedule_id INT(10),
	player_id INT(10),
	one_points INT(2),
	one_points_attempted INT(2),
	two_points INT(2),
	three_points INT(2),
	fouls INT(2),
	PRIMARY KEY(id) );
	
CREATE TABLE player_points
(  schedule_id INT(10),
	player_id INT(10),
	one_points INT(2) NOT NULL DEFAULT 0,
	one_points_attempted INT(2) NOT NULL DEFAULT 0,
	two_points INT(2) NOT NULL DEFAULT 0,
	three_points INT(2) NOT NULL DEFAULT 0,
	rebounds INT(2) NOT NULL DEFAULT 0,
	PRIMARY KEY(schedule_id, player_id));
	
CREATE TABLE team_stats
(  schedule_id INT(10),
	team_id INT(10),
	first_quarter INT(2) NOT NULL DEFAULT 0,
	second_quarter INT(2) NOT NULL DEFAULT 0,
	third_quarter INT(2) NOT NULL DEFAULT 0,
	fourth_quarter INT(2) NOT NULL DEFAULT 0,
	overtime INT(2) NOT NULL DEFAULT 0,
	highlights VARCHAR(255) NOT NULL,
	PRIMARY KEY(schedule_id, team_id));

REPLACE INTO player_points (schedule_id, player_id, one_points) VALUES (0, 1, one_points + 1);
REPLACE INTO player_points SET one_points=one_points + 1 WHERE schedule_id=0 AND player_id=1;

INSERT INTO player_points (schedule_id, player_id) VALUES (0, 1) ON DUPLICATE KEY UPDATE one_points = one_points + 1;
	
INSERT INTO users (phone_number, first_name, last_name, password, team_id) VALUES ("6104573819", "Timothy", "McKeown", "tmckeown1", 1);
INSERT INTO users (phone_number, first_name, last_name, password, team_id) VALUES ("6108121272", "Bobb", "Higgins", "bobhiggins", 1);
	
INSERT INTO leagues (league_name) VALUE ("Central");

INSERT INTO teams (school_name, team_name, league_id) VALUE ("Penncrest", "Lions", 1);

INSERT INTO players (first_name, last_name, team_id) VALUE ("Timothy", "McKeown", 1);
INSERT INTO players (first_name, last_name, team_id) VALUE ("Bob", "Higgins", 1);
INSERT INTO players (first_name, last_name, team_id) VALUE ("Robbie", "Higgins", 1);
INSERT INTO players (first_name, last_name, team_id) VALUE ("Kevin", "Danko", 1);
