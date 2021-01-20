-- !Ups
CREATE TABLE LOGIN_PROMPT (
  ID bigint(20) NOT NULL AUTO_INCREMENT,
  CAPTION varchar(255) NOT NULL,
  IMAGE_URL varchar(255) NOT NULL,
  QUIET_TIME_IN_SECONDS INT NOT NULL,
  LAST_LOGIN_TIMESTAMP BIGINT NOT NULL
);

INSERT INTO LOGIN_PROMPT (CAPTION, IMAGE_URL, QUIET_TIME_IN_SECONDS, LAST_LOGIN_TIMESTAMP) VALUES
  ('Play Merion!', 'image_merion.jpg', 0, 0),
  ('Enter the Virtual US Open!', 'image_vuso.jpg', 5, 0),
  ('Check out the proshop!', 'image_proshop.jpg', 15, 0);

--!Downs
DROP TABLE LOGIN_PROMPT;