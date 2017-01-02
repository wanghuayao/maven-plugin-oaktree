package io.github.wanghuayao.maven.plugin.oaktree.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by wanghuayao on 2016/12/21.
 */
public class StreamUtils {
    public final static boolean quiteClose(Closeable closeable) {
        if(closeable == null) {
            return true;
        }
        try {
            closeable.close();
            return true;
        }catch (IOException ex) {
            return false;
        }
    }
}
