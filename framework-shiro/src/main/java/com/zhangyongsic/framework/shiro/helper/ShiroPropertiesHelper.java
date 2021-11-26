package com.zhangyongsic.framework.shiro.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author zhang yong
 */
public class ShiroPropertiesHelper {

    private Logger logger = LoggerFactory.getLogger(ShiroPropertiesHelper.class);

    private Properties props;

    private static final ShiroPropertiesHelper INSTANCE = new ShiroPropertiesHelper();

    private ShiroPropertiesHelper() {
        try {
            InputStream inStream = ShiroPropertiesHelper.class.getResourceAsStream("/shiro.properties");
            props = new Properties();
            props.load(inStream);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    public static ShiroPropertiesHelper getInstance() {
        return INSTANCE;
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }

}
