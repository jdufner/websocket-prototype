/*
 * Lege Tabellen an. 
 */
CREATE TABLE benutzer (
  name     VARCHAR(25) NOT NULL PRIMARY KEY,
  kennwort VARCHAR(25) NOT NULL
);
--CREATE UNIQUE INDEX benu_name_idx ON benutzer (benu_name);

CREATE TABLE rollen (
  name VARCHAR(25) NOT NULL PRIMARY KEY
);

CREATE TABLE benutzer_rollen (
  benutzer_name VARCHAR(25) NOT NULL,
  rollen_name   VARCHAR(25) NOT NULL,
  PRIMARY KEY (benutzer_name, rollen_name),
  FOREIGN KEY (benutzer_name) REFERENCES benutzer(name),
  FOREIGN KEY (rollen_name) REFERENCES rollen(name)
);

/*
 *  Lege Minimalbefüllung an.
 */
insert into rollen (name) values ('User');
insert into rollen (name) values ('Admin');

insert into benutzer (name, kennwort) values ('juergen', 'juergen');
insert into benutzer_rollen (benutzer_name, rollen_name) values ('juergen', 'User');
insert into benutzer_rollen (benutzer_name, rollen_name) values ('juergen', 'Admin');

insert into benutzer (name, kennwort) values ('jens', 'jens');
insert into benutzer_rollen (benutzer_name, rollen_name) values ('jens', 'User');

insert into benutzer (name, kennwort) values ('matthias', 'matthias');
