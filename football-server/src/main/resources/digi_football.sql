DROP DATABASE digi_football;
CREATE DATABASE digi_football;
ALTER DATABASE digi_football CHARACTER SET utf8 COLLATE utf8_general_ci;
use digi_football;

create table country (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(128),
  phoneCode VARCHAR(10)
);
ALTER TABLE country CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

create table province (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  country_id INT,
  title VARCHAR(128),
  CONSTRAINT fk_province_country FOREIGN KEY (country_id) REFERENCES country (id)
);
ALTER TABLE province CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

create table city (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  country_id INT,
  province_id INT,
  title VARCHAR(128),
  phoneCode VARCHAR(10),
  CONSTRAINT fk_city_country FOREIGN KEY (country_id) REFERENCES country (id),
  CONSTRAINT fk_city_province FOREIGN KEY (province_id) REFERENCES province (id)
);
ALTER TABLE city CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

create table location (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  country_id INT NOT NULL,
  province_id INT NOT NULL,
  city_id INT NOT NULL,
  title VARCHAR(60) NOT NULL,
  latitude DOUBLE,
  longitude DOUBLE,
  address VARCHAR(255),
  phone_number VARCHAR(8),
  post_code VARCHAR(10),
  CONSTRAINT fk_location_country FOREIGN KEY (country_id) REFERENCES country (id),
  CONSTRAINT fk_location_province FOREIGN KEY (province_id) REFERENCES province (id),
  CONSTRAINT fk_location_city FOREIGN KEY (city_id) REFERENCES city (id)
);
ALTER TABLE location CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

create table player_position (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(128)
);
ALTER TABLE player_position CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

create table user (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  gmail VARCHAR(128) NOT NULL,
  mobile VARCHAR(11),
  password VARCHAR(128),
  enter_date timestamp NOT NULL
);
ALTER TABLE user CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

create table user_info (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL UNIQUE,
  first_name VARCHAR(40),
  last_name VARCHAR(40),
  full_name VARCHAR(80),
  national_code VARCHAR(10),
  birthdate CHAR(10),
  age INT,
  image CHAR(255),
  CONSTRAINT fk_user_info_user FOREIGN KEY (user_id) REFERENCES user (id)
);
ALTER TABLE user_info CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

create table user_location (
  user_id INT NOT NULL,
  location_id INT NOT NULL,
  CONSTRAINT fk_user_location_user FOREIGN KEY (user_id) REFERENCES user (id),
  CONSTRAINT fk_user_location_location FOREIGN KEY (location_id) REFERENCES location (id)
);
ALTER TABLE user_location CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

create table player_info (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  position_one INT NOT NULL,
  position_two INT,
  position_three INT,
  goalkeeper INT,
  right_foot INT,
  left_foot INT,
  height INT,
  weight INT,
  birth_year INT,
  CONSTRAINT fk_user_positions_user FOREIGN KEY (user_id) REFERENCES user (id),
  CONSTRAINT fk_user_positions_player_position FOREIGN KEY (position_one) REFERENCES player_position (id)
);
ALTER TABLE player_info CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

create table team (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  title VARCHAR(128),
  created_date timestamp NOT NULL,
  CONSTRAINT fk_team_user FOREIGN KEY (user_id) REFERENCES user (id)
);
ALTER TABLE team CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

create table user_team (
  user_id INT NOT NULL,
  team_id INT NOT NULL,
  CONSTRAINT fk_user_team_user FOREIGN KEY (user_id) REFERENCES user (id),
  CONSTRAINT fk_user_team_team FOREIGN KEY (team_id) REFERENCES team (id)
);
ALTER TABLE team CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

INSERT INTO country (title, phoneCode) VALUES ('ایران', '+98');

INSERT INTO province (country_id, title) VALUES (1, 'تهران');

INSERT INTO city (country_id, province_id, title, phoneCode) VALUES (1, 1, 'تهران', '21');

INSERT INTO player_position (title) VALUES ('گلر'),
  ('دفاع وسط'),
  ('دفاع چپ'),
  ('دفاع راست'),
  ('هافبک دفاعی'),
  ('هافبک بازی ساز'),
  ('هافبک راست'),
  ('هافبک چپ'),
  ('مهاجم');

