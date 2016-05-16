package com.illucit.encryptzip;

import com.airhacks.afterburner.injection.Injector;
import com.illucit.encryptzip.password.PasswordPresenter;
import com.illucit.encryptzip.password.PasswordView;
import com.illucit.encryptzip.upload.UploadPresenter;
import com.illucit.encryptzip.upload.UploadView;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    private Stage stage;
    private final PasswordView passwordView;
    private final UploadView uploadView;
    
    public App() {
        passwordView = new PasswordView(f -> null);
        uploadView = new UploadView(f -> null);
        
        PasswordPresenter passwordPresenter = (PasswordPresenter) passwordView.getPresenter();
        passwordPresenter.setOnOk(this::navigateUpload);
        passwordPresenter.setOnCancel(this::cancel);
        
        UploadPresenter uploadPresenter = (UploadPresenter) uploadView.getPresenter();
        uploadPresenter.setOnCancel(this::cancel);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        
        /*
         * Properties of any type can be easily injected.
         */
        LocalDate date = LocalDate.of(4242, Month.JULY, 21);
        Map<Object, Object> customProperties = new HashMap<>();
        customProperties.put("date", date);
        
        /*
         * any function which accepts an Object as key and returns
         * and return an Object as result can be used as source.
         */
        Injector.setConfigurationSource(customProperties::get);

        System.setProperty("happyEnding", " Enjoy the flight!");
        
        Scene scene = new Scene(passwordView.getView());
        stage.setTitle("followme.fx");
        String uri = getClass().getResource("app.css").toExternalForm();
        scene.getStylesheets().add(uri);
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
