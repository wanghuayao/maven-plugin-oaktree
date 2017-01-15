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

import io.github.wanghuayao.maven.plugin.oaktree.command.CmdExecutor;
import io.github.wanghuayao.maven.plugin.oaktree.command.NormalCmdResult;
import io.github.wanghuayao.maven.plugin.oaktree.parser.ArtifactItem;
import io.github.wanghuayao.maven.plugin.oaktree.parser.ProcessingFilter;
import io.github.wanghuayao.maven.plugin.oaktree.utils.StreamUtils;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.*;

/**
 *
 * Create dependency of all the module.
 *
 * <pre>
 * Usage:
 * mvn io.github.wanghuayao.maven.plugin:maven-oaktree-plugin:1.0-SNAPSHOT:create-tree
 * </pre>
 * @goal create-tree
 * @phase process-sources
 */
public class CreateTreeMojo extends AbstractMojo {
    private OakProperties properties;

    private Log log;
    /**
     * Location of the file.
     *
     * @parameter property="project.build.directory"
     * @required
     */
    private File outputDirectory;

    /**
     * Location of the file.
     *
     * @parameter property="settings.localRepository"
     * @required
     */
    private File localRepository;

    /**
     * Location of the file.
     *
     * @parameter property="like"
     */
    private String likePatten;

    /**
     * Location of the file.
     *
     * @parameter property="hate"
     */
    private String hitePatten;

    /**
     * Location of the file.
     *
     * @parameter property="deep"
     */
    private int deep = Integer.MAX_VALUE;

    /**
     * Location of the file.
     *
     * @parameter property="maven.home"
     */
    private File mavenHome;


    private String mvnExec;
    private String okadependencyOutputDir;
    private boolean isWindwos = false;
    private String dependencyArtifacts;

    private ProcessingFilter filter;

    public void execute() throws MojoExecutionException {
        log = getLog();
        try {
            OakProperties properties = new OakProperties();
            dependencyArtifacts = "io.github.wanghuayao:maven-plugin-oaktree:" + properties.getVersion() + ":dependency-artifacts";

            filter = new ProcessingFilter(likePatten, hitePatten, deep);

            MavenProject mp = (MavenProject) this.getPluginContext().get("project");
            String mavenHomeStr = removeUnusedCharInPath(mavenHome.getPath());
            File mvnExecFile = new File(mavenHomeStr + "/bin/mvn");
            mvnExec = mvnExecFile.getPath();
            String os = System.getProperty("os.name");
            if (os.toLowerCase().startsWith("win")) {
                mvnExec = mvnExec + ".cmd";
                isWindwos = true;
            }

            File okaBaseDir = new File(outputDirectory, "oaktree");
            File okadependencyDir = new File(okaBaseDir, "dependency");
            if (!okadependencyDir.exists()) {
                okadependencyDir.mkdirs();
            }
            okadependencyOutputDir = okadependencyDir.getPath();

            ArtifactItem rootItem = new ArtifactItem(mp.getGroupId(), mp.getArtifactId(), mp.getPackaging(), mp.getVersion());

            File rootPom = mp.getFile();
            int startDeep = 2;
            for (Object obj : mp.getDependencyArtifacts()) {
                DefaultArtifact artifact = (DefaultArtifact) obj;
                ArtifactItem chieldern = ArtifactItem.valueOf(artifact);
                if (filter.isGoOnProcessing(chieldern, startDeep)) {
                    calcDependancy(chieldern, startDeep);
                    rootItem.addChildren(chieldern);
                }
            }



            FileWriter w = null;
            try {
                File okaFile = new File(okaBaseDir,"okatree.txt");
                if(okaFile.exists()) {
                    okaFile.delete();
                }
                w = new FileWriter(okaFile);
                log.info("writing file :  " + okaFile.getPath());
                printArtifactItem(w, rootItem, "");
                log.info("writing complete.");
            }finally {
                StreamUtils.quiteClose(w);
            }
        } catch (Exception ex) {
            getLog().error(ex.getMessage(), ex);
            throw new MojoExecutionException(ex.getMessage(), ex);
        }
    }

    private void printArtifactItem(Writer writer, ArtifactItem item, String intent) throws IOException {

        writer.write(intent);
        writer.write("- ");
        writer.write(item.toYamlStr());
        writer.write("\n");
        String nextIntent = "  " + intent;
        for (ArtifactItem subItem : item.getChildren()) {
            printArtifactItem(writer, subItem, nextIntent);
        }
    }

    private void calcDependancy(ArtifactItem item, int deep) throws IOException {

        String outPutfileStr = okadependencyOutputDir + "/" + item.toArtifactString().replaceAll(":", "-") + ".txt";
        String pomFile = item.getPomFilePath(localRepository.getPath());
        File outputFile = new File(outPutfileStr);

        if (!new File(pomFile).exists()) {
            item.setChildUnkwon(true);
            return;
        }


        if (outputFile.exists()) {
            // TODO 如果时间太久删除
            // TODO 如果是snapeshot删除
        }

        if (!outputFile.exists()) {
            log.info("resove from pom.xml:" + item.toArtifactString());
            // 生成
            CmdExecutor e = new CmdExecutor();
            if(log.isDebugEnabled()) {
                log.debug(mvnExec
                                + " " + "-f"
                                + " " + pomFile
                                + " " + "-Doutput.file=" + outPutfileStr
                                + " " + dependencyArtifacts);
            }

            NormalCmdResult cr = e.execNormalCmd(new String[]{mvnExec
                    , "-f"
                    , pomFile
                    , "-Doutput.file=" + outPutfileStr
                    , dependencyArtifacts}, null, null);

            if(log.isDebugEnabled()) {
                log.info(cr.getStdout());
            }
            if(cr.getStderr() != null && cr.getStderr().length() != 0) {
                log.error(cr.getStderr());
            }
        } else {
            log.info("resove from cache:" + item.toArtifactString());
        }

        if (!outputFile.exists()) {
            log.debug(item.toArtifactString() + " not found children file.");
            item.setChildUnkwon(true);
            return;
        }

        FileReader fr = new FileReader(outputFile);
        BufferedReader bf = new BufferedReader(fr);
        String valueString = null;
        int nextDeep = deep + 1;
        while ((valueString = bf.readLine()) != null) {
            ArtifactItem subItem = ArtifactItem.valueOf(valueString);
            if (filter.isGoOnProcessing(subItem, nextDeep)) {
                calcDependancy(subItem, nextDeep);
                item.addChildren(subItem);
            }
        }
    }

    public String removeUnusedCharInPath(String path) throws MojoExecutionException {
        String result = path;
        if (!isWindwos) {
            // remove  ../
            result = path.replaceAll("(((?!/).)+/\\.\\.(/|$))", "");
            // remove  ./
            result = result.replaceAll("(/|^)((\\./)|(\\.$))", "$1");
            // remove  last /
            result = result.replaceAll("/$", "$1");
        } else {
            // remove  ..\
            result = path.replaceAll("(((?!\\\\).)+\\\\\\.\\.(\\\\|$))", "");
            // remove  .\
            result = result.replaceAll("(\\\\|^)((\\.\\\\)|(\\.$))", "$1");
            // remove  last \
            result = result.replaceAll("\\\\$", "");
        }
        return result;
    }
}
