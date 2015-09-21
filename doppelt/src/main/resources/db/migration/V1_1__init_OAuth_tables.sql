CREATE TABLE oauth_refresh_token (
  oret_refresh_token_id  VARCHAR(256),
  oret_token             ${datatype.blob},
  oret_authentication    ${datatype.blob},
  PRIMARY KEY (oret_refresh_token_id)
);

CREATE TABLE oauth_access_token (
  oact_access_token_id   VARCHAR(256),
  oact_token             ${datatype.blob},
  oact_authentication_id VARCHAR(256),
  oact_user_name         VARCHAR(256),
  oact_client_id         VARCHAR(256),
  oact_authentication    ${datatype.blob},
  oact_refresh_token_id  VARCHAR(256),
  PRIMARY KEY (oact_access_token_id),
  FOREIGN KEY (oact_refresh_token_id) REFERENCES oauth_refresh_token(oret_refresh_token_id) 
);
CREATE UNIQUE INDEX oact_tuning_1 ON oauth_access_token(oact_access_token_id);
CREATE UNIQUE INDEX oact_tuning_2 ON oauth_access_token(oact_user_name, oact_client_id);
CREATE INDEX oacht_tuning_3 ON oauth_access_token(oact_refresh_token_id);
