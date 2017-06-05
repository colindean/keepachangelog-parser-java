package cx.cad.keepachangelog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileTools {
    public static String listToPath(List<String> pathAsList) {
        return String.join(File.separator, pathAsList);
    }

    public static String readFile(File file) {
        try {
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Something happened while loading %s", file.getAbsolutePath()), e);
        }
    }
}
