/**
 * This file is a part of changelog-parser and is licensed as detailed in LICENSE.md.
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
    private Version version;
    private Date date;
    private Set<ChangelogSection> sections = new TreeSet<>();

    private ChangelogEntry(Version version, Date date, Set<ChangelogSection> sections) {
        this.version = version;
        this.date = date;
        this.sections.addAll(sections);
    }

    public String getVersion() {
        return version.toString();
    }
    public Date getDate() {
        return date;
    }
    public Set<ChangelogSection> getSections() {
        return sections;
    }

    @Override
    public int compareTo(ChangelogEntry o) {
        return descendingOrder(version.compareTo(o.version));
    }
    private int descendingOrder(int i) { return -i; }

    public static class Builder {
        Version version;
        Date date;
        Set<ChangelogSection> sections = new TreeSet<>();

        static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        public Builder() {}

        public Builder version(String version) {
            this.version = Version.valueOf(version);
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
