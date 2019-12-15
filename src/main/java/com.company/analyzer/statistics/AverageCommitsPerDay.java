package com.company.analyzer.statistics;

import com.company.core.Execute;
import com.company.model.GitEvent;
import com.company.utils.DateTimeUtils;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

import java.time.LocalDate;
import java.util.List;

/**
 * Calculates number of commits per day
 */
public class AverageCommitsPerDay implements Execute<Double> {

    @Override
    public Double calculate(List<GitEvent> gitEvents) {
        if (gitEvents.isEmpty()){
            return 0.0;
        }
        ListMultimap<LocalDate, Integer> dayAndCommit = MultimapBuilder.treeKeys().arrayListValues().build();
        gitEvents.forEach(gitEvent -> {
            String createdAt = gitEvent.getCreatedAt();
            Integer numberOfCommit = gitEvent.getPush().getSize();
            dayAndCommit.put(DateTimeUtils.toDate(createdAt), numberOfCommit);
        });

        double averageCommitPerDay = 0.0;
        if (dayAndCommit != null && !dayAndCommit.isEmpty()) {
            int totalCommit = dayAndCommit.values().stream().mapToInt(Integer::intValue).sum();
            int numberOfDays = dayAndCommit.keySet().size();
            averageCommitPerDay = totalCommit * 1.0 / numberOfDays;
        }

        return averageCommitPerDay;
    }
}
