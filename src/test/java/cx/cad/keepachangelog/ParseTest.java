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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RunWith(JUnit4.class)
public class ParseTest {

    public static final Date DATE_2017_JUNE_6 = new Date(2017 - 1900, 5, 6); //XXX this is so bad

    @Test
    public void test_parser_instantiation() {
        new ChangelogParser();
    }

    @Test
    public void test_clean_parse() {
        ChangelogParser parser = a_parser();
        parser.parse(TestChangelogs.BASIC);
    }

    @Test
    public void test_parser_gets_projectName() {
        ChangelogParser parser = a_parser();
        ChangelogData data = parser.parse(TestChangelogs.BASIC);
        Assert.assertEquals("project name", data.getProjectName());
    }
    @Test public void test_parser_gets_description() {
        ChangelogParser parser = a_parser();
        ChangelogData data = parser.parse(TestChangelogs.BASIC);
        Assert.assertTrue(data.getDescription().contains("semver.org"));
        Assert.assertTrue(data.getDescription().contains("notable changes"));
    }
    @Test public void test_parser_gets_entry_date() {
        ChangelogParser parser = a_parser();
        ChangelogData data = parser.parse(TestChangelogs.BASIC);
        Iterator<ChangelogEntry> it = data.getEntries().iterator();
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals(DATE_2017_JUNE_6, it.next().getDate());
    }
    @Test public void test_parser_gets_entry_version() {
        ChangelogParser parser = a_parser();
        ChangelogData data = parser.parse(TestChangelogs.BASIC);
        Iterator<ChangelogEntry> it = data.getEntries().iterator();
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals("1.1.0", it.next().getVersion());
    }
    @Test public void test_parser_gets_three_sections_for_1p0p0() {
        ChangelogParser parser = a_parser();
        ChangelogData data = parser.parse(TestChangelogs.BASIC);
        ChangelogEntry[] entries = data.getEntries().toArray(new ChangelogEntry[0]);
        ChangelogEntry lastEntry = entries[entries.length-1];
        Assert.assertEquals("1.0.0", lastEntry.getVersion());
        Assert.assertEquals(3, lastEntry.getSections().size());
        ChangelogSection[] sections = lastEntry.getSections().toArray(new ChangelogSection[0]);
        List<String> items = sections[1].getItems();
        Assert.assertEquals(2, items.size());
    }

    @Test
    public void test_parser_gets_changed_content() {
        ChangelogParser parser = a_parser();
        ChangelogData data = parser.parse(TestChangelogs.BASIC);
        ChangelogEntry[] entries = data.getEntries().toArray(new ChangelogEntry[0]);
        ChangelogEntry lastEntry = entries[entries.length-1];
        ChangelogSection[] sections = lastEntry.getSections().toArray(new ChangelogSection[0]);
        List<String> items = sections[1].getItems();

        Assert.assertTrue(items.get(0).contains("Frobnosticators"));
    }
    @Test
    public void test_parser_gets_specific_version() {
        ChangelogParser parser = a_parser();
        ChangelogData data = parser.parse(TestChangelogs.BASIC);
        Assert.assertEquals("1.1.0", data.getEntryForVersion("1.1.0").get().getVersion());
        Assert.assertEquals("1.0.0", data.getEntryForVersion("1.0.0").get().getVersion());
    }
    @Test
    public void test_parser_does_not_get_specific_version_when_missing() {
        ChangelogParser parser = a_parser();
        ChangelogData data = parser.parse(TestChangelogs.BASIC);
        Assert.assertEquals(Optional.empty(), data.getEntryForVersion("9.1.1"));
    }


    private ChangelogParser a_parser() {
        return new ChangelogParser();
    }
}
