/**
 * Copyright 2017 Colin Dean
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
