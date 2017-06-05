package cx.cad.keepachangelog;


import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.parser.Parser;

import java.io.File;
import java.util.HashSet;

public class ChangelogParser {
    public ChangelogData parse(String markdownText) {
        Node mdNode = getMarkdownAst(markdownText);
        ChangelogExtractor extractor = ChangelogExtractor.withDocumentNode(mdNode);
        String projectName = extractor.getProjectName();
        String description = extractor.getDescription();

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
}
