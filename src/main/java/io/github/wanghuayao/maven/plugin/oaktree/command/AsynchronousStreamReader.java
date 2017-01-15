/*
 * Copyright 2016 wanghuayao@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.wanghuayao.maven.plugin.oaktree.command;

import java.io.*;

/**
 * AsynchronousStreamReader
 */
public class AsynchronousStreamReader implements Runnable {

    private final InputStreamReader reader;
    private final StringBuilder result = new StringBuilder();


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

    private void quietClose(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception ex) {
            // omit
        }
    }
}
