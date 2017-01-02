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


public class CmdExecutor {

    private final static long     DEFAULT_TIMEOUT = 300000;

    private final ExecutorService executor        = Executors.newCachedThreadPool();


    public final NormalCmdResult execNormalCmd(String[] cmdAndArgs, File directory,
                                         Map<String, String> env)
            throws IOException {
        return execNormalCmd(cmdAndArgs, directory, env, DEFAULT_TIMEOUT);
    }


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
