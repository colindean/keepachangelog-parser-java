package cx.cad.keepachangelog;


import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.parser.Parser;

import java.io.File;
import java.util.HashSet;

public class ChangelogParser {
    public ChangelogData parse(String markdownText) {
        Node mdNode = getMarkdownAst(markdownText);
        String projectName = extractProjectName(mdNode);
        String description = getParagraphAfterFirstHeading(mdNode);

        return new ChangelogData(projectName, description, new HashSet<>());
    }
    public ChangelogData parse(File file) {
        String text = FileTools.readFile(file);
        return parse(text);
    }

    private Node getMarkdownAst(String markdownText) {
        Parser mdParser = Parser.builder().build();
        Node mdNode = mdParser.parse(markdownText);
        return mdNode;
    }


    private String extractProjectName(Node mdNode) {
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
    private String getParagraphAfterFirstHeading(Node mdNode) {
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
