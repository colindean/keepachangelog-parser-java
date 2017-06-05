/**
 * This file is a part of changelog-parser and is licensed as detailed in LICENSE.md.
 */
package cx.cad.keepachangelog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class ChangelogEntry {
    private String version; //XXX this should be some kind of real semver object that has equality and comparison
    private Date date;
    private Set<ChangelogSection> sections = new TreeSet<>();

    private ChangelogEntry(String version, Date date, Set<ChangelogSection> sections) {
        this.version = version;
        this.date = date;
        this.sections.addAll(sections);
    }

    public String getVersion() {
        return version;
    }
    public Date getDate() {
        return date;
    }
    public Set<ChangelogSection> getSections() {
        return sections;
    }

    public static class Builder {
        String version = "";
        Date date;
        Set<ChangelogSection> sections = new TreeSet<>();

        static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        public Builder() {}

        public Builder version(String version) {
            this.version = version;
            return this;
        }
        public Builder date(String date) throws ParseException {
            this.date = dateFormat.parse(date);
            return this;
        }
        public Builder addSection(ChangelogSection section) {
            sections.add(section);
            return this;
        }
        public ChangelogEntry build() {
            return new ChangelogEntry(version, date, sections);
        }
    }
}
