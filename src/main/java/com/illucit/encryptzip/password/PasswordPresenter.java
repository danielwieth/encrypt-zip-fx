package com.illucit.encryptzip.password;

import com.illucit.encryptzip.engine.NavigatablePresenter;
import com.illucit.encryptzip.engine.PasswordHolder;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.inject.Inject;

/**
 *
 * @author Daniel Wieth
 */
public class PasswordPresenter extends NavigatablePresenter {

    @FXML private TextField password;
    @FXML private TextField confirmPassword;
    @FXML private Label hint;

    @Inject private PasswordHolder passwordHolder;
    @Inject private String passwordsUnequal;
    @Inject private String passwordsEqual;

    public void checkPasswords() {
        if (!passwordsEqual()) {
            hint.setText(passwordsUnequal);
        } else {
            hint.setText(passwordsEqual);
        }
    }

    private boolean passwordsEqual() {
        return password.getText().equals(confirmPassword.getText());
    }

    @Override
    public void ok() {
        if (passwordsEqual()) {
            passwordHolder.setPassword(password.getCharacters().toString());
            super.ok();
        }
    }

}
