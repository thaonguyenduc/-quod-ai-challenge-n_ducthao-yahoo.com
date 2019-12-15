package com.company.analyzer.statistics;

import com.company.core.Execute;
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
public class AverageTimePullRequestMerged implements Execute<Double> {

    @Override
    public Double calculate(List<GitEvent> gitEvents) {
        if (gitEvents.isEmpty()){
            return 0.0;
        }

        List<GitEvent> pullRequestMerged = gitEvents.stream().filter(isMerged()).collect(Collectors.toList());
        if (pullRequestMerged.isEmpty()) {
            return 0.0;
        }

        ListMultimap<Long, Long> prsMerged = MultimapBuilder.treeKeys().arrayListValues().build();

        pullRequestMerged.forEach(gitEvent -> {
            PullRequest pullRequest = gitEvent.getPullRequest();
            String createdAt = pullRequest.getCreatedAt();
            String mergedAt = pullRequest.getMergedAt();
            Long id = pullRequest.getId();
            long timeToMerge = DateTimeUtils.timeRange(createdAt, mergedAt);
            if (timeToMerge != 0) {
                prsMerged.put(id, timeToMerge);
            }
        });

        if (prsMerged.isEmpty()) {
            return 0.0;
        }

        Long totalTimeToMerge = prsMerged.values().stream().reduce(0l, Long::sum);
        int totalMerged = prsMerged.keySet().size();
        double days = (totalTimeToMerge * 1.0) / Constants.ONE_DAY;
        double average = totalMerged * 1.0 / days;
        return average;
    }

    private Predicate<? super GitEvent> isMerged() {
        return gitEvent -> CLOSED_STATE.equalsIgnoreCase(gitEvent.getPullRequest().getAction())
                && gitEvent.getPullRequest().isMerged();
    }

}
