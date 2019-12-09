package com.company.analyzer.statistics;

import com.company.analyzer.DoFunction;
import com.company.analyzer.Execute;
import com.company.analyzer.statistics.exception.AverageCommitPerDayException;
import com.company.model.GitEvent;
import com.company.utils.DateTimeUtils;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

import java.time.LocalDate;
import java.util.List;

/**
 * Calculates number of commits per day
 */
public class AverageCommitsPerDay implements Execute<List<GitEvent>> {

    @Override
    public Double apply(List<GitEvent> events) throws AverageCommitPerDayException {
        try {
            ListMultimap<LocalDate, Integer> dayAndCommit =
                    (ListMultimap<LocalDate, Integer>) DoFunction.apply(events, (Execute<List<GitEvent>>) gitEvents -> {
                        ListMultimap<LocalDate, Integer> mapDayCommit = MultimapBuilder.treeKeys().arrayListValues().build();
                        gitEvents.stream().forEach(gitEvent -> {
                            String createdAt = gitEvent.getCreatedAt();
                            Integer numberOfCommit = gitEvent.getPush().getSize();
                            mapDayCommit.put(DateTimeUtils.toDate(createdAt), numberOfCommit);
                        });
                        return mapDayCommit;
                    });

            double averageCommitPerDay = 0.0;
            if (dayAndCommit != null && !dayAndCommit.isEmpty()) {
                int totalCommit = dayAndCommit.values().stream().mapToInt(Integer::intValue).sum();
                int numberOfDays = dayAndCommit.keySet().size();
                averageCommitPerDay = totalCommit * 1.0 / numberOfDays;
            }

            return averageCommitPerDay;
        } catch (Exception t) {
            throw new AverageCommitPerDayException("Error is happened while calculating average commit per day", t);
        }
    }
}
