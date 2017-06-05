/**
 * This file is a part of changelog-parser and is licensed as detailed in LICENSE.md.
 */
package cx.cad.keepachangelog;

import java.util.ArrayList;
import java.util.List;

public class ChangelogSection {
    private String name;
    private List<String> items = new ArrayList<>();

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
}
