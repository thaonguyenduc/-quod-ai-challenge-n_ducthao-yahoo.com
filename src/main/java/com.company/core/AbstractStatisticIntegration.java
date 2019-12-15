package com.company.core;

import com.company.model.GitEvent;

import java.util.*;

public abstract class AbstractStatisticIntegration<T extends Number> implements StatisticIntegration {
    private ThreadLocal<ValueHolder<T>> holderThreadLocal = new ThreadLocal<>();

    @Override
    public boolean assertSupportEventType(String eventType) {
        return register().equalsIgnoreCase(eventType);
    }

    @Override
    public void execute(Map<String, List<GitEvent>> mapEventGitEvent) throws Exception {
        executeInternal(getGitEvents(mapEventGitEvent));
    }

    @Override
    public ValueHolder<T> getHolderThreadLocal() {
        return holderThreadLocal.get();
    }

    private List<GitEvent> getGitEvents(Map<String, List<GitEvent>> mapEventGitEvent) {
        List<GitEvent> gitEvents = mapEventGitEvent.get(register());
        return gitEvents != null ? gitEvents : new ArrayList<>();
    }

    private void executeInternal(List<GitEvent> gitEvents) throws Exception {
        holderThreadLocal.set(new ValueHolder<>((T) getExecution().calculate(gitEvents)));
    }

    protected abstract Execute getExecution();

}
