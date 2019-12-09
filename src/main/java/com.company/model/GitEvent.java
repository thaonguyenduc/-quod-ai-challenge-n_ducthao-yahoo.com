package com.company.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Represent git event.
 */
public class GitEvent implements Identifiable {
    private Long id;
    private String type;
    private Repo repo;
    private Issue issue;
    private PullRequest pullRequest;
    private Push push;
    @JsonProperty("created_at")
    private String createdAt;

    @Override
    public Long getId() {
        return id;
    }

    public GitEvent setId(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public GitEvent setType(String type) {
        this.type = type;
        return this;
    }


    public Issue getIssue() {
        return issue;
    }

    public GitEvent setIssue(Issue issue) {
        this.issue = issue;
        return this;
    }

    public PullRequest getPullRequest() {
        return pullRequest;
    }

    public void setPullRequest(PullRequest pullRequest) {
        this.pullRequest = pullRequest;
    }

    public Push getPush() {
        return push;
    }

    public void setPush(Push push) {
        this.push = push;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("payload")
    private void unpackNested(Map<String, Object> payload) {
        //Issue
        Map<String, Object> issueNode = (Map<String, Object>) payload.get("issue");
        if (issueNode != null) {
            this.issue = Issue.createIssue(issueNode);
        }
        //PullRequest
        Map<String, Object> pullRequestNode = (Map<String, Object>) payload.get("pull_request");

        if (pullRequestNode != null) {
            if (payload.get("action") == null) {
                System.out.println(payload);
            }
            this.pullRequest = PullRequest.createPullRequest(pullRequestNode, (String) payload.get("action"));
        }
        //Push
        if ("PushEvent".equals(this.type)) {
            this.push = Push.createPush(payload);
        }
    }

    public Repo getRepo() {
        return repo;
    }

    public GitEvent setRepo(Repo repo) {
        this.repo = repo;
        return this;
    }

    public Long getRepositoryId() {
        return this.getRepo().getId();
    }

    @Override
    public String toString() {
        return "GitEvent{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", repo=" + repo +
                ", issue=" + issue +
                ", pullRequest=" + pullRequest +
                ", push=" + push +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
