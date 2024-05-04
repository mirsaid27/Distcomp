CREATE TABLE IF NOT EXISTS note_by_country_and_news
(
    note_id BIGINT,
    news_id BIGINT,
    country varchar,
    content text,
    PRIMARY KEY ( (country), news_id, note_id)
)
WITH CLUSTERING ORDER BY (news_id ASC, note_id ASC);
CREATE TABLE IF NOT EXISTS note_by_news
(
    note_id BIGINT,
    news_id BIGINT,
    content text,
    PRIMARY KEY ( (news_id), note_id)
)
WITH CLUSTERING ORDER BY (note_id ASC);
CREATE TABLE IF NOT EXISTS note_by_id
(
    note_id BIGINT,
    news_id BIGINT,
    content text,
    PRIMARY KEY ( (note_id) )
);