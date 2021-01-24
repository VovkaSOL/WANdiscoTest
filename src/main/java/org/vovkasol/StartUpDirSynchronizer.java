package org.vovkasol;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.Files.copy;
import static java.nio.file.Files.createDirectories;

public class StartUpDirSynchronizer {
    private String from;
    private String to;

    public StartUpDirSynchronizer(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public void sync() {
        try {
            copyFolder(Paths.get(from), Paths.get(to), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            //TODO add logic
            e.printStackTrace();
        }
    }

    public void copyFolder(Path source, Path target, CopyOption... options)
            throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                createDirectories(target.resolve(source.relativize(dir)));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                copy(file, target.resolve(source.relativize(file)), options);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
