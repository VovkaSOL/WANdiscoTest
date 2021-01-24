package org.vovkasol;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StupidDirectoryDuplicator implements EventListener {
    private String activeDir;
    private String passiveDir;

    public StupidDirectoryDuplicator(String activeDir, String passiveDir) {
        this.activeDir = activeDir.replaceAll("\\\\", "//");
        this.passiveDir = passiveDir.replaceAll("\\\\", "//");
    }

    @Override
    public void event(String file, FileEvents fileEvents) {
        if (fileEvents == FileEvents.FILE_CREATED) {
            File file1 = new File(file);
            if (file1.isDirectory()) {
                try {
                    file = file.replaceAll("\\\\", "//");
                    Files.createDirectories(Paths.get(file.replace(activeDir, passiveDir)));
                    return;
                } catch (IOException e) {
                    e.printStackTrace();//TODO
                }
            }
            copyFileFromActiveToPassive(file);
        }
        if (fileEvents == FileEvents.FILE_DELETED) {
            deleteFileFromPassive(file);
        }
        if (fileEvents == FileEvents.FILE_MODIFIED) {
            //TODO find fast merge algorithm
            File file1 = new File(file);
            if (file1.isDirectory()) { //TODO modify folder event
                return;
            }
            deleteFileFromPassive(file);
            copyFileFromActiveToPassive(file);
        }
    }

    private void copyFileFromActiveToPassive(String file) {
        //TODO fix // and \\ hardcode
        file = file.replaceAll("\\\\", "//");
        //TODO make error handling when copy crash
        String diffFileName = file.replace(activeDir, "").replaceAll("\\\\", "//");
        String newFileName = passiveDir + diffFileName;
        String newFileDirName = newFileName.substring(0, newFileName.lastIndexOf("//") + 2);
        try {
            FileUtils.copyFileToDirectory(new File(file), new File(newFileDirName));
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    private void deleteFileFromPassive(String fileFromActive) {
        //TODO fix // and \\ hardcode
        fileFromActive = fileFromActive.replaceAll("\\\\", "//");
        //TODO make error handling when copy crash
        String diffFileName = fileFromActive.replace(activeDir, "").replaceAll("\\\\", "//");
        String passiveFileName = passiveDir + diffFileName;
        FileUtils.deleteQuietly(new File(passiveFileName));
    }
}
