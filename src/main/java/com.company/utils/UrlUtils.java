package com.company.utils;

import com.company.constants.Constants;

import java.util.List;
import java.util.stream.Collectors;

public class UrlUtils {
    public static List<String> populateUrls(List<String> dateRange) {
        return dateRange.stream()
                .map(stringReplace -> Constants.URL_TEMPLATE.replace("{dateToReplace}", stringReplace))
                .collect(Collectors.toList());
    }
}
