/**
 * Copyright 2024 Colin Dean, Romano Zabini
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

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class VersionInBracketsParseTest {

    static ChangelogParser parser;
    static ChangelogData data;

    @BeforeClass
    static public void setup() {
        parser = new ChangelogParser();
        data = parser.parse(TestChangelogs.VERSIONS_IN_BRACKETS);
    }

    @Test
    public void test_parser_gets_projectName() {
        Assert.assertEquals("Changelog", data.getProjectName());
    }

    @Test
    public void test_parser_gets_description() {
        Assert.assertTrue(data.getDescription().contains("semver.org"));
        Assert.assertTrue(data.getDescription().contains("notable changes"));
    }

}
