package com.company.analyzer.integrations;

import com.company.analyzer.statistics.AverageIssueRemainedOpen;
import com.company.constants.Constants;
import com.company.core.AbstractStatisticIntegration;
import com.company.core.Execute;


public class AverageIssueRemainedOpenIntegration extends AbstractStatisticIntegration {
    private Execute execution = new AverageIssueRemainedOpen();

    @Override
    public String register() {
        return Constants.ISSUES_EVENT;
    }

    @Override
    public String getName() {
        return Constants.AVERAGE_ISSUE_REMAINED_OPEN;
    }

    @Override
    protected Execute getExecution() {
        return execution;
    }
}
