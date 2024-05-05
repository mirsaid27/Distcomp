-- cql

-- changeset init:1
USE distcomp;
CREATE TABLE tbl_note
(
    country TEXT,
    news_id BIGINT,
    id      BIGINT,
    content TEXT,
    PRIMARY KEY ((country), news_id, id)
)
WITH CLUSTERING ORDER BY (news_id ASC, id ASC);