package com.company.analyzer.tasks;

import com.company.constants.Constants;
import com.company.core.StatisticIntegration;
import com.company.core.IntegrationLocatorService;
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

        Map<String, List<GitEvent>> mapEventGitEvent =
                gitEvents.stream().collect(Collectors.groupingBy(GitEvent::getType, Collectors.toList()));

        for (StatisticIntegration statisticIntegration: IntegrationLocatorService.getIntegrations()){
            statisticIntegration.execute(mapEventGitEvent);
        }

        double averageCommitPerDay = (Double) IntegrationLocatorService.
                getValueHolder(Constants.AVERAGE_COMMIT_PER_DAY).getValue();

        double ratioCommitPerDeveloper  = (Double) IntegrationLocatorService.
                getValueHolder(Constants.RATIO_COMMIT_PER_DEVELOPER).getValue();

        double averageIssueRemainedOpen = (Double) IntegrationLocatorService.
                getValueHolder(Constants.AVERAGE_PULL_REQUEST_MERGED).getValue();

        double averagePullRequestMerged = (Double) IntegrationLocatorService.
                getValueHolder(Constants.AVERAGE_ISSUE_REMAINED_OPEN).getValue();


        double healthScore = MathUtils.sum(averageCommitPerDay,
                averageIssueRemainedOpen,
                averagePullRequestMerged,
                ratioCommitPerDeveloper);
        String[] orgRepo = repo.getName().split("/");

        List<HealthRepo> healthRepos = new ArrayList<>();

        HealthRepo healthRepo = HealthRepo.create(averageCommitPerDay,
                ratioCommitPerDeveloper,
                averageIssueRemainedOpen,
                averagePullRequestMerged,
                healthScore,
                orgRepo);

        healthRepos.add(healthRepo);
        return healthRepos;
    }
}
