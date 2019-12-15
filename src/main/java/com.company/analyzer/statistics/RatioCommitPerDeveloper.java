package com.company.analyzer.statistics;

import com.company.core.Execute;
import com.company.model.GitEvent;
import com.company.model.Push;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

import java.util.List;
import java.util.Map;

/**
 * Calculates ratio commit per developer.
 */
public class RatioCommitPerDeveloper implements Execute<Double> {

    @Override
    public Double calculate(List<GitEvent> gitEvents) {
        if (gitEvents.isEmpty()){
            return 0.0;
        }

        ListMultimap<String, Integer> mapCommitPerDeveloper = MultimapBuilder.treeKeys().arrayListValues().build();
        gitEvents.forEach(gitEvent -> {
            Push push = gitEvent.getPush();
            Map<String, Integer> commitPerDeveloper = push.getMapUserCommit();
            commitPerDeveloper.forEach(mapCommitPerDeveloper::put);
        });


        if (mapCommitPerDeveloper != null && mapCommitPerDeveloper.isEmpty()) {
            return 0.0;
        }

        int totalOfCommit = mapCommitPerDeveloper.values().stream().mapToInt(Integer::intValue).sum();
        int numberOfDeveloper = mapCommitPerDeveloper.keySet().size();
        return numberOfDeveloper * 1.0 / totalOfCommit;

    }
}
