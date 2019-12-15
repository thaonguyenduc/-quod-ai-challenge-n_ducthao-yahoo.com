package com.company.utils;

import com.company.core.IntegrationLocatorService;
import com.company.model.GitEvent;
import com.company.model.Repo;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class JsonUtils {
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static void parse(File file) throws IOException {
        List<GitEvent> gitEvents = Collections.synchronizedList(new ArrayList<>());
        JsonFactory factory = new JsonFactory();
        try (Stream<String> stream = Files.lines(Paths.get(file.getPath()))) {
            stream.parallel().forEach(s -> {
                try {
                    GitEvent ge = bindToGitEvent(factory, s);
                    String eventType = ge.getType();
                    boolean isValidEvent = IntegrationLocatorService
                            .getIntegrations()
                            .stream().anyMatch(gei -> gei.assertSupportEventType(eventType));
                    if (isValidEvent) {
                        gitEvents.add(ge);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            //write to json file;
            serializeListEvents(file, gitEvents);
        }
    }

    /**
     * Serializes git events.
     *
     * @param file      File to write.
     * @param gitEvents Git events.
     * @throws IOException if any
     */
    private static void serializeListEvents(File file, List<GitEvent> gitEvents) throws IOException {
        mapper.writeValue(file, gitEvents);
    }

    /**
     * Parses string input to git event.
     *
     * @param factory Json factory
     * @param s       Json string to will be parsed
     * @return GitEvent
     * @throws IOException if any
     */
    private static GitEvent bindToGitEvent(JsonFactory factory, String s) throws IOException {
        GitEvent ge = mapper.readValue(factory.createParser(s), GitEvent.class);
        return ge;
    }

    /**
     * Deserializes json files.
     *
     * @param files Json files will be de-serialized.
     * @return ListMultimap with repo as key and git events as value.
     */
    public static ListMultimap<Repo, GitEvent> deserializeFiles(List<File> files) {
        long start = System.currentTimeMillis();
        System.out.printf("Start deserialize files: %d \n ", files.size());
        System.out.printf(" De-serializing: %s \n", files.size());
        ListMultimap<Repo, GitEvent> mapRepoGitEvent = Multimaps.synchronizedListMultimap(ArrayListMultimap.create());
        files.parallelStream().forEach(f -> {
            try {
                List<GitEvent> gitEvents = mapper.readValue(f, new TypeReference<List<GitEvent>>() {
                });
                mapRepoGitEvent.putAll(Multimaps.index(gitEvents, GitEvent::getRepo));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.printf("Start deserialize files: %d \n ", files.size());
        System.out.printf(" Deserialize successfully : %d \n", (System.currentTimeMillis() - start));
        return mapRepoGitEvent;
    }
}
