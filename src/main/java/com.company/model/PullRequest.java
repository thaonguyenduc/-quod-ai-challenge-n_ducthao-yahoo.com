package com.company.model;

import java.util.Map;

/**
 * Represent payload of an IssuesEvent
 */
public class PullRequest implements Identifiable {
    private Long id;
    private String action;
    private boolean merged;
    private String createdAt;
    private String mergedAt;
    private int number;

    public static PullRequest createPullRequest(Map<String, Object> pullRequestNode, String action, int number) {
        PullRequest pullRequest = new PullRequest();
        pullRequest.setAction(action);
        pullRequest.setId(Long.valueOf(pullRequestNode.get("id") + ""));
        pullRequest.setCreatedAt((String) pullRequestNode.get("created_at"));
        if (pullRequestNode.get("merged_at") != null) {
            pullRequest.setMergedAt((String) pullRequestNode.get("merged_at"));
        }

        if (pullRequestNode.get("merged") != null) {
            pullRequest.setMerged((Boolean) pullRequestNode.get("merged"));
        }

        pullRequest.setNumber(number);
        return pullRequest;
    }

    public static PullRequest deserialize(Map<String, Object> pullRequestNode) {
        PullRequest pullRequest = new PullRequest();
        pullRequest.setAction((String) pullRequestNode.get("action"));
        pullRequest.setId(Long.valueOf(pullRequestNode.get("id") + ""));
        pullRequest.setCreatedAt((String) pullRequestNode.get("createdAt"));
        if (pullRequestNode.get("mergedAt") != null) {
            pullRequest.setMergedAt((String) pullRequestNode.get("mergedAt"));
        }

        if (pullRequestNode.get("merged") != null) {
            pullRequest.setMerged((Boolean) pullRequestNode.get("merged"));
        }

        if (pullRequestNode.get("number") != null) {
            pullRequest.setNumber((Integer) pullRequestNode.get("number"));
        }

        return pullRequest;
    }


    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getMergedAt() {
        return mergedAt;
    }

    public void setMergedAt(String mergedAt) {
        this.mergedAt = mergedAt;
    }

    public String getAction() {
        return action;
    }

    public PullRequest setAction(String action) {
        this.action = action;
        return this;
    }

    public boolean isMerged() {
        return merged;
    }

    public PullRequest setMerged(boolean merged) {
        this.merged = merged;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public PullRequest setNumber(int number) {
        this.number = number;
        return this;
    }
}
