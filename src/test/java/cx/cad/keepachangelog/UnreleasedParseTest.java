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
public class UnreleasedParseTest {
    static ChangelogParser parser;
    static ChangelogData data;

    @BeforeClass
    static public void setup() {
        parser = new ChangelogParser();
        data = parser.parse(TestChangelogs.UNRELEASED);
    }

    @Test
    public void test_parser_gets_projectName() {
        Assert.assertEquals("Unreleased test", data.getProjectName());
    }
    @Test public void test_parser_gets_description() {
        Assert.assertTrue(data.getDescription().contains("unreleased section"));
    }
    @Test public void test_parser_gets_null_entry_date_for_unreleased_entry() {
        Iterator<ChangelogEntry> it = data.getEntries().iterator();
        Assert.assertTrue(it.hasNext());
        Assert.assertNull(it.next().getDate());
    }
    @Test public void test_parser_gets_entry_version_for_unreleased_entry() {
        Iterator<ChangelogEntry> it = data.getEntries().iterator();
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals("Unreleased", it.next().getVersion());
    }
    @Test public void test_parser_gets_unreleased() {
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
        ChangelogEntry[] entries = data.getEntries().toArray(new ChangelogEntry[0]);
        ChangelogEntry lastEntry = entries[entries.length-1];
        ChangelogSection[] sections = lastEntry.getSections().toArray(new ChangelogSection[0]);
        List<String> items = sections[1].getItems();

        Assert.assertTrue(items.get(0).contains("Frobnosticators"));
    }
    @Test
    public void test_parser_gets_specific_version_with_unreleased_entry() {
        Assert.assertEquals("Unreleased", data.getEntryForVersion("Unreleased").get().getVersion());
        Assert.assertEquals("1.0.0", data.getEntryForVersion("1.0.0").get().getVersion());
    }
    @Test
    public void test_parser_sets_unreleased_property() {
        Assert.assertTrue(data.getEntryForVersion("Unreleased").get().isUnreleased());
        Assert.assertFalse(data.getEntryForVersion("1.0.0").get().isUnreleased());
    }
    @Test
    public void test_parser_does_not_get_specific_version_when_missing() {
        Assert.assertEquals(Optional.empty(), data.getEntryForVersion("9.1.1"));
    }
}
