package cx.cad.keepachangelog;

import java.io.File;
import java.util.Arrays;

import static cx.cad.keepachangelog.FileTools.listToPath;
import static cx.cad.keepachangelog.FileTools.readFile;

public class TestChangelogs {
    private static final String RESOURCES_DIRECTORY = listToPath(Arrays.asList("src", "test", "resources"));
    public static final String BASIC = readTestFile("CHANGELOG-basic.md");

    private static String readTestFile(String testFileName) {
        String filename = listToPath(Arrays.asList(RESOURCES_DIRECTORY, testFileName));
        return readFile(new File(filename));
    }
}
