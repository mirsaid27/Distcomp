-- +goose Up
CREATE TABLE IF NOT EXISTS Writer (
  id        bigserial     NOT NULL,
  login     varchar(64)   NOT NULL UNIQUE CHECK (LENGTH(login) >= 2),
  password  varchar(128)  NOT NULL CHECK (LENGTH(password) >= 8),
  firstname varchar(64)   NOT NULL CHECK (LENGTH(firstname) >= 2),
  lastname  varchar(64)   NOT NULL CHECK (LENGTH(lastname) >= 2),
  PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS news_id_seq
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    CACHE 1;


CREATE TABLE IF NOT EXISTS News (
  id        bigint      NOT NULL DEFAULT nextval('news_id_seq'),
  writerId  bigint      NOT NULL, 
  title     varchar(64) NOT NULL UNIQUE CHECK (LENGTH(title) >= 2),
  content   text        NOT NULL CHECK (LENGTH(content) >= 4),
  created   timestamp   NOT NULL DEFAULT NOW(),
  modified  timestamp   NOT NULL DEFAULT NOW(),
  PRIMARY KEY (id),
  FOREIGN KEY (writerId) REFERENCES Writer (id)
);

CREATE SEQUENCE IF NOT EXISTS notice_id_seq
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    CACHE 1;

CREATE TABLE IF NOT EXISTS Notice (
  id        bigint      NOT NULL DEFAULT nextval('notice_id_seq'),
  newsId    bigint      NOT NULL,
  content   text        NOT NULL CHECK(LENGTH(content) >= 2),
  PRIMARY KEY (id),
  FOREIGN KEY (newsId) REFERENCES News (id)
);

CREATE SEQUENCE IF NOT EXISTS label_id_seq
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS Label (
  id        bigint      NOT NULL DEFAULT nextval('label_id_seq'),
  name      varchar(32) NOT NULL UNIQUE CHECK(LENGTH(name) >= 2),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS m2m_News_Label (
  newsId  bigint NOT NULL,
  labelId bigint NOT NULL,
  PRIMARY KEY (newsId, labelId),
  FOREIGN KEY (newsId) REFERENCES News (Id),
  FOREIGN KEY (labelId) REFERENCES Label (Id)
);

-- +goose Down
DROP TABLE IF EXISTS m2m_News_Label;
DROP TABLE IF EXISTS Label;
DROP TABLE IF EXISTS Notice;
DROP TABLE IF EXISTS News;
DROP TABLE IF EXISTS Writer;
DROP SEQUENCE IF EXISTS news_id_seq;
DROP SEQUENCE IF EXISTS notice_id_seq;
DROP SEQUENCE IF EXISTS label_id_seq;
