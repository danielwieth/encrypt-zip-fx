package com.airhacks.followme.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.inject.Inject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Daniel Wieth
 */
public class ZipService {

    private static final DateFormat FILENAME_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_hh-mm");

    @Inject private PasswordHolder passwordHolder;

    private final ZipBackgroundService zipBackgroundService = new ZipBackgroundService();

    public void zip(List<File> files, Supplier<Void> onSucceeded, Supplier<Void> onScheduled) {
        // TODO: Register event handlers
        // TODO: Only one file at a time
        zipBackgroundService.setFiles(files);
        zipBackgroundService.setOnSucceeded(e -> onSucceeded.get());
        zipBackgroundService.setOnScheduled(e -> onScheduled.get());
        zipBackgroundService.getProgress();
        zipBackgroundService.start();
    }

    private void zipImpl(List<File> files) throws IOException, ZipException {

        String fileName = getFileName(files);

        try (FileOutputStream dest = new FileOutputStream(fileName); ZipOutputStream out = new ZipOutputStream(dest)) {

            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);

                ZipParameters zipParams = new ZipParameters();
                zipParams.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
                zipParams.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
                zipParams.setEncryptFiles(true);

                if (passwordHolder.encrypt()) {
                    zipParams.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
                    zipParams.setPassword(passwordHolder.getPassword());
                }

                zipParams.setSourceExternalStream(true);
                zipParams.setFileNameInZip(file.getName());

                out.putNextEntry(new File(file.getName()), zipParams);
                out.write(Files.readAllBytes(file.toPath()));
                out.closeEntry();
            }
            out.finish();
        }
    }

    private String getFileName(List<File> files) {
        String fileName = "archive";
        if (files.size() == 1) {
            fileName = files.stream().findFirst().map(File::getName).get();
        }
        fileName = FilenameUtils.getBaseName(fileName) + "_" + FILENAME_DATE_FORMAT.format(new Date()) + ".zip";
        return fileName;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    private class ZipBackgroundService extends Service<Void> {

        private List<File> files;

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override protected Void call() {
                    try {
                        zipImpl(files);
                    } catch (IOException | ZipException ex) {
                        Logger.getLogger(ZipService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return null;
                }              
            };
        }
    }
}
