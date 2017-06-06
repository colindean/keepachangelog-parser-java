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

import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Models a changelog in keepachangelog format
 */
public class ChangelogData {
    private String projectName = "";
    private String description = "";
    private SortedSet<ChangelogEntry> entries = new TreeSet<>();

    /**
     * Create a new changelog model
     *
     * @param projectName the name of the project
     * @param description description of the project
     * @param entries any set of entries in any order; the entries will be sorted when added
     */
    public ChangelogData(String projectName, String description, Set<ChangelogEntry> entries) {
        this.projectName = projectName;
        this.description = description;
        this.entries.addAll(entries);
    }

    /**
     * The name of the project is the content of the first-level heading.
     *
     * @return the name of the project or an empty string
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * The description of the project is the paragraph content between the
     * first-level heading and the first second-level heading.
     *
     * @return the description of the project or an empty string
     */
    public String getDescription() {
        return description;
    }

    /**
     * The entries in the changelog, one for each second-level heading
     * consisting of a version number, a date, and other metadata.
     *
     * @return an set ordered from newest version to oldest, with unreleased at the beginning if present.
     */
    public SortedSet<ChangelogEntry> getEntries() {
        return entries;
    }

    /**
     * A convenience method to retrieve one particular version from the entry set.
     *
     * @param version A string representation of the version desired or "Unreleased"
     * @return the entry, if it exists, or Optional.empty() if it does not.
     */
    public Optional<ChangelogEntry> getEntryForVersion(String version) {
        return getEntries().stream().filter(entry -> entry.getVersion().equals(version)).findFirst();
    }
}
