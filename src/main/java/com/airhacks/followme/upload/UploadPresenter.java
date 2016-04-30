package com.airhacks.followme.upload;

import com.airhacks.followme.engine.NavigatablePresenter;
import com.airhacks.followme.engine.ZipService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javax.inject.Inject;

/**
 *
 * @author Daniel Wieth
 */
public class UploadPresenter extends NavigatablePresenter {

    @FXML private Label dragArea;

    @Inject private ZipService zipService;

    public void dragOver(DragEvent event) {
        System.out.println("com.airhacks.followme.upload.UploadPresenter.dragOver()");
        event.acceptTransferModes(TransferMode.ANY);
        event.consume();
    }

    public void dragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            List<File> files = db.getFiles();
            for (File file : files) {
                String filePath = file.getAbsolutePath();
                System.out.println("file path: " + filePath);
            }
//            try {
            zipService.zip(db.getFiles());
//            } catch (IOException ex) {
//                Logger.getLogger(UploadPresenter.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

    public void dragEntered(DragEvent event) {
        System.out.println("com.airhacks.followme.upload.UploadPresenter.dragEntered()");
    }

}
