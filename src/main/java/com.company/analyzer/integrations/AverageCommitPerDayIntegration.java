package com.company.analyzer.integrations;

import com.company.analyzer.statistics.AverageCommitsPerDay;
import com.company.constants.Constants;
import com.company.core.AbstractStatisticIntegration;
import com.company.core.Execute;

public class AverageCommitPerDayIntegration extends AbstractStatisticIntegration {
    private final Execute<Double> execution = new AverageCommitsPerDay();

    @Override
    public String register() {
        return Constants.PUSH_EVENT;
    }

    @Override
    public String getName() {
        return Constants.AVERAGE_COMMIT_PER_DAY;
    }

    @Override
    protected Execute getExecution() {
        return execution;
    }
}
