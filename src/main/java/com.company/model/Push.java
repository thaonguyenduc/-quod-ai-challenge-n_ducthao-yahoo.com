package com.company.model;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represent payload of PushEvent.
 */
public class Push implements Identifiable {
    private Long id;
    private Integer size;
    private Map<String, Integer> mapUserCommit = Collections.synchronizedMap(new HashMap<>());

    public static Push createPush(Map<String, Object> node) {
        Push push = new Push();
        push.setId(Long.valueOf(node.get("push_id") + ""));
        push.setSize((Integer) node.get("size"));

        List<Map<String, Object>> commits = (List<Map<String, Object>>) node.get("commits");

        if (!commits.isEmpty()) {
            ListMultimap<String, Integer> commitPerDeveloper = MultimapBuilder.hashKeys().arrayListValues().build();
            commits.forEach(commit -> {
                Map<String, Object> author = (Map<String, Object>) commit.get("author");
                String authorEmail = (String) author.get("email");
                commitPerDeveloper.put(authorEmail, 1);
            });

            commitPerDeveloper.asMap().forEach((email, numberOfCommit) -> push.getMapUserCommit().put(email, numberOfCommit.size()));

        }

        return push;
    }

    public Map<String, Integer> getMapUserCommit() {
        return mapUserCommit;
    }

    public static Push deserialize(Map<String, Object> node) {
        Push push = new Push();
        push.setId(Long.valueOf(node.get("id") + ""));
        push.setSize((Integer) node.get("size"));
        push.getMapUserCommit().putAll((Map<String, Integer>) node.get("mapUserCommit"));
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

}
