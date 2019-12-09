package com.company.utils;

import com.company.constants.Constants;
import com.company.model.GitEvent;
import com.company.model.Repo;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Utils {
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static List<String> supportEventTypes() {
        return Arrays.asList(Constants.PUSH_EVENT, Constants.ISSUES_EVENT, Constants.PULL_REQUEST_EVENT);
    }

    public static ListMultimap<Repo, GitEvent> readFile(File file) throws IOException {
        System.out.printf("Start parse file: %s \n ", file.getName());
        System.out.printf(" Analyzing: %s \n", file.getName());
        ListMultimap<Repo, GitEvent> mapRepoGitEvent = Multimaps.synchronizedListMultimap(ArrayListMultimap.create());
        JsonFactory factory = new JsonFactory();
        try (Stream<String> stream = Files.lines(Paths.get(file.getPath()))) {
            stream.parallel().forEach(s -> {
                mapRepoGitEvent.putAll(bindToGitEvent(factory, s));
            });

        }
        System.out.printf("Done: %s.\n", file.getName());
        return mapRepoGitEvent;
    }

    private static ListMultimap<Repo, GitEvent> bindToGitEvent(JsonFactory factory, String s) {
        ListMultimap<Repo, GitEvent> mapRepoGitEvent = ArrayListMultimap.create();
        try {
            GitEvent ge = mapper.readValue(factory.createParser(s), GitEvent.class);
            if (supportEventTypes().contains(ge.getType())) {
                mapRepoGitEvent.put(ge.getRepo(), ge);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapRepoGitEvent;
    }

    public static File createTempFile(String fileName, String extension) throws IOException {
        return Files.createTempFile(fileName, extension).toFile();
    }
}
