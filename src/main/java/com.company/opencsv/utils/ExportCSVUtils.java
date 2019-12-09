package com.company.opencsv.utils;

import com.company.constants.Constants;
import com.company.model.HealthRepo;
import com.company.opencsv.CustomMappingStrategy;
import com.company.utils.Utils;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.File;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ExportCSVUtils {

    public static File exportCSVFile(List<HealthRepo> healthRepos) throws Exception {
        File csvFile = Utils.createTempFile(Constants.CSV_FILE_NAME, Constants.CSV_EXTENSION);
        try (Writer writer = Files.newBufferedWriter(Paths.get(csvFile.toURI()))) {
            final CustomMappingStrategy<HealthRepo> mappingStrategy = new CustomMappingStrategy<>();
            mappingStrategy.setType(HealthRepo.class);

            StatefulBeanToCsv<HealthRepo> csvWriter = new StatefulBeanToCsvBuilder<HealthRepo>(writer)
                    .withMappingStrategy(mappingStrategy)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withEscapechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .withOrderedResults(true)
                    .build();

            csvWriter.write(healthRepos);
        }
        return csvFile;
    }
}
