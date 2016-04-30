package com.airhacks.followme.engine;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.inject.Inject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Daniel Wieth
 */
public class ZipService {

    @Inject private PasswordHolder passwordHolder;

    private final ZipBackgroundService zipBackgroundService = new ZipBackgroundService();

    public void zip(List<File> files) {
        zipBackgroundService.setFiles(files);
        zipBackgroundService.start();;
    }

    private void zipImpl(List<File> files) throws IOException {

        try (FileOutputStream dest = new FileOutputStream("myfigs.zip");
                ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest))) {

            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                System.out.println("Adding: " + file.getName());
                ZipEntry entry = new ZipEntry(file.getName());
                out.putNextEntry(entry);
                out.write(Files.readAllBytes(file.toPath()));
            }
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    private class ZipBackgroundService extends Service<Void> {

        private List<File> files;

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() {
                    try {
                        zipImpl(files);
                    } catch (IOException ex) {
                        Logger.getLogger(ZipService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return null;
                }
            };
        }
    }
}
