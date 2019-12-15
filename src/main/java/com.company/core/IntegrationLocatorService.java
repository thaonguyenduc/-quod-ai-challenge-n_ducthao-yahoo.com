package com.company.core;

import javax.naming.OperationNotSupportedException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Integration locator service.
 *
 * The service is responsible for gathering concrete statistic integration.
 *
 * The integrations must be configure manually via load method see Bootstrap.configure().
 *
 */
public class IntegrationLocatorService {

    // Integration mapping storage
    // Integration Name : integration
    private static Map<String, StatisticIntegration> metadataMap = new HashMap<>();

    public static void load(StatisticIntegration... integrations) {
        metadataMap = Stream.of(integrations)
                .collect(
                        Collectors
                                .toMap(StatisticIntegration::getName,
                                        statisticIntegration -> statisticIntegration));
    }

    public static StatisticIntegration getStatisticIntegration(String integrationName) throws OperationNotSupportedException {
        StatisticIntegration eventIntegration = metadataMap.get(integrationName);
        if (eventIntegration == null) {
            throw new OperationNotSupportedException("Event not support. Please register event integration");
        }
        return eventIntegration;
    }

    public static ValueHolder getValueHolder(String integrationName) throws OperationNotSupportedException {
       return getStatisticIntegration(integrationName).getHolderThreadLocal();
    }

    public static Collection<StatisticIntegration> getIntegrations() {
        return metadataMap.values();
    }
}
