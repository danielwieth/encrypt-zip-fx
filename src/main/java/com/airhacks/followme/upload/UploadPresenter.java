package com.airhacks.followme.upload;

import com.airhacks.followme.engine.NavigatablePresenter;
import com.airhacks.followme.engine.ZipService;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import javax.inject.Inject;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 *
 * @author Daniel Wieth
 */
public class UploadPresenter extends NavigatablePresenter implements Initializable {

    @FXML private TextField outputPath;
    @FXML private Button outputPathChooser;

    @FXML private Label statusLabel;
    @FXML private Button exitButton;
    @FXML private FontIcon uploadIcon;

    @Inject private ZipService zipService;

    //<editor-fold defaultstate="collapsed" desc="messages">
    @Inject private String done;
    @Inject private String working;
    @Inject private String quit;
    @Inject private String forceQuit;
    //</editor-fold>

    private Path outputDirectoy;
    private StringProperty outputDirectoryProperty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        uploadIcon.setIconLiteral("mdi-cloud-upload");
        // Initialize with current directory
        File currentDirectory = new File(System.getProperty("user.dir"));
        outputDirectoy = Paths.get(currentDirectory.getAbsolutePath());
        outputDirectoryProperty = new SimpleStringProperty();
        outputDirectoryProperty.set(outputDirectoy.toAbsolutePath().toString());
        outputPath.textProperty().bind(outputDirectoryProperty);
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
            zipService.zip(db.getFiles(), outputDirectoy,
                    () -> onSucceeded(), () -> onScheduled());
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private Void onSucceeded() {
        statusLabel.setText(done);
//        exitButton.setDisable(false);
        exitButton.setText(quit);
        return null;
    }

    private Void onScheduled() {
        statusLabel.setText(working);
//        exitButton.setDisable(true);
        exitButton.setText(forceQuit);
        return null;
    }

    public void onOutputPathChooserClick(ActionEvent e) {
        DirectoryChooser fileChooser = new DirectoryChooser();
        Window window = outputPathChooser.getScene().getWindow();
        File outputFile = fileChooser.showDialog(window);

        if (outputFile != null && outputFile.isDirectory()) {
            this.outputDirectoy = Paths.get(outputFile.getAbsolutePath());
            outputDirectoryProperty.set(outputDirectoy.toAbsolutePath().toString());
        }
    }

}
