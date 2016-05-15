package com.airhacks.followme.upload;

import com.airhacks.followme.engine.NavigatablePresenter;
import com.airhacks.followme.engine.ZipService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javax.inject.Inject;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 *
 * @author Daniel Wieth
 */
public class UploadPresenter extends NavigatablePresenter implements Initializable {

    @FXML private Label statusLabel;
    @FXML private Button exitButton;
    @FXML private FontIcon uploadIcon;

    @Inject private ZipService zipService;
    @Inject private String done;
    @Inject private String working;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        uploadIcon.setIconLiteral("mdi-cloud-upload");
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
