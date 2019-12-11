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
    @JsonProperty("issue")
    private Issue issue;
    @JsonProperty("pullRequest")
    private PullRequest pullRequest;
    @JsonProperty("push")
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
            this.issue = Issue.createIssue(issueNode, (String) payload.get("action"));
        }
        //PullRequest
        Map<String, Object> pullRequestNode = (Map<String, Object>) payload.get("pull_request");

        if (pullRequestNode != null) {
            int number = 0;
            if (payload.get("number") != null) {
                number = (int) payload.get("number");
            }
            this.pullRequest = PullRequest.deserialize(pullRequestNode, (String) payload.get("action"), number);
        }
        //Push
        if ("PushEvent".equals(this.type)) {
            this.push = Push.createPush(payload);
        }
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("push")
    private void deserializePushEvent(Map<String, Object> pushNode) {
        //Push
        if (pushNode != null) {
            this.push = Push.deserialize(pushNode);
        }
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("pullRequest")
    private void deserializePullRequestEvent(Map<String, Object> pullRequestNode) {

        if (pullRequestNode != null) {
            if (pullRequestNode.get("action") == null) {
                System.out.println(pullRequestNode);
            }
            this.pullRequest = PullRequest.deserialize(pullRequestNode);
        }
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("issue")
    private void deserializeIssueEvent(Map<String, Object> issueNode) {
        //Issue
        if (issueNode != null) {
            this.issue = Issue.deserialize(issueNode);
        }
    }

    public Repo getRepo() {
        return repo;
    }

    public GitEvent setRepo(Repo repo) {
        this.repo = repo;
        return this;
    }

}
