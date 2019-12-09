package com.company.analyzer.steps;

import com.company.analyzer.steps.exception.DataCollectorStepException;
import com.company.model.GitEvent;
import com.company.model.Repo;
import com.company.utils.DownloadUtils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.company.utils.Utils.readFile;

/**
 * Collect data.
 */
public class DataCollectorStep implements Step<List<String>, ListMultimap<Repo, GitEvent>> {

    @Override
    public ListMultimap<Repo, GitEvent> execute(List<String> urls) throws DataCollectorStepException {
        try {
            long start = System.currentTimeMillis();
            System.out.println("Data Collector Start: ");
            System.out.println("Running...");
            System.out.println("Number of request to proceed: " + urls.size());
            CountDownLatch latch = new CountDownLatch(urls.size());
            ListMultimap<Repo, GitEvent> output = Multimaps.synchronizedListMultimap(ArrayListMultimap.create());
            for (String url : urls) {
                final File file = DownloadUtils.download(url);
                Thread t = new Thread(() -> {
                    try {
                        output.putAll(readFile(file));
                        latch.countDown();
                    } catch (Throwable e) {
                        latch.countDown();
                        e.printStackTrace();
                    } finally {
                        System.out.printf("%s is removed. \n ", file.getName());
                        file.deleteOnExit();
                    }
                }, "File Parser");
                t.start();
            }

            try {
                latch.await();  // wait until latch counted down to 0
            } catch (InterruptedException e) {
                //ignore
            }
            long end = System.currentTimeMillis();
            System.out.println("Data collector done.");
            System.out.println("Execution time: " + (end - start));
            return output;
        } catch (IOException e) {
            throw new DataCollectorStepException("Error is happened while running data collector", e);
        }
    }

}

