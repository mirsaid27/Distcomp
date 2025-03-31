package by.kapinskiy.Discussion.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.io.Serializable;


@Table("tbl_note")
public class Note implements Serializable {

    @PrimaryKeyClass
    public static class NoteKey implements Serializable {
        @PrimaryKeyColumn(name = "country", type = PrimaryKeyType.PARTITIONED)
        private String country = "UAE";

        @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
        private Long id;

        @PrimaryKeyColumn(name = "issueId", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
        private Long issueId;


        public NoteKey() {
        }

        public NoteKey(String country, Long issueId, Long id) {
            this.country = country;
            this.issueId = issueId;
            this.id = id;
        }

        public NoteKey(Long issueId) {
            this.issueId = issueId;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Long getIssueId() {
            return issueId;
        }

        public void setIssueId(Long issueId) {
            this.issueId = issueId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

    }


    @Column("content")
    private String content;

    @PrimaryKey
    private NoteKey key;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NoteKey getKey() {
        return key;
    }

    public void setKey(NoteKey key) {
        this.key = key;
    }
}