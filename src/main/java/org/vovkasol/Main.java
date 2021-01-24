package org.vovkasol;

import org.vovkasol.watcher.WatchDir;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * WANdisco test
 */
public class Main {
    public static void main(String[] args) throws IOException {
        //TODO validate input
        //TODO test Watcher with symlink (maybe problem)
        //sFileUtils.copyFileToDirectory(new File("C://1//dfd.txt"),new File("C://2//"));
        new WatchDir(Paths.get(args[0]), true, new StupidDirectoryDuplicator(args[0], args[1])).processEvents();
    }
}
