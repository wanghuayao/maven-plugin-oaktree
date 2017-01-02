package io.github.wanghuayao.maven.plugin.oaktree.command;

import java.io.*;

public class AsynchronousStreamReader implements Runnable {

    private final InputStreamReader reader;
    private final StringBuilder     result = new StringBuilder();


    public AsynchronousStreamReader(InputStream inputStream) throws UnsupportedEncodingException {
        reader = new InputStreamReader(inputStream, "UTF-8");
    }

    public void run() {
        int oneChar;
        try {
            while ((oneChar = reader.read()) != -1) {
                result.append((char) oneChar);
            }
        } catch (IOException e) {
            // omit
        } finally {
            quietClose(reader);
        }
    }


    public String getResult() {
        return result.toString();
    }


    public void stop() {
        quietClose(reader);
    }

    private void quietClose(Closeable closeable){
        try{
            closeable.close();
        } catch (Exception ex) {
            // omit
        }
    }
}
