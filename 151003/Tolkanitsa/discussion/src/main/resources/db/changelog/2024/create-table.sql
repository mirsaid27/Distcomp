CREATE TABLE distcomp.comment (
   country TEXT,
   storyId BIGINT,
   id BIGINT,
   content TEXT,
   PRIMARY KEY ((country), storyId, id)
);

CREATE INDEX note_id_idx
    ON distcomp.comment (id);