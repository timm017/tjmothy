use stats;

CREATE TABLE users
( id int(10) NOT NULL auto_increment,
	phone_number VARCHAR(10) NOT NULL,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	team_id INT(10) NOT NULL,
	email VARCHAR(255) NOT NULL DEFAULT '',
	type INT(10) NOT NULL DEFAULT 1,
	PRIMARY KEY(id) );
	
CREATE TABLE user_types
( id int(10) NOT NULL auto_increment,
	name VARCHAR(255) NOT NULL DEFAULT '',
	description VARCHAR(255) NOT NULL DEFAULT '',
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
	team_wins INT(2) DEFAULT 0,
	team_loses INT(2) DEFAULT 0,
	teamscol VARCHAR(45) DEFAULT ''
	sport INT(2) DEFAULT 0,
	sch_pts DECIMAL(7,4) NOT NULL DEFAULT 0.0000,
    bns_pts DECIMAL(7,4) NOT NULL DEFAULT 0.0000,
    rank DECIMAL(5,3) NOT NULL DEFAULT 0.000,
	PRIMARY KEY(id) );

CREATE TABLE players
( id int(10) NOT NULL auto_increment, 
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	team_id INT(10),
	number INT(3),
	PRIMARY KEY(id) );
	
CREATE TABLE player_points
(  schedule_id INT(10),
	player_id INT(10),
	one_points INT(2) NOT NULL DEFAULT 0,
	one_points_attempted INT(2) NOT NULL DEFAULT 0,
	two_points INT(2) NOT NULL DEFAULT 0,
	three_points INT(2) NOT NULL DEFAULT 0,
	fouls INT(2) NOT NULL DEFAULT 0,
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
	highlights VARCHAR(255) NOT NULL DEFAULT '',
	submitted BOOLEAN DEFAULT 0,
	PRIMARY KEY(schedule_id, team_id));
	
CREATE TABLE sports
( id INT(2) NOT NULL auto_increment, 
	sport VARCHAR(255) NOT NULL,
	gender CHAR(1) NOT NULL,
	description VARCHAR(255),
	PRIMARY KEY(id) );
	
CREATE TABLE season
( id INT(2) NOT NULL auto_increment, 
	season VARCHAR(10) NOT NULL,
	year INT(4) NOT NULL,
	PRIMARY KEY(id) );

CREATE UNIQUE INDEX scheduleTeam ON team_stats(schedule_id, team_id);

ALTER TABLE player_points MODIFY COLUMN pitches INT(3) NOT NULL DEFAULT 0;
ALTER TABLE users MODIFY COLUMN phonenumber DECIMAL(4,3) NOT NULL DEFAULT 0.000;	
ALTER TABLE teams MODIFY COLUMN team_wins INT(2) NOT NULL DEFAULT 0;
ALTER TABLE teams MODIFY COLUMN rank DECIMAL(4,3) NOT NULL DEFAULT 0.000;
ALTER TABLE users ADD type int(10) NOT NULL DEFAULT 1;
ALTER TABLE users ADD email VARCHAR(255) NOT NULL DEFAULT '';
ALTER TABLE teams ADD sport INT(2) DEFAULT 0;
ALTER TABLE teams ADD season INT(4) DEFAULT 1;
ALTER TABLE teams MODIFY COLUMN sch_pts DECIMAL(7,4) NOT NULL DEFAULT 0.0000;
ALTER TABLE teams MODIFY COLUMN bns_pts DECIMAL(7,4) NOT NULL DEFAULT 0.0000;

update teams set sch_pts = 0.0000 where sch_pts IS NULL;
update teams set bns_pts = 0.0000 where bns_pts IS NULL;

insert into players (first_name, last_name, team_id) values ('timstrath', 'tim strath', 131);

INSERT INTO sports (sport, gender, description) VALUES ('Basketball', 'B', 'Boys Basketball');

INSERT INTO schedule (id,game_day, home_team, home_id, home_score, road_team, road_id, road_score) VALUE (153, '2017-01-13', 'Penncrest', 1, 0, 'Conestoga', 2, 0);	
