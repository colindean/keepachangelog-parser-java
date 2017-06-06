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

    public Optional<ChangelogEntry> getEntryForVersion(String version) {
        return getEntries().stream().filter(entry -> entry.getVersion().equals(version)).findFirst();
    }
}
