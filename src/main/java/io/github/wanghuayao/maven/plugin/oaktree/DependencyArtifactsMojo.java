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
package io.github.wanghuayao.maven.plugin.oaktree;

import io.github.wanghuayao.maven.plugin.oaktree.utils.ArtifactWriter;
import io.github.wanghuayao.maven.plugin.oaktree.utils.StreamUtils;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * this is used by CreateTreeMojo
 * <pre>
 * mvn io.github.wanghuayao.maven.plugin:maven-oaktree-plugin:1.0-SNAPSHOT:dependency-artifacts -Doutput.file=xxxxxxxxxxxxxxxxxxxxx
 * </pre>
 * @goal dependency-artifacts
 * 
 * @phase process-sources
 */
public class DependencyArtifactsMojo extends AbstractMojo {
    /**
     * Location of the file.
     * @parameter property="output.file"
     */
    private File outputFile;

    /**
     * Location of the file.
     * @parameter property="project.build.directory"
     * @required
     */
    private File outputDirectory;

    public void execute() throws MojoExecutionException
    {
        try {
            MavenProject mp = (MavenProject) this.getPluginContext().get("project");

            if (outputFile == null) {
                outputFile = new File(outputDirectory, mp.getGroupId() + "." + mp.getArtifactId() + "." + mp.getVersion() + ".txt");
            }

            // delete old file
            if (outputFile.exists()) {
                return;
            }

            FileWriter w = null;
            try {
                w = new FileWriter(outputFile);
                for (Object obj : mp.getDependencyArtifacts()) {
                    DefaultArtifact artifact = (DefaultArtifact) obj;
                    ArtifactWriter.artifactToWriter(artifact, w);
                    w.write('\n');
                }
            } catch (IOException e) {
                throw new MojoExecutionException("Error writing file " + outputFile, e);
            } finally {
                StreamUtils.quiteClose(w);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw  new MojoExecutionException(ex.getMessage(), ex);
        }
    }
}
