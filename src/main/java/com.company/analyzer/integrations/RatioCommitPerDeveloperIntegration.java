package com.company.analyzer.integrations;

import com.company.analyzer.statistics.RatioCommitPerDeveloper;
import com.company.constants.Constants;
import com.company.core.AbstractStatisticIntegration;
import com.company.core.Execute;

public class RatioCommitPerDeveloperIntegration extends AbstractStatisticIntegration {
    private Execute<Double> execution =  new RatioCommitPerDeveloper();

    @Override
    public String register() {
        return Constants.PUSH_EVENT;
    }

    @Override
    public String getName() {
        return Constants.RATIO_COMMIT_PER_DEVELOPER;
    }
    @Override
    protected Execute getExecution() {
        return execution;
    }
}
