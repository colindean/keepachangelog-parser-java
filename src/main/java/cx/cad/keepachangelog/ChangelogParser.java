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


import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.parser.Parser;

import java.io.File;
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
