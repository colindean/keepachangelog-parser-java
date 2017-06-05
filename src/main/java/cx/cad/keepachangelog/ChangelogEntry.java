/**
 * This file is a part of changelog-parser and is licensed as detailed in LICENSE.md.
 */
package cx.cad.keepachangelog;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ChangelogEntry {
    private String version;
    private Date date;
    private Set<ChangelogSection> sections = new HashSet<ChangelogSection>();

    public ChangelogEntry(String version, Date date, Set<ChangelogSection> sections) {
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

    public class Builder {
        String version = "";
        Date date;
        Set<ChangelogSection> sections = new HashSet<ChangelogSection>();

        public Builder version(String version) {
            this.version = version;
            return this;
        }
        public Builder date(String date) throws ParseException {
            this.date = DateFormat.getDateInstance().parse(date);
            return this;
        }
        public Builder addSection(ChangelogSection section) {
            sections.add(section);
            return this;
        }
    }
}
