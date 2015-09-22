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

CREATE TABLE oauth_refresh_token (
  oret_refresh_token_id  VARCHAR(256),
  oret_token             LONGVARBINARY,
  oret_authentication    LONGVARBINARY,
  PRIMARY KEY (oret_refresh_token_id)
);

CREATE TABLE oauth_access_token (
  oact_access_token_id   VARCHAR(256),
  oact_token             LONGVARBINARY,
  oact_authentication_id VARCHAR(256),
  oact_user_name         VARCHAR(256),
  oact_client_id         VARCHAR(256),
  oact_authentication    LONGVARBINARY,
  oact_refresh_token_id  VARCHAR(256),
  PRIMARY KEY (oact_access_token_id),
  FOREIGN KEY (oact_refresh_token_id) REFERENCES oauth_refresh_token(oret_refresh_token_id) 
);
CREATE UNIQUE INDEX oact_tuning_1 ON oauth_access_token(oact_access_token_id);
CREATE UNIQUE INDEX oact_tuning_2 ON oauth_access_token(oact_user_name, oact_client_id);
CREATE INDEX oacht_tuning_3 ON oauth_access_token(oact_refresh_token_id);

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
