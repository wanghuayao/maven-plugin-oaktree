package io.github.wanghuayao.maven.plugin.oaktree;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by wanghuayao on 2017/1/2.
 */
public class OakProperties {

    private final Properties properties = new Properties();

    public OakProperties() throws  IOException {
        properties.load(this.getClass().getResourceAsStream("/oaktree-application.properties"));
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String getVersion(){
        return get("oaktree.version");
    }

}
