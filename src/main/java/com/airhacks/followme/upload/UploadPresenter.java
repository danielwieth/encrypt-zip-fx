package com.airhacks.followme.upload;

import com.airhacks.followme.engine.NavigatablePresenter;
import com.airhacks.followme.engine.ZipService;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javax.inject.Inject;

/**
 *
 * @author Daniel Wieth
 */
public class UploadPresenter extends NavigatablePresenter {

    @Inject private ZipService zipService;

    public void dragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
        event.consume();
    }

    public void dragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            zipService.zip(db.getFiles());
        }
        event.setDropCompleted(success);
        event.consume();
    }

}
