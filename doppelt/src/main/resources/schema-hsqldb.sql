/*
 *  Lösche Authentifizierungs-Tabellen, sofern sie existieren.
 */
DROP TABLE benutzer_rollen IF EXISTS;
DROP TABLE rollen          IF EXISTS;
DROP TABLE benutzer        IF EXISTS;

/*
 * Lege Authentifizierungs-Tabellen an. 
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
  bero_roll_name VARCHAR(256) NOT NULL,
  PRIMARY KEY (bero_benu_name, bero_roll_name),
  FOREIGN KEY (bero_benu_name) REFERENCES benutzer(benu_name),
  FOREIGN KEY (bero_roll_name) REFERENCES rollen(roll_name)
);

/*
 *  Lege Minimalbefüllung an.
 */
DELETE FROM benutzer_rollen;
DELETE FROM rollen;
DELETE FROM benutzer;

INSERT INTO rollen (roll_name) VALUES ('User');
INSERT INTO rollen (roll_name) VALUES ('Admin');

--INSERT INTO benutzer (benu_name, benu_kennwort)              VALUES ('juergen', 'juergen');
INSERT INTO benutzer (benu_name, benu_kennwort)              VALUES ('juergen', '$2a$10$rLHFZuzBOLviSEbgIQ4FXuD2jzjHOKnFomzGzDgTlvumGYBBsYlwS');
INSERT INTO benutzer_rollen (bero_benu_name, bero_roll_name) VALUES ('juergen', 'User');
INSERT INTO benutzer_rollen (bero_benu_name, bero_roll_name) VALUES ('juergen', 'Admin');

--INSERT INTO benutzer (benu_name, benu_kennwort)              VALUES ('jens', 'jens');
INSERT INTO benutzer (benu_name, benu_kennwort)              VALUES ('jens', '$2a$10$zzauz/riWWbHVjry/v2L1e7lWBcZv8bikynblVzmq5Vkuswd9ZHnq');
INSERT INTO benutzer_rollen (bero_benu_name, bero_roll_name) VALUES ('jens', 'User');

--INSERT INTO benutzer (benu_name, benu_kennwort)              VALUES ('matthias', 'matthias');
INSERT INTO benutzer (benu_name, benu_kennwort)              VALUES ('matthias', '$2a$10$4yrcFL8peUsAwfIdMlwoq.iQ2JK3vTb2LxxZgcIVfrt9Br8v31E06');


/*
 * Lösche OAuth2-Tabellen, sofern sie existieren.
 */
DROP TABLE oauth_client_details IF EXISTS;
DROP TABLE oauth_access_token   IF EXISTS;
DROP TABLE oauth_refresh_token  IF EXISTS;

/*
 * Lege OAuth2-Tabellen an.
 */
CREATE TABLE oauth_client_details (
  client_id               VARCHAR(256) PRIMARY KEY,
  resource_ids            VARCHAR(256),
  client_secret           VARCHAR(256),
  scope                   VARCHAR(256),
  authorized_grant_types  VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities             VARCHAR(256),
  access_token_validity   INTEGER,
  refresh_token_validity  INTEGER,
  additional_information  VARCHAR(4096),
  autoapprove             VARCHAR(256)
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
