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

import com.github.zafarkhaja.semver.Version;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A model for each release in a changelog file. Each release is a second-level
 * heading in the changelog Markdown file. The heading text should have a
 * version number and date of release with an optional [YANKED] tag following
 * the date, OR be simply "Unreleased".
 *
 * The description of the entry is any paragraph data after the second-level
 * heading which starts the entry but before the first third-level heading
 * describing the changes within the release.
 *
 * {@link ChangelogSection} models the sections inside third-level headings.
 */
public class ChangelogEntry implements Comparable<ChangelogEntry> {
    /**
     * The date format required for each entry specifying a date.
     */
    static public String EXPECTED_DATE_FORMAT = "yyyy-MM-dd";
    private Version version;
    private Date date;
    private SortedSet<ChangelogSection> sections = new TreeSet<>();
    private String description;
    private boolean isUnreleased;
    private boolean wasYanked;

    private ChangelogEntry(Version version, Date date, String description, boolean isUnreleased, boolean wasYanked, Set<ChangelogSection> sections) {
        this.version = version;
        this.date = date;
        this.description = description;
        this.isUnreleased = isUnreleased;
        this.wasYanked = wasYanked;
        this.sections.addAll(sections);
    }

    /**
     * Retrieve the version number of the entry
     *
     * @return the version as a string or "Unreleased"
     */
    public String getVersion() {
        return isUnreleased ? "Unreleased" : version.toString();
    }

    /**
     * Retrieve the release date of the entry
     *
     * @return the release date or null if no date is set
     */
    public Date getDate() {
        return date;
    }

    /**
     * Retrieve the sections described by the third-level headings within the
     * changelog document
     *
     * @return a set of sections sorted by the configured order of appearance
     */
    public SortedSet<ChangelogSection> getSections() {
        return sections;
    }

    /**
     * Retrieves an optional description stored between the second-level heading
     * defining the entry and the first third-level section heading.
     *
     * @return a description or null
     */
    public String getDescription() { return description; }

    /**
     * Describes if the entry is considered unreleased. This is when the version
     * is written as "Unreleased".
     *
     * @return true if the entry version/date heading is "Unreleased"
     */
    public boolean isUnreleased() { return isUnreleased; }

    /**
     * Describes if the entry is considered yanked. Entries marked in the
     * changelog with [YANKED] in the version/date heading have been yanked. A
     * yanked released should have a description indicating why it was yanked
     * but this description is not required.
     *
     * @return true if the entry was marked with the [YANKED] tag
     */
    public boolean wasYanked() {
        return wasYanked;
    }

    /**
     * Compares two entries. Unreleased entries are always first followed by
     * other entries in descending order of version number, latest to oldest.
     *
     * @param o the other entry
     * @return -1 if it's newer, 0 if it's the same, 1 if it's older.
     */
    @Override
    public int compareTo(ChangelogEntry o) {
        if(isUnreleased || o.isUnreleased) {
            if(isUnreleased && !o.isUnreleased){
                return -1;
            }
            if(isUnreleased){
                return 0;
            }
            return 1;
        }
        return descendingOrder(version.compareTo(o.version));
    }
    private int descendingOrder(int i) { return -i; }

    public static class Builder {
        private Version version;
        private Date date;
        private SortedSet<ChangelogSection> sections = new TreeSet<>();
        private String description;
        private boolean isUnreleased;
        private boolean wasYanked;

        public static DateFormat dateFormat = new SimpleDateFormat(EXPECTED_DATE_FORMAT);

        public Builder() {}

        public Builder version(String version) {
            if(version == null || "Unreleased".equals(version)){
                return setUnreleased();
            } else {
                this.version = Version.valueOf(version);
                return this;
            }
        }
        public Builder date(String date) throws ParseException {
            if(date != null) {
                this.date = dateFormat.parse(date);
            }
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder addSection(ChangelogSection section) {
            sections.add(section);
            return this;
        }
        public Builder setUnreleased() {
            isUnreleased = true;
            return this;
        }
        public Builder wasYanked() {
            wasYanked = true;
            return this;
        }
        public ChangelogEntry build() {
            return new ChangelogEntry(version, date, description, isUnreleased, wasYanked, sections);
        }
    }
}
