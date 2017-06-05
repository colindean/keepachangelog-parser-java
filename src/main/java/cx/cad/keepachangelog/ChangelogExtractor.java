/**
 * This file is a part of changelog-parser and is licensed as detailed in LICENSE.md.
 */
package cx.cad.keepachangelog;

import com.vladsch.flexmark.ast.*;

public class ChangelogExtractor {

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
        Text text = (Text) heading.getFirstChildAny(Text.class);
        return nodeToString(text);
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

    private String nodeToString(Node node) {
        return node.getChars().unescape();
    }
}
