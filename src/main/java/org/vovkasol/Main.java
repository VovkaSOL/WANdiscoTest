package org.vovkasol;

import org.vovkasol.watcher.WatchDir;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * WANdisco test
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //TODO validate input
        //TODO test Watcher with symlink (maybe problem)
        //TODO very stupid start sync
        new StartUpDirSynchronizer(args[0], args[1]).sync();
        new StartUpDirSynchronizer(args[1], args[0]).sync();
        new WatchDir(Paths.get(args[0]), true, new StupidDirectoryDuplicator(args[0], args[1])).processEvents();
    }
}
