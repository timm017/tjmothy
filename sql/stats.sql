CREATE TABLE leagues
( id int(3) NOT NULL auto_increment, 
	league_name VARCHAR(255) NOT NULL,
	PRIMARY KEY(id) );

CREATE TABLE teams
( id int(10) NOT NULL auto_increment, 
	team_name VARCHAR(255) NOT NULL,
	league_id INT(3),
	PRIMARY KEY(id) );

CREATE TABLE players
( id int(10) NOT NULL auto_increment, 
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	team_id INT(10),
	PRIMARY KEY(id) );
	
	
INSERT INTO leagues (league_name) VALUE ("Central");

INSERT INTO teams (team_name, league_id) VALUE ("Penncrest", 1);

INSERT INTO players (first_name, last_name, team_id) VALUE ("Timothy", "McKeown", 1);
INSERT INTO players (first_name, last_name, team_id) VALUE ("Bob", "Higgins", 1);
INSERT INTO players (first_name, last_name, team_id) VALUE ("Robbie", "Higgins", 1);
INSERT INTO players (first_name, last_name, team_id) VALUE ("Kevin", "Danko", 1);