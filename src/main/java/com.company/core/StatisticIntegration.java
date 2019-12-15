package com.company.core;

import com.company.model.GitEvent;

import java.util.List;
import java.util.Map;

public interface StatisticIntegration {
    boolean assertSupportEventType(String eventType);

    String register();

    String getName();

    void execute(Map<String, List<GitEvent>> mapEventGitEvent) throws Exception;

    ValueHolder getHolderThreadLocal();
}
