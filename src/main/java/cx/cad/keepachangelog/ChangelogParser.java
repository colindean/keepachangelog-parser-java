package cx.cad.keepachangelog;


import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.parser.Parser;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ChangelogParser {
    public ChangelogData parse(String markdownText) {
        Node mdNode = getMarkdownAst(markdownText);

        ChangelogExtractor extractor = ChangelogExtractor.withDocumentNode(mdNode);
        String projectName = extractor.getProjectName();
        String description = extractor.getDescription();
        Set<ChangelogEntry> entries = extractor.getEntries();

        return new ChangelogData(projectName, description, entries);
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
