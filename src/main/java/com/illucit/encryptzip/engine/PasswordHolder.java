package com.illucit.encryptzip.engine;

import de.mknaub.appfx.annotations.Service;
import de.mknaub.appfx.services.AbstractService;
import lombok.Data;

/**
 *
 * @author Daniel Wieth
 */
@Data
@Service
public class PasswordHolder extends AbstractService {

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
