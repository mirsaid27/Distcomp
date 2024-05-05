DROP KEYSPACE IF EXISTS distcomp;

CREATE KEYSPACE distcomp WITH replication = {
    'class': 'SimpleStrategy',
    'replication_factor': 3
};

USE distcomp;

CREATE TABLE IF NOT EXISTS tbl_post (
    country text,
    story_id bigint,
    id bigint,
    content text,
    PRIMARY KEY ((country), story_id, id)
);