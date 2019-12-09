package com.company.model;

import java.util.Map;

/**
 * Represent payload of an IssuesEvent
 */
public class Issue implements Identifiable {
    private Long id;
    private String state;
    private String createdAt;
    private String closeAt;
    private String action;

    public static Issue createIssue(Map<String, Object> issueNode) {
        Issue issue = new Issue();
        issue.setId(Long.valueOf((Integer) issueNode.get("id")));
        issue.setState((String) issueNode.get("state"));
        issue.setCreatedAt((String) issueNode.get("created_at"));
        issue.setCloseAt(((String) issueNode.get("closed_at")));
        issue.setAction(((String) issueNode.get("action")));
        return issue;
    }

    @Override
    public Long getId() {
        return null;
    }

    public Issue setId(Long id) {
        this.id = id;
        return this;
    }


    public String getState() {
        return state;
    }

    public Issue setState(String state) {
        this.state = state;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Issue setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }


    public String getCloseAt() {
        return closeAt;
    }

    public void setCloseAt(String closeAt) {
        this.closeAt = closeAt;
    }

    public String getAction() {
        return action;
    }

    public Issue setAction(String action) {
        this.action = action;
        return this;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", closeAt='" + closeAt + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
