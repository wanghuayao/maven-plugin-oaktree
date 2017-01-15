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

/**
 * command result
 */
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
