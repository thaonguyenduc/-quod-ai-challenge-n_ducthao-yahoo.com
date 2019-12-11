package com.company.analyzer.statistics;

import com.company.analyzer.DoFunction;
import com.company.analyzer.Execute;
import com.company.analyzer.statistics.exception.RatioCommitPerDeveloperException;
import com.company.model.GitEvent;
import com.company.model.Push;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

import java.util.List;
import java.util.Map;

/**
 * Calculates ratio commit per developer.
 */
public class RatioCommitPerDeveloper implements Execute<List<GitEvent>> {

    @Override
    public Double apply(List<GitEvent> pushEvents) throws RatioCommitPerDeveloperException {
        ListMultimap<String, Integer> mapCommitPerDeveloper;
        try {
            mapCommitPerDeveloper = (ListMultimap<String, Integer>) DoFunction.transform(pushEvents, (Execute<List<GitEvent>>) gitEvents -> {
                ListMultimap<String, Integer> totalCommitPerDeveloper = MultimapBuilder.treeKeys().arrayListValues().build();
                gitEvents.forEach(gitEvent -> {
                    Push push = gitEvent.getPush();
                    Map<String, Integer> commitPerDeveloper = push.getMapUserCommit();
                    commitPerDeveloper.forEach(totalCommitPerDeveloper::put);
                });
                return totalCommitPerDeveloper;
            });
        } catch (Exception e) {
            throw new RatioCommitPerDeveloperException("An error is happened while calculating ratio commit", e);
        }

        if (mapCommitPerDeveloper != null && mapCommitPerDeveloper.isEmpty()) {
            return 0.0;
        }

        int totalOfCommit = mapCommitPerDeveloper.values().stream().mapToInt(Integer::intValue).sum();
        int numberOfDeveloper = mapCommitPerDeveloper.keySet().size();
        return numberOfDeveloper * 1.0 / totalOfCommit;

    }
}
