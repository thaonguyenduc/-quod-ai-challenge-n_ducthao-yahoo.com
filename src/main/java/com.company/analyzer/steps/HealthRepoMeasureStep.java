package com.company.analyzer.steps;

import com.company.analyzer.steps.exception.HealthRepoMeasureException;
import com.company.analyzer.tasks.StatisticTask;
import com.company.model.GitEvent;
import com.company.model.HealthRepo;
import com.company.model.Repo;
import com.google.common.collect.ListMultimap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class HealthRepoMeasureStep implements Step<ListMultimap<Repo, GitEvent>, List<HealthRepo>> {

    @Override
    public List<HealthRepo> execute(ListMultimap<Repo, GitEvent> mapGitEvents) throws HealthRepoMeasureException {
        try {
            long start = System.currentTimeMillis();
            System.out.println("Health Repo Measure Start: ");
            System.out.println("Number of repo: " + mapGitEvents.keySet().size());
            System.out.println("Number of row: " + mapGitEvents.values().size());
            System.out.println("Health Repo Measure running...");
            ExecutorService pool = Executors.newCachedThreadPool();
            CompletionService<List<HealthRepo>> service
                    = new ExecutorCompletionService<>(pool);
            List<StatisticTask> callables = Collections.synchronizedList(new ArrayList<>());
            mapGitEvents.asMap().forEach((repo, gitEvents) -> {
                StatisticTask task = new StatisticTask(repo, gitEvents);
                callables.add(task);
            });
            List<Future<List<HealthRepo>>> futures = new ArrayList<>();
            for (StatisticTask task : callables) {
                Future<List<HealthRepo>> future = service.submit(task);
                futures.add(future);
            }
            pool.shutdown();
            List<HealthRepo> healthRepos = futures.stream().map(future -> {
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }).flatMap(List::stream)
                    .sorted(Collections.reverseOrder(Comparator.comparingDouble(HealthRepo::getHealthScore)))
                    .collect(Collectors.toList());
            System.out.println("Health Repo Measure done in : " + (System.currentTimeMillis() - start));
            return healthRepos.subList(0, 1000);
        } catch (Throwable t) {
            throw new HealthRepoMeasureException("Error is happened while measuring health repo", t);
        }

    }

}
