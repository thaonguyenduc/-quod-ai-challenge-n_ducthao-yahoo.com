package com.company.analyzer.statistics;

import com.company.analyzer.Execute;
import com.company.analyzer.statistics.exception.IssuesRemainedOpenException;
import com.company.constants.Constants;
import com.company.model.GitEvent;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Calculates number of commits per day
 */
public class AverageIssueRemainedOpen implements Execute<List<GitEvent>> {

    @Override
    public Double apply(List<GitEvent> events) throws IssuesRemainedOpenException {
        try {
            List<GitEvent> remainedOpenIssueEvents = events.stream()
                    .filter(isIssueClosed().negate())
                    .collect(Collectors.toList());
            if (remainedOpenIssueEvents.isEmpty()) {
                return 0.0;
            }
            int totalIssues = events.size();
            int numberOfIssuesRemainedOpen = remainedOpenIssueEvents.size();
            double average = numberOfIssuesRemainedOpen * 1.0 / totalIssues;
            return average;
        } catch (Exception e) {
            throw new IssuesRemainedOpenException("Error is happened while calculating average issue remained open", e);
        }
    }

    private Predicate<GitEvent> isIssueClosed() {
        return gitEvent -> Constants.CLOSED_STATE.equalsIgnoreCase(gitEvent.getIssue().getAction());
    }
}
