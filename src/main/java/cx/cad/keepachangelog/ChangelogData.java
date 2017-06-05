package cx.cad.keepachangelog;

import java.util.Set;
import java.util.TreeSet;

public class ChangelogData {
    private String projectName = "";
    private String description = "";
    private Set<ChangelogEntry> entries = new TreeSet<>();


    public ChangelogData(String projectName, String description, Set<ChangelogEntry> entries) {
        this.projectName = projectName;
        this.description = description;
        this.entries.addAll(entries);
    }

    public String getProjectName() {
        return projectName;
    }
    public String getDescription() {
        return description;
    }
    public Set<ChangelogEntry> getEntries() {
        return entries;
    }
}
