package com.illucit.encryptzip;

import com.illucit.encryptzip.password.PasswordController;
import com.illucit.encryptzip.upload.UploadController;
import de.mknaub.appfx.AppFx;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends AppFx {

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;
        stage.initStyle(StageStyle.DECORATED);

        PasswordController passwordController = getController(PasswordController.class);
        passwordController.setOnOk(this::navigateUpload);
        passwordController.setOnCancel(this::cancel);

        Scene scene = new Scene((Parent) passwordController.getView());
        stage.setTitle("Encrypt ZIP");
        String uri = getClass().getResource("app.css").toExternalForm();
        scene.getStylesheets().add(uri);
        stage.getIcons().add(new Image(getClass().getResource("lock.png").toExternalForm()));
        stage.setScene(scene);
        stage.show();
    }

    public void navigateUpload() {
        UploadController uploadController = getController(UploadController.class);
        uploadController.setOnCancel(this::cancel);
        Scene scene = new Scene((Parent) uploadController.getView());
        stage.setScene(scene);
        stage.show();
    }

    public void cancel() {
        stage.close();
    }

    @Override
    public void stop() throws Exception {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
