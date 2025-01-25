package by.ustsinovich.distcomp.lab1.model;

import java.util.List;

public class Mark extends AbstractEntity {

    private String name;

    private List<Long> issueIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getIssueIds() {
        return issueIds;
    }

    public void setIssueIds(List<Long> issueIds) {
        this.issueIds = issueIds;
    }

}
