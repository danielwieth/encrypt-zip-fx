package com.airhacks.followme;

/*
 * #%L
 * igniter
 * %%
 * Copyright (C) 2013 - 2016 Adam Bien
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.airhacks.afterburner.injection.Injector;
import com.airhacks.followme.password.PasswordPresenter;
import com.airhacks.followme.password.PasswordView;
import com.airhacks.followme.upload.UploadPresenter;
import com.airhacks.followme.upload.UploadView;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author adam-bien.com
 */
public class App extends Application {

    private Stage stage;
    private PasswordView passwordView;
    private UploadView uploadView;
    
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
