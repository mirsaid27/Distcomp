-- Create the "storyteller_discussion" keyspace
CREATE KEYSPACE IF NOT EXISTS storyteller_discussion
    WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

-- Switch to the "storyteller_discussion" keyspace
USE storyteller_discussion;

-- Create the "tbl_post" table
CREATE TABLE IF NOT EXISTS tbl_post (
    country text,
    id bigint,
    storyId bigint,
    content text,
    PRIMARY KEY (country, id, storyId)
) WITH CLUSTERING ORDER BY (id ASC, storyId ASC);