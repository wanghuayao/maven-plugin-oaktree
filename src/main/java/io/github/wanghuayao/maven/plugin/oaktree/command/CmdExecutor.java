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

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * Execute command on Operating System
 */
public class CmdExecutor {

    private final static long     DEFAULT_TIMEOUT = 300000;

    private final ExecutorService executor        = Executors.newCachedThreadPool();


    /**
     * Execute a command
     * @param cmdAndArgs command arguments
     * @param directory work directory
     * @param env  env
     * @return Command result
     * @throws IOException
     */
    public final NormalCmdResult execNormalCmd(String[] cmdAndArgs, File directory,
                                         Map<String, String> env)
            throws IOException {
        return execNormalCmd(cmdAndArgs, directory, env, DEFAULT_TIMEOUT);
    }

    /**
     * Execute a command with a timeout.
     * @param cmdAndArgs command arguments
     * @param directory work directory
     * @param env  env
     * @param timeout  timeout
     * @return Command result
     * @throws IOException
     */
     public final NormalCmdResult execNormalCmd(String[] cmdAndArgs, File directory,
                                         Map<String, String> env, long timeout)
            throws IOException {

        // start Process
        final Process process = startProcess(cmdAndArgs, directory, env);

        // timeout
        Future<Void> future = executor.submit(new Callable<Void>() {

            public Void call() throws Exception {
                try {
                    process.waitFor();
                } catch (Exception e) {
                    // ommit
                }
                return null;
            }
            
        });

        AsynchronousStreamReader inputStreamReader = new AsynchronousStreamReader(
                process.getInputStream());
        AsynchronousStreamReader errorStreamReader = new AsynchronousStreamReader(
                process.getErrorStream());
        executor.execute(inputStreamReader);
        executor.execute(errorStreamReader);

        boolean isTimeout = false;
        try {
            future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e1) {
        } catch (ExecutionException e1) {
        } catch (TimeoutException e1) {
            isTimeout = true;
            process.destroy();
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                // omit
            }
        }

        // edit result
        NormalCmdResult normalCmdResult = new NormalCmdResult();
        normalCmdResult.setExitValue(process.exitValue());

        inputStreamReader.stop();
        errorStreamReader.stop();

        normalCmdResult.setStdout(inputStreamReader.getResult());
        String errorMessage = errorStreamReader.getResult();
        if (isTimeout) {
            normalCmdResult.setStderr("Timeout. " + errorMessage);
        } else {
            normalCmdResult.setStderr(errorMessage);
        }

        return normalCmdResult;
    }

    
    /**
     * start new process
     * 
     * @param cmdAndArgs
     * @param directory
     * @param env
     * @return the Process
     * @throws IOException
     */
    private Process startProcess(String[] cmdAndArgs, File directory, Map<String, String> env)
            throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(cmdAndArgs);
        if (directory != null) {
            processBuilder.directory(directory);
        }
        if (env != null) {
            Map<String, String> processEnv = processBuilder.environment();
            processEnv.putAll(env);
        }
        final Process process = processBuilder.start();
        return process;
    }


    public void shutdown() {
        executor.shutdownNow();
    }

}
