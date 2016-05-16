package com.illucit.encryptzip.engine;

import lombok.Data;

/**
 *
 * @author Daniel Wieth
 */
@Data
public class PasswordHolder {

    private String password;

    /**
     * Returns true, if encryption is enabled. Encryption is enabled, when the
     * password is not null or empty.
     *
     * @return true, if encryption is enabled
     */
    public boolean encrypt() {
        return password != null && !password.isEmpty();
    }

}
