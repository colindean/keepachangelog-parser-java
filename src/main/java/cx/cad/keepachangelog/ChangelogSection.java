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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangelogSection implements Comparable<ChangelogSection> {
    private String name;
    private List<String> items = new ArrayList<>();
    private static List<String> ordering = Arrays.asList("Added", "Changed", "Deprecated", "Removed", "Fixed", "Security");

    public ChangelogSection(String name, List<String> items) {
        this.name = name;
        this.items.addAll(items);
    }

    public String getName() {
        return name;
    }
    public List<String> getItems() {
        return items;
    }

    private Integer nameOrder() {
        int position = ordering.indexOf(getName());
        if(position == -1) {
            return 99;
        } else {
            return position;
        }
    }

    @Override
    public int compareTo(ChangelogSection other) {
        return nameOrder().compareTo(other.nameOrder());
    }
}
