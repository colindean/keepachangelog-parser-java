/**
 * This file is a part of changelog-parser and is licensed as detailed in LICENSE.md.
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
