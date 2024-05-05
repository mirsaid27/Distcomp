CREATE TABLE distcomp.comment (
      country TEXT,
      issueId BIGINT,
      id BIGINT,
      content TEXT,
      PRIMARY KEY ((country), issueId, id)
);

CREATE INDEX comment_id_idx
    ON distcomp.comment (id);
