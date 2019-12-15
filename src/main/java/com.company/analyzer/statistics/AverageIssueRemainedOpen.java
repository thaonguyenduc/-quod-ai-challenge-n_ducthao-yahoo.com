package com.company.analyzer.statistics;

import com.company.core.Execute;
import com.company.constants.Constants;
import com.company.model.GitEvent;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Calculates number of commits per day
 */
public class AverageIssueRemainedOpen implements Execute<Double> {

    @Override
    public Double calculate(List<GitEvent> gitEvents) {
        if (gitEvents.isEmpty()){
            return 0.0;
        }
        List<GitEvent> remainedOpenIssueEvents = gitEvents.stream()
                .filter(isIssueClosed().negate())
                .collect(Collectors.toList());
        if (remainedOpenIssueEvents.isEmpty()) {
            return 0.0;
        }
        int totalIssues = gitEvents.size();
        int numberOfIssuesRemainedOpen = remainedOpenIssueEvents.size();
        double average = numberOfIssuesRemainedOpen * 1.0 / totalIssues;
        return average;
    }

    private Predicate<GitEvent> isIssueClosed() {
        return gitEvent -> Constants.CLOSED_STATE.equalsIgnoreCase(gitEvent.getIssue().getAction());
    }

}
