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


import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RunWith(JUnit4.class)
public class YankedParseTest {
    static ChangelogParser parser;
    static ChangelogData data;

    private static final Date DATE_2017_JUNE_6 = new Date(2017 - 1900, 5, 6); //XXX this is so bad


    @BeforeClass
    static public void setup() {
        parser = new ChangelogParser();
        data = parser.parse(TestChangelogs.YANKED);
    }

    @Test
    public void test_parser_gets_projectName() {
        Assert.assertEquals("Yanked test", data.getProjectName());
    }
    @Test public void test_parser_gets_description() {
        Assert.assertTrue(data.getDescription().contains("yanked release"));
    }
    @Test public void test_parser_gets_entry_date_for_yanked_entry() {
        ChangelogEntry yanked = data.getEntryForVersion("1.1.0").get();
        Assert.assertEquals(DATE_2017_JUNE_6, yanked.getDate());
    }
    @Test public void test_parser_gets_yanked_description() {
        ChangelogEntry yanked = data.getEntryForVersion("1.1.0").get();
        Assert.assertTrue(yanked.getDescription().contains("quantum interference"));
    }
    @Test public void test_parser_gets_yanked_property() {
        data.getEntries().forEach(entry -> {
            if("1.1.0".equals(entry.getVersion())){
                Assert.assertTrue("A yanked entry was not marked as yanked", entry.wasYanked());
            } else {
                Assert.assertFalse("An unyanked entry was marked as yanked", entry.wasYanked());
            }
        });
    }

    @Test
    public void test_parser_gets_yanked_section_content() {
        ChangelogEntry yanked = data.getEntryForVersion("1.1.0").get();
        ChangelogSection removed = yanked.getSections().stream().findFirst().get();
        Assert.assertEquals("Removed", removed.getName());
        Assert.assertEquals(1, removed.getItems().size());
    }
    @Test
    public void test_parser_sets_unreleased_property() {
        Assert.assertTrue(data.getEntryForVersion("Unreleased").get().isUnreleased());
        Assert.assertFalse(data.getEntryForVersion("1.1.0").get().isUnreleased());
    }
}
