package com.company.analyzer.tasks;

import com.company.analyzer.DoFunction;
import com.company.analyzer.statistics.AverageCommitsPerDay;
import com.company.analyzer.statistics.AverageIssueRemainedOpen;
import com.company.analyzer.statistics.AverageTimePullRequestMerged;
import com.company.analyzer.statistics.RatioCommitPerDeveloper;
import com.company.constants.Constants;
import com.company.model.GitEvent;
import com.company.model.HealthRepo;
import com.company.model.Repo;
import com.company.utils.MathUtils;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class StatisticTask implements Callable<List<HealthRepo>> {
    private final Repo repo;
    private final Collection<GitEvent> gitEvents;

    public StatisticTask(Repo repo, Collection<GitEvent> gitEvents) {
        this.repo = repo;
        this.gitEvents = gitEvents;
    }

    @Override
    public List<HealthRepo> call() throws Exception {
        List<HealthRepo> healthRepos = Collections.synchronizedList(new ArrayList<>());
        Map<String, List<GitEvent>> mapEventGitEvent =
                gitEvents.stream().collect(Collectors.groupingBy(GitEvent::getType, Collectors.toList()));
        List<GitEvent> pushEvents = getByEventType(mapEventGitEvent, Constants.PUSH_EVENT);
        double averageCommitPerDay = 0;
        double ratioCommitPerDeveloper = 0.0;
        if (!pushEvents.isEmpty()) {
            //Average number of commits (push) per day (to any branch)
            averageCommitPerDay = (Double) DoFunction.apply(pushEvents, new AverageCommitsPerDay());
            //Ratio Commit Per Developer
            ratioCommitPerDeveloper = (Double) DoFunction.apply(pushEvents, new RatioCommitPerDeveloper());
        }
        //Average time that an issue remains opened
        double averageIssueRemainedOpen =
                calculateAverageIssueRemainedOpen(getByEventType(mapEventGitEvent, Constants.ISSUES_EVENT));
        //3.Average time for a pull request to get merged
        double averagePullRequestMerged =
                calculateAveragePullRequestMerged(getByEventType(mapEventGitEvent, Constants.PULL_REQUEST_EVENT));

        double healthScore = MathUtils.sum(averageCommitPerDay,
                averageIssueRemainedOpen,
                averagePullRequestMerged,
                ratioCommitPerDeveloper);
        String[] orgRepo = repo.getName().split("/");
        HealthRepo healthRepo = HealthRepo.create(averageCommitPerDay,
                ratioCommitPerDeveloper,
                averageIssueRemainedOpen,
                averagePullRequestMerged,
                healthScore,
                orgRepo);
        healthRepos.add(healthRepo);
        return healthRepos;
    }

    private double calculateAverageIssueRemainedOpen(Collection<GitEvent> issueEvents) throws Exception {
        double averageIssueRemainedOpen = 0.0;
        if (!issueEvents.isEmpty()) {
            averageIssueRemainedOpen = (Double) DoFunction.apply(issueEvents, new AverageIssueRemainedOpen());
        }
        return averageIssueRemainedOpen;
    }

    private double calculateAveragePullRequestMerged(Collection<GitEvent> prsMerged) throws Exception {

        double averagePullRequestMerged = 0.0;
        if (!prsMerged.isEmpty()) {
            averagePullRequestMerged = (Double) DoFunction.apply(prsMerged, new AverageTimePullRequestMerged());
        }
        return averagePullRequestMerged;
    }

    private List<GitEvent> getByEventType(Map<String, List<GitEvent>> mapEventGitEvent, String eventType) {
        return Optional.ofNullable(mapEventGitEvent.get(eventType)).orElse(Collections.EMPTY_LIST);
    }
}
