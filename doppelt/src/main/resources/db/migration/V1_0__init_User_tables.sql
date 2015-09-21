/*
 * Lege Tabellen an. 
 */
CREATE TABLE benutzer (
  benu_name     VARCHAR(256) NOT NULL PRIMARY KEY,
  benu_kennwort VARCHAR(256) NOT NULL
);
CREATE UNIQUE INDEX benu_name_idx ON benutzer(benu_name);

CREATE TABLE rollen (
  roll_name VARCHAR(256) NOT NULL PRIMARY KEY
);
CREATE UNIQUE INDEX roll_name_idx ON rollen(roll_name);

CREATE TABLE benutzer_rollen (
  bero_benu_name VARCHAR(256) NOT NULL,
  bero_roll_name   VARCHAR(256) NOT NULL,
  PRIMARY KEY (bero_benu_name, bero_roll_name),
  FOREIGN KEY (bero_benu_name) REFERENCES benutzer(benu_name),
  FOREIGN KEY (bero_roll_name) REFERENCES rollen(roll_name)
);

/*
 *  Lege Minimalbefüllung an.
 */
insert into rollen (roll_name) values ('User');
insert into rollen (roll_name) values ('Admin');

insert into benutzer (benu_name, benu_kennwort) values ('juergen', 'juergen');
insert into benutzer_rollen (bero_benu_name, bero_roll_name) values ('juergen', 'User');
insert into benutzer_rollen (bero_benu_name, bero_roll_name) values ('juergen', 'Admin');

insert into benutzer (benu_name, benu_kennwort) values ('jens', 'jens');
insert into benutzer_rollen (bero_benu_name, bero_roll_name) values ('jens', 'User');

insert into benutzer (benu_name, benu_kennwort) values ('matthias', 'matthias');
