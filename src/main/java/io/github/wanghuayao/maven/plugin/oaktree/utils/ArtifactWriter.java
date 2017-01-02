package io.github.wanghuayao.maven.plugin.oaktree.utils;

import org.apache.maven.artifact.Artifact;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by wanghuayao on 2016/12/24.
 */
public class ArtifactWriter {

    public final static void artifactToWriter(Artifact artifact, Writer writer) throws IOException {

        writer.write(artifact.getGroupId());
        writer.write(":");
        writer.write(artifact.getArtifactId());
        writer.write(":");
        writer.write(artifact.getType());
        writer.write(":");
        writer.write(artifact.getVersion());
        writer.write(":");
        writer.write(artifact.getScope());
        writer.write(":");
        if(artifact.getClassifier() != null) {
            writer.write(artifact.getClassifier());
        }
    }
}
