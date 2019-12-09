package com.company.utils;

import com.company.utils.file.UncompressingFileHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Request;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class DownloadUtils {
    public static final String JSON_EXTENSION = ".json";
    private static final AsyncHttpClientConfig config;
    private static final int MAX_REQUEST_RETRY = 5;

    private static final int READ_TIMEOUT = 60 * 1000 * 60;

    private static final int MAX_CONNECTIONS = 50;

    private static final int MAX_CONNECTIONS_PER_HOST = 50;

    static {
        config = new AsyncHttpClientConfig.Builder()
                .setMaxRequestRetry(MAX_REQUEST_RETRY)
                .setReadTimeout(READ_TIMEOUT)
                .setMaxConnections(MAX_CONNECTIONS)
                .setMaxConnectionsPerHost(MAX_CONNECTIONS_PER_HOST)
                .build();
    }

    public static File download(String url) throws IOException {
        AsyncHttpClient ahc = new AsyncHttpClient(config);
        String fileName = FilenameUtils.getBaseName(url);
        Request req = ahc.prepareGet(url)
                .addHeader("Accept-Encoding", "gzip")
                .build();
        ListenableFuture<File> future = ahc.executeRequest(req,
                new UncompressingFileHandler(
                        Utils.createTempFile(fileName, JSON_EXTENSION)
                ));

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException t) {
            throw new IOException("failed to download due to time out", t);
        }

    }

}
