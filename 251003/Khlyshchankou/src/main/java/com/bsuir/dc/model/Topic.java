package com.bsuir.dc.model;

import java.util.Date;

public class Topic {
    private long id;
    private long authorId;
    private String title;
    private String content;
    private Date created;
    private Date modified;

    public void setId(long id) { this.id = id; }
    public long getId() { return id; }

    public void setAuthorId(long authorId) { this.authorId = authorId; }
    public long getAuthorId() { return authorId; }

    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }

    public void setContent(String content) { this.content = content; }
    public String getContent() { return content; }

    public void setCreated(Date created) { this.created = created; }
    public Date getCreated() { return created; }

    public void setModified(Date modified) { this.modified = modified; }
    public Date getModified() { return modified; }
}
