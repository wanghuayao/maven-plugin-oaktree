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
        writer.write(StrUtils.defaultStr(artifact.getVersion() , "none"));
        writer.write(":");
        writer.write(StrUtils.defaultStr(artifact.getScope(), "none"));
        writer.write(":");
        if (artifact.getClassifier() != null) {
            writer.write(artifact.getClassifier());
        }
    }
}
