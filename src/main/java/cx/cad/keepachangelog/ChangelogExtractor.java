/**
 * This file is a part of changelog-parser and is licensed as detailed in LICENSE.md.
 */
package cx.cad.keepachangelog;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.HeadingCollectingVisitor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChangelogExtractor {

    public static final String VERSION_AND_DATE_SEPARATOR = " - ";
    private Document mdNode;

    private ChangelogExtractor(Document node) {
        this.mdNode = node;
    }

    public static ChangelogExtractor withDocumentNode(Node node) {
        if(node instanceof Document) {
            return new ChangelogExtractor((Document) node);
        } else {
            throw new IllegalArgumentException(String.format("Node is a %s and not a Document", node.getClass().toString()));
        }
    }

    public String getProjectName() {
        if(!mdNode.hasChildren() || hasTopHeading(mdNode)) throw new MissingHeaderException();
        return getFirstHeadingText(mdNode);
    }

    private boolean hasTopHeading(Node mdNode) {
        return mdNode.getChildOfType(Heading.class) != null;
    }
    private String getFirstHeadingText(Node mdNode) {
        Heading heading = (Heading) mdNode.getFirstChildAny(Heading.class);
        return heading.getText().unescape();
    }

    public String getDescription() {
        return getParagraphAfterFirstHeading();
    }

    private String getParagraphAfterFirstHeading() {
        StringBuilder sb = new StringBuilder();
        Node possibleParagraph = mdNode.getFirstChild().getNext();
        while(possibleParagraph != null && possibleParagraph instanceof Paragraph){
            Paragraph paragraph = (Paragraph) possibleParagraph;
            sb.append(paragraph.getContentChars().unescape());
            possibleParagraph = paragraph.getNextAny(Paragraph.class);
        }
        return sb.toString();
    }

    public Set<ChangelogEntry> getEntries() {
        //find all Heading with level == 2 -> Entry
          // any paragraph is a description
          // find all Heading with level == 3 -> Section
            // any paragraph is a description
            // any lists are items in the section

        List<Heading> headings = new HeadingCollectingVisitor().collectAndGetHeadings(mdNode);
        headings.removeIf( heading -> heading.getLevel() !=2 );
        List<Heading> headings2 = headings;
        Set<ChangelogEntry> entries = headings2.stream().map(this::extractHeading2).collect(Collectors.toSet());
        return entries;
    }

    private ChangelogEntry extractHeading2(Heading heading) {
        if(heading.getLevel() != 2) throw new IllegalArgumentException(String.format("Heading [%s] is not a second-level heading.", heading.getText().unescape()));

        String text = heading.getText().unescape();
        String[] versionAndDate = text.split(VERSION_AND_DATE_SEPARATOR);
        String version = versionAndDate[0];
        String date = versionAndDate[1];

        ChangelogEntry.Builder builder = new ChangelogEntry.Builder();

        try {
            builder = builder.version(version).date(date);
        } catch (ParseException e) {
            throw new MalformedChangelogException(e);
        }

        Node next = heading.getNext();

        String description = null;

        while(next != null && !isAHeading2(next)) {
            if(isAParagraph(next)) {
                Paragraph paragraph = (Paragraph) next;
                description = ((Paragraph) next).getContentChars().unescape();
                next = paragraph.getNext();
            } else
            if(isHeading3(next)) {
                Heading sectionHeading = (Heading) next;
                String name = sectionHeading.getText().unescape();
                BulletList list = (BulletList) sectionHeading.getNextAny(BulletList.class);
                ArrayList<String> items = new ArrayList<>(5);
                list.getChildren().forEach(item -> {
                    BulletListItem blItem = (BulletListItem) item;
                    items.add(blItem.getContentChars().unescape());
                });
                builder.addSection(new ChangelogSection(name, items));
                next = next.getNext();
            } else {
                next = next.getNext();
            }
        }

        return builder.description(description).build();
    }

    private boolean isAHeading2(Node node) {
        return isHeadingLevel(node, 2);
    }
    private boolean isHeading3(Node node) {
        return isHeadingLevel(node, 3);
    }
    private boolean isHeadingLevel(Node node, int level) {
        return node.isOrDescendantOfType(Heading.class) && ((Heading)node).getLevel() == level;
    }
    private boolean isAParagraph(Node node) {
        return node.isOrDescendantOfType(Paragraph.class);
    }
}
