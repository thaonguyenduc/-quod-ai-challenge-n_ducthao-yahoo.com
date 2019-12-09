package com.company.model;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

import java.util.List;
import java.util.Map;

/**
 * Represent payload of PushEvent.
 */
public class Push implements Identifiable {
    private Long id;
    private Integer size;
    private ListMultimap<String, Integer> commitPerDeveloper;

    public static Push createPush(Map<String, Object> node) {
        Push push = new Push();
        push.setId((Long) node.get("push_id"));
        push.setSize((Integer) node.get("size"));

        List<Map<String, Object>> commits = (List<Map<String, Object>>) node.get("commits");
        if (!commits.isEmpty()) {
            ListMultimap<String, Integer> commitPerDeveloper = MultimapBuilder.hashKeys().arrayListValues().build();
            commits.stream().forEach(commit -> {
                Map<String, Object> author = (Map<String, Object>) commit.get("author");
                String authorEmail = (String) author.get("email");
                commitPerDeveloper.put(authorEmail, 1);
            });
            push.setCommitPerDeveloper(commitPerDeveloper);
        }

        return push;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public ListMultimap<String, Integer> getCommitPerDeveloper() {
        return commitPerDeveloper;
    }

    public Push setCommitPerDeveloper(ListMultimap<String, Integer> commitPerDeveloper) {
        this.commitPerDeveloper = commitPerDeveloper;
        return this;
    }

    @Override
    public String toString() {
        return "Push{" +
                "id=" + id +
                ", size=" + size +
                ", commitPerDeveloper=" + commitPerDeveloper +
                '}';
    }
}
