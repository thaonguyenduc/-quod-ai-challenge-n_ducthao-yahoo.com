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

    public static PullRequest createPullRequest(Map<String, Object> pullRequestNode, String action) {
        PullRequest pullRequest = new PullRequest();
        pullRequest.setAction(action);
        pullRequest.setId(Long.valueOf((Integer) pullRequestNode.get("id")));
        pullRequest.setCreatedAt((String) pullRequestNode.get("created_at"));
        if (pullRequestNode.get("merged_at") != null) {
            pullRequest.setMergedAt((String) pullRequestNode.get("merged_at"));
        }

        if (pullRequestNode.get("merged") != null) {
            pullRequest.setMerged((Boolean) pullRequestNode.get("merged"));
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

    @Override
    public String toString() {
        return "PullRequest{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", merged=" + merged +
                ", createdAt='" + createdAt + '\'' +
                ", mergedAt='" + mergedAt + '\'' +
                '}';
    }
}
