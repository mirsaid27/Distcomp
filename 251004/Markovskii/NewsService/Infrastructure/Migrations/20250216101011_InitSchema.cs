using System;
using FluentMigrator;

namespace Infrastructure.Migrations;

[Migration(20250216101011, TransactionBehavior.None)]
public class InitSchema : Migration
{
    public override void Up()
    {
        const string init_script = 
"""
CREATE ROLE root WITH LOGIN PASSWORD 'root';
CREATE TABLE IF NOT EXISTS tbl_editor (
  id        bigserial     NOT NULL,
  login     varchar(64)   NOT NULL UNIQUE CHECK (LENGTH(login) >= 2),
  password  varchar(128)  NOT NULL CHECK (LENGTH(password) >= 8),
  firstname varchar(64)   NOT NULL CHECK (LENGTH(firstname) >= 2),
  lastname  varchar(64)   NOT NULL CHECK (LENGTH(lastname) >= 2),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tbl_news (
  id        bigserial   NOT NULL,
  editor_id  bigint      NOT NULL, 
  title     varchar(64) NOT NULL UNIQUE CHECK (LENGTH(title) >= 2),
  content   text        NOT NULL CHECK (LENGTH(content) >= 4),
  created   timestamp   NOT NULL DEFAULT NOW(),
  modified  timestamp   NOT NULL DEFAULT NOW(),
  PRIMARY KEY (id),
  FOREIGN KEY (editor_id) REFERENCES tbl_editor (id)
);

CREATE TABLE IF NOT EXISTS tbl_post (
  id        bigserial   NOT NULL,
  news_id    bigint      NOT NULL,
  content   text        NOT NULL CHECK(LENGTH(content) >= 2),
  PRIMARY KEY (id),
  FOREIGN KEY (news_id) REFERENCES tbl_news (id)
);

CREATE TABLE IF NOT EXISTS tbl_mark (
  id        bigserial   NOT NULL,
  name      varchar(32) NOT NULL UNIQUE CHECK(LENGTH(name) >= 2),
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS m2m_news_mark (
  news_id  bigint NOT NULL,
  mark_id bigint NOT NULL,
  PRIMARY KEY (news_id, mark_id),
  FOREIGN KEY (news_id) REFERENCES tbl_news (id),
  FOREIGN KEY (mark_id) REFERENCES tbl_mark (id)
);
""";
        Execute.Sql(init_script);
    }

    public override void Down()
    {
        const string init_down_script = 
"""
DROP TABLE IF EXISTS m2m_news_mark;
DROP TABLE IF EXISTS tbl_mark;
DROP TABLE IF EXISTS tbl_post;
DROP TABLE IF EXISTS tbl_news;
DROP TABLE IF EXISTS tbl_editor;
""";
        Execute.Sql(init_down_script);
    }
}
