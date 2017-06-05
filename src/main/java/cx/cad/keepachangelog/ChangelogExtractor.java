/**
 * This file is a part of changelog-parser and is licensed as detailed in LICENSE.md.
 */
package cx.cad.keepachangelog;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.util.HeadingCollectingVisitor;

import java.text.ParseException;
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

        return builder.build();
    }
}
