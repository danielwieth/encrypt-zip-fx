package com.illucit.encryptzip.engine;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.ResourceBundle;
import static java.util.ResourceBundle.getBundle;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Daniel Wieth
 */
@Slf4j
public class Messages {

    public static final ResourceBundle RESOURCE_BUNDLE = getBundle("com.illucit.encryptzip.messages");

    public static String getMessage(String key, Object... args) {
        String msg = null;
        try {
            msg = RESOURCE_BUNDLE.getString(key);
            if (args != null) {
                MessageFormat formatter = new MessageFormat(msg);
                msg = formatter.format(args);
            }
        } catch (Exception ex) {
            log.error("Missing key: {}", key);
        }
        return msg;
    }

    public static String getKey(String msg) {
        Enumeration<String> keys = RESOURCE_BUNDLE.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String keyValue = RESOURCE_BUNDLE.getString(key);
            if (keyValue.equals(msg)) {
                return key;
            }
        }
        log.error("Missing key for msg: {}", msg);
        return null;
    }
}
