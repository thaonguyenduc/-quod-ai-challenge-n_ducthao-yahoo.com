package com.company.utils.file;

import com.ning.compress.DataHandler;
import com.ning.compress.UncompressorOutputStream;
import com.ning.compress.gzip.GZIPUncompressor;
import com.ning.http.client.AsyncHandler;
import com.ning.http.client.HttpResponseBodyPart;
import com.ning.http.client.HttpResponseHeaders;
import com.ning.http.client.HttpResponseStatus;

import java.io.*;

/**
 * Support decompress gzip file on the fly.
 */
public class UncompressingFileHandler implements AsyncHandler<File>, DataHandler {
    private final OutputStream out;
    private File file;
    private boolean failed = false;
    private UncompressorOutputStream uncompressingStream;

    public UncompressingFileHandler(File file) throws FileNotFoundException {
        this.file = file;
        this.out = new FileOutputStream(file);
    }

    @Override
    public boolean handleData(byte[] buffer, int offset, int len) throws IOException {
        out.write(buffer, offset, len);
        return true;
    }

    @Override
    public void allDataHandled() throws IOException {
    }

    @Override
    public void onThrowable(Throwable t) {
        failed = true;
    }

    @Override
    public STATE onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
        if (!failed) {
            // if compressed pass through uncompressing stream
            bodyPart.writeTo(uncompressingStream);
        }
        return STATE.CONTINUE;
    }

    @Override
    public STATE onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
        failed = (responseStatus.getStatusCode() != 200);
        return failed ? STATE.ABORT : STATE.CONTINUE;
    }

    @Override
    public STATE onHeadersReceived(HttpResponseHeaders headers) throws Exception {
        String compression = headers.getHeaders().getFirstValue("Content-Type");
        if (compression != null && !compression.isEmpty() && (compression.contains("gzip"))) {
            uncompressingStream = new UncompressorOutputStream(new GZIPUncompressor(this));
        } else {
            throw new UnsupportedOperationException("Support gzip compression gzip only");
        }
        return STATE.CONTINUE;
    }

    @Override
    public File onCompleted() throws Exception {
        out.flush();
        if (uncompressingStream != null) {
            uncompressingStream.close();
        }
        if (failed) {
            file.delete();
            return null;
        }
        return file;
    }
}
