/**
 * Copyright 2026 Colin Dean
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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(JUnit4.class)
public class WeakSemVerParseTest {

    static ChangelogParser parser;
    static ChangelogData data;

    @BeforeClass
    static public void setup() {
        parser = new ChangelogParser();
        data = parser.parse(TestChangelogs.WEAK_SEMVER);
    }

    @Test
    public void test_parser_gets_all_entries() {
        Assert.assertEquals(6, data.getEntries().size());
    }

    @Test
    public void test_parser_keeps_versions_as_written() {
        List<String> versions = data.getEntries().stream()
                .map(ChangelogEntry::getVersion)
                .collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("Unreleased", "5.11", "5.10", "5.9", "5", "4.3.4"), versions);
    }

    @Test
    public void test_parser_gets_specific_version_without_patch() {
        Assert.assertTrue(data.getEntryForVersion("5.10").isPresent());
        Assert.assertEquals("5.10", data.getEntryForVersion("5.10").get().getVersion());
        Assert.assertFalse(data.getEntryForVersion("5.10.0").isPresent());
    }

    @Test
    public void test_parser_gets_entry_date_after_en_dash() {
        ChangelogEntry entry = data.getEntryForVersion("5.11").get();
        String date = new SimpleDateFormat(ChangelogEntry.EXPECTED_DATE_FORMAT).format(entry.getDate());
        Assert.assertEquals("2023-10-22", date);
    }

    @Test
    public void test_parser_gets_section_content() {
        ChangelogEntry entry = data.getEntryForVersion("5").get();
        ChangelogSection section = entry.getSections().first();
        Assert.assertEquals("Added", section.getName());
        Assert.assertEquals("Major release", section.getItems().get(0));
    }
}
