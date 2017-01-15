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
package io.github.wanghuayao.maven.plugin.oaktree.parser;

import org.apache.maven.artifact.Artifact;

import java.util.HashSet;
import java.util.Set;

public class ArtifactItem {
    private final String             groupId;
    private final String             artifactId;
    private final String             version;
    private final String             packaging;
    private final Set<ArtifactItem> children = new HashSet<ArtifactItem>();

    public boolean isChildUnkwon() {
        return childUnkwon;
    }

    public void setChildUnkwon(boolean childUnkwon) {
        this.childUnkwon = childUnkwon;
    }

    private boolean childUnkwon = false;

    public ArtifactItem(String groupId,
                        String artifactId,
                        String packaging,
                        String version){
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.packaging = packaging;
        this.version = version;
    }

    private String              scope;
    private String             classifier;
    private String             comment;

    public ArtifactItem valueOf(ArtifactItem others) {
        return new ArtifactItem(others.getGroupId(),others.getArtifactId(),others.getPackaging(),others.getVersion());
    }


    public final static ArtifactItem valueOf(String strArtifact) {
        String[] parts = strArtifact.split(":");
        ArtifactItem item =  new ArtifactItem(parts[0], parts[1], parts[2], parts[3]);
        if(parts.length >= 5) {
            item.setScope(parts[4]);
        }
        if(parts.length >= 6) {
            item.setClassifier(parts[5]);
        }
        return item;
    }


    public final static ArtifactItem valueOf(Artifact artifact) {
        ArtifactItem item =  new ArtifactItem(artifact.getGroupId(),artifact.getArtifactId(),artifact.getType(),artifact.getVersion());
        item.setClassifier(artifact.getClassifier());
        item.setScope( artifact.getScope());
        return item;
    }

    public String getPomFilePath(String localRepository ) {
        String separator = "/";
        StringBuilder sb = new StringBuilder();
        sb.append(localRepository);
        sb.append(separator).append(this.groupId.replaceAll("\\.",separator));
        sb.append(separator).append(this.artifactId);
        sb.append(separator).append(this.version);
        sb.append(separator).append(this.artifactId).append("-").append(this.version).append(".pom");
        return sb.toString();
    }


    public Set<ArtifactItem> getChildren(){
        return  this.children;
    }

    public void addChildren(ArtifactItem item){
        this.children.add(item);
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public String getPackaging() {
        return packaging;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toArtifactString() {
        StringBuilder sb = new StringBuilder();
        sb.append(groupId).append(":").append(artifactId)
                .append(":").append(packaging).append(":").append(version);
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(groupId).append(":").append(artifactId)
                .append(":").append(packaging).append(":").append(version).append(":").append(scope!=null ? scope:"default").append(":").append(classifier==null ? "":classifier);
        return sb.toString();
    }

    public String toYamlStr() {
        StringBuilder sb = new StringBuilder();
        sb.append(groupId).append(":").append(artifactId)
                .append("\n").append(packaging).append(":").append(version).append(":").append(scope!=null ? scope:"default").append(" ").append(classifier==null ? "":classifier);
        return sb.toString();
    }
    @Override
    public int hashCode() {
        return toArtifactString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof  ArtifactItem)) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }


}
