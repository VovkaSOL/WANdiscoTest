package org.vovkasol;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class StupidDirectoryDuplicator implements EventListener {
    private String activeDir;
    private String passiveDir;

    public StupidDirectoryDuplicator(String activeDir, String passiveDir) {
        this.activeDir = activeDir.replaceAll("\\\\", "//");
        this.passiveDir = passiveDir.replaceAll("\\\\", "//");
        ;
    }

    @Override
    public void event(String file, FileEvents fileEvents) {
        if (fileEvents == FileEvents.FILE_CREATED) {
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
            e.printStackTrace();
        }
    }
}
