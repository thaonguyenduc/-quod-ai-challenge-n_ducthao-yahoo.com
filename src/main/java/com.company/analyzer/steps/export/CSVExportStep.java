package com.company.analyzer.steps.export;

import com.company.analyzer.steps.Step;
import com.company.analyzer.steps.exception.CSVExportException;
import com.company.model.HealthRepo;
import com.company.opencsv.utils.ExportCSVUtils;

import java.io.File;
import java.util.List;

/**
 * Exports CSV.
 */
public class CSVExportStep implements Step<List<HealthRepo>, File> {
    @Override
    public File execute(List<HealthRepo> healthRepos) throws CSVExportException {
        try {
            return ExportCSVUtils.exportCSVFile(healthRepos);
        } catch (Exception e) {
            throw new CSVExportException("An error is happened while exporting", e);
        }
    }
}
