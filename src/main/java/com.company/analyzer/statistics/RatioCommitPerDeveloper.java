package com.company.analyzer.statistics;

import com.company.analyzer.DoFunction;
import com.company.analyzer.Execute;
import com.company.analyzer.statistics.exception.RatioCommitPerDeveloperException;
import com.company.model.GitEvent;
import com.company.model.Push;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

import java.util.List;

/**
 * Calculates ratio commit per developer.
 */
public class RatioCommitPerDeveloper implements Execute<List<GitEvent>> {

    @Override
    public Double apply(List<GitEvent> pushEvents) throws RatioCommitPerDeveloperException {
        ListMultimap<String, Integer> mapCommitPerDeveloper = null;
        try {
            mapCommitPerDeveloper = (ListMultimap<String, Integer>) DoFunction.apply(pushEvents, (Execute<List<GitEvent>>) gitEvents -> {
                ListMultimap<String, Integer> totalCommitPerDeveloper = MultimapBuilder.treeKeys().arrayListValues().build();
                gitEvents.stream().forEach(gitEvent -> {
                    Push push = gitEvent.getPush();
                    //commit per event
                    ListMultimap<String, Integer> commitPerDeveloper = push.getCommitPerDeveloper();
                    if (commitPerDeveloper != null) {
                        commitPerDeveloper.asMap().forEach((developer, commit) -> {
                            int commitPerEvent = commit.size();
                            totalCommitPerDeveloper.put(developer, commitPerEvent);
                        });
                    }
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
