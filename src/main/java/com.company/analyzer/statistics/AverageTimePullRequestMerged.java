package com.company.analyzer.statistics;

import com.company.analyzer.DoFunction;
import com.company.analyzer.Execute;
import com.company.analyzer.statistics.exception.AverageTimePullRequestMergeException;
import com.company.constants.Constants;
import com.company.model.GitEvent;
import com.company.model.PullRequest;
import com.company.utils.DateTimeUtils;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.company.constants.Constants.CLOSED_STATE;

/**
 * Calculates average time pull request Merge
 */
public class AverageTimePullRequestMerged implements Execute<List<GitEvent>> {

    @Override
    public Double apply(List<GitEvent> events) throws AverageTimePullRequestMergeException {
        try {
            List<GitEvent> pullRequestMerged = events.stream().filter(isMerged()).collect(Collectors.toList());

            if (pullRequestMerged.isEmpty()) {
                return 0.0;
            }

            ListMultimap<Long, Long> prsMerged =
                    (ListMultimap<Long, Long>) DoFunction.transform(pullRequestMerged, (Execute<List<GitEvent>>) gitEvents -> {
                        ListMultimap<Long, Long> prMerged = MultimapBuilder.treeKeys().arrayListValues().build();
                        gitEvents.forEach(gitEvent -> {
                            PullRequest pullRequest = gitEvent.getPullRequest();
                            String createdAt = pullRequest.getCreatedAt();
                            String mergedAt = pullRequest.getMergedAt();
                            Long id = pullRequest.getId();
                            long timeToMerge = DateTimeUtils.timeRange(createdAt, mergedAt);
                            if (timeToMerge!=0){
                                prMerged.put(id, timeToMerge);
                            }

                        });
                        return prMerged;
                    });

            if (prsMerged.isEmpty()) {
                return 0.0;
            }

            Long totalTimeToMerge = prsMerged.values().stream().reduce(0l, Long::sum);
            int totalMerged = prsMerged.keySet().size();
            double days = (totalTimeToMerge * 1.0) / Constants.ONE_DAY;
            double average = totalMerged * 1.0 / days;
            return average;
        } catch (Exception e) {
            throw new AverageTimePullRequestMergeException("Error is happened while calculating average time pull request", e);
        }
    }

    private Predicate<? super GitEvent> isMerged() {
        return gitEvent -> CLOSED_STATE.equalsIgnoreCase(gitEvent.getPullRequest().getAction())
                && gitEvent.getPullRequest().isMerged();
    }

}
