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
import java.util.TreeSet;

public class ChangelogEntry implements Comparable<ChangelogEntry> {
    static public String EXPECTED_DATE_FORMAT = "yyyy-MM-dd";
    private Version version;
    private Date date;
    private Set<ChangelogSection> sections = new TreeSet<>();
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

    public String getVersion() {
        return isUnreleased ? "Unreleased" : version.toString();
    }
    public Date getDate() {
        return date;
    }
    public Set<ChangelogSection> getSections() {
        return sections;
    }
    public String getDescription() { return description; }
    public boolean isUnreleased() { return isUnreleased; }
    public boolean wasYanked() {
        return wasYanked;
    }

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
        private Set<ChangelogSection> sections = new TreeSet<>();
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
