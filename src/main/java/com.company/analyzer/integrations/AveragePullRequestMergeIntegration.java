package com.company.analyzer.integrations;

import com.company.analyzer.statistics.AverageTimePullRequestMerged;
import com.company.constants.Constants;
import com.company.core.AbstractStatisticIntegration;
import com.company.core.Execute;

public class AveragePullRequestMergeIntegration extends AbstractStatisticIntegration {
    private Execute<Double> execution = new AverageTimePullRequestMerged();

    @Override
    public String register() {
        return Constants.PULL_REQUEST_EVENT;
    }

    @Override
    public String getName() {
        return Constants.AVERAGE_PULL_REQUEST_MERGED;
    }

    @Override
    protected Execute getExecution() {
        return execution;
    }
}
