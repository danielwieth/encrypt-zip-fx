package com.illucit.encryptzip;

import com.airhacks.afterburner.injection.Injector;
import com.illucit.encryptzip.password.PasswordPresenter;
import com.illucit.encryptzip.password.PasswordView;
import com.illucit.encryptzip.upload.UploadPresenter;
import com.illucit.encryptzip.upload.UploadView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    private Stage stage;
    private final PasswordView passwordView;
    private final UploadView uploadView;
    
    public App() {
        passwordView = new PasswordView();
        uploadView = new UploadView();
        
        PasswordPresenter passwordPresenter = (PasswordPresenter) passwordView.getPresenter();
        passwordPresenter.setOnOk(this::navigateUpload);
        passwordPresenter.setOnCancel(this::cancel);
        
        UploadPresenter uploadPresenter = (UploadPresenter) uploadView.getPresenter();
        uploadPresenter.setOnCancel(this::cancel);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        this.stage = stage;
        stage.initStyle(StageStyle.DECORATED);
        
        Scene scene = new Scene(passwordView.getView());
        stage.setTitle("Encrypt ZIP");
        String uri = getClass().getResource("app.css").toExternalForm();
        scene.getStylesheets().add(uri);
//        stage.getIcons().add(new Image("/com/illucit/encryptzip/lock.png"));
        stage.getIcons().add(new Image(getClass().getResource("lock.png").toExternalForm()));
        stage.setScene(scene);
        stage.show();
    }
    
    public void navigateUpload() {
        Scene scene = new Scene(uploadView.getView());
        stage.setScene(scene);
        stage.show();
    }
    
    public void cancel() {
        stage.close();
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
