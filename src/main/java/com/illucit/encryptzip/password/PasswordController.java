package com.illucit.encryptzip.password;

import com.illucit.encryptzip.engine.PasswordHolder;
import de.mknaub.appfx.annotations.Controller;
import de.mknaub.appfx.controller.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Daniel Wieth
 */
@Controller(url = "/com/illucit/encryptzip/fxml/password.fxml")
public class PasswordController extends AbstractController {

    @FXML private TextField password;
    @FXML private TextField confirmPassword;
    @FXML private Label hint;

    private PasswordHolder passwordHolder;
    private String passwordsUnequal;
    private String passwordsEqual;

    private Runnable onOk;
    private Runnable onCancel;

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

    public void ok() {
        if (passwordsEqual()) {
            passwordHolder.setPassword(password.getCharacters().toString());
        }
        onOk.run();
    }

    public void cancel() {
        onCancel.run();
    }

    public void setOnOk(Runnable onOk) {
        this.onOk = onOk;
    }

    public void setOnCancel(Runnable onCancel) {
        this.onCancel = onCancel;
    }

}
