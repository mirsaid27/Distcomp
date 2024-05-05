CREATE TABLE distcomp.message (
      country TEXT,
      tweetId BIGINT,
      id BIGINT,
      content TEXT,
      PRIMARY KEY ((country), tweetId, id)
);

CREATE INDEX note_id_idx
    ON distcomp.message (id);
