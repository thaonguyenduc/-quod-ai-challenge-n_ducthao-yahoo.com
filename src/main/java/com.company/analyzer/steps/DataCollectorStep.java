package com.company.analyzer.steps;

import com.company.analyzer.steps.exception.DataCollectorStepException;
import com.company.utils.DownloadUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.company.utils.JsonUtils.parse;

/**
 * Collect data.
 */
public class DataCollectorStep implements Step<List<String>, List<File>> {

    @Override
    public List<File> execute(List<String> urls) throws DataCollectorStepException {
        try {
            long start = System.currentTimeMillis();
            System.out.println("Data Collector Start: ");
            System.out.println("Running...");
            System.out.println("Number of request to proceed: " + urls.size());
            CountDownLatch latch = new CountDownLatch(urls.size());
            List<File> files = Collections.synchronizedList(new ArrayList<>());
            for (String url : urls) {
                final File file = DownloadUtils.download(url);
                files.add(file);
                Thread t = new Thread(() -> {
                    try {
                        parse(file);
                        latch.countDown();
                    } catch (Throwable e) {
                        latch.countDown();
                        e.printStackTrace();
                    } finally {
                        System.out.printf("%s is written. \n ", file.getName());
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
            return files;
        } catch (IOException e) {
            throw new DataCollectorStepException("Error is happened while running data collector", e);
        }
    }

}

