package com.company.bootstrap;

import com.company.analyzer.ExecutionContext;
import com.company.analyzer.steps.DataCollectorStep;
import com.company.analyzer.steps.HealthRepoMeasureStep;
import com.company.analyzer.steps.Step;
import com.company.analyzer.steps.export.CSVExportStep;
import com.company.utils.DateTimeUtils;
import com.company.utils.UrlUtils;

import java.io.File;
import java.util.List;

import static com.company.utils.ValidationUtils.assertFromDateLessThanToDate;
import static com.company.utils.ValidationUtils.assertValidDate;

public class Bootstrap {
    public static void main(String[] args) throws Exception {
        String from = args[0];
        String to = args[1];
        System.out.printf("Process request: %s - %s \n", from, to);

        assertValidDate(from);
        assertValidDate(to);
        assertFromDateLessThanToDate(from, to);

        run(from, to);
        System.exit(0);
    }

    /**
     * Runs the job
     *
     * @param from From date.
     * @param to   To date.
     * @throws Exception if any.
     */
    private static void run(String from, String to) throws Exception {
        long start = System.currentTimeMillis();
        System.out.println("Main start. ");
        System.out.println("Main is running... ");

        List<String> dateRange = DateTimeUtils.exportDateBetweenTwoDates(from, to);

        final ExecutionContext executionContext = new ExecutionContext(dateRange);

        Step<ExecutionContext, List<String>> source = Step.of(ExecutionContext::getDateRange);
        Step<ExecutionContext, File> task = source.pipe(UrlUtils::populateUrls)
                .pipe(new DataCollectorStep())
                .pipe(new HealthRepoMeasureStep())
                .pipe(new CSVExportStep());

        File csvFile = task.execute(executionContext);

        long end = System.currentTimeMillis();
        System.out.println("Main Execution Time: " + (end - start));
        System.out.println("Main Done. ");
        System.out.println("Csv output: " + csvFile.getAbsolutePath());
    }
}
