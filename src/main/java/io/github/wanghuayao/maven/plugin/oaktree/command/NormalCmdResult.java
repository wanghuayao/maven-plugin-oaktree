package io.github.wanghuayao.maven.plugin.oaktree.command;

public class NormalCmdResult {

    private String stdout;
    private String stderr;
    private int    exitValue;


    public String getStdout() {
        return stdout;
    }


    public void setStdout(String stdout) {
        this.stdout = stdout;
    }


    public String getStderr() {
        return stderr;
    }


    public void setStderr(String stderr) {
        this.stderr = stderr;
    }



    public int getExitValue() {
        return exitValue;
    }


    public void setExitValue(int exitValue) {
        this.exitValue = exitValue;
    }

}
