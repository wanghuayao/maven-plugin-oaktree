package io.github.wanghuayao.maven.plugin.oaktree.parser;

import java.util.regex.Pattern;

/**
 * Created by wanghuayao on 2016/12/24.
 */
public class ProcessingFilter {

    int deep;
    Pattern hate = null;
    Pattern like = null;

    public ProcessingFilter(String like, String hate, int deep){
        if(like != null && like.length() > 0) {
            this.like = Pattern.compile(like);
        }
        if(hate != null && hate.length() > 0) {
            this.hate = Pattern.compile(hate);
        }

        this.deep = deep;
    }

    /**
     *
     * @param item
     * @param deep
     * @return
     */
    public boolean isGoOnProcessing(ArtifactItem item, int deep){
        if(deep > this.deep) {
            return false;
        }
        String value = item.toArtifactString() + ":" + item.getScope();
        if(hate != null && hate.matcher(value).matches()) {
            return false;
        }
        if(like != null) {
            return like.matcher(value).matches();
        }

        return true;
    }

}
