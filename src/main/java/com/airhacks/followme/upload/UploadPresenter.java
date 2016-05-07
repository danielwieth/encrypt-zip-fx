package com.airhacks.followme.upload;

import com.airhacks.followme.engine.NavigatablePresenter;
import com.airhacks.followme.engine.ZipService;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author Daniel Wieth
 */
public class UploadPresenter extends NavigatablePresenter {

    @FXML private Label statusLabel;
    @FXML private Button exitButton;
    @FXML private ProgressIndicator uploadProgress;
    
    @Inject private ZipService zipService;
    @Inject private String done;
    @Inject private String working;
    
    @PostConstruct
    public void init() {
        
    }

    public void dragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
        event.consume();
    }

    public void dragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            zipService.zip(db.getFiles(), () -> onSucceeded(), () -> onScheduled());
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private Void onSucceeded() {
        statusLabel.setText(done);
        exitButton.setDisable(false);
        return null;
    }

    private Void onScheduled() {
        statusLabel.setText(working);
        exitButton.setDisable(true);
        return null;
    }
}
