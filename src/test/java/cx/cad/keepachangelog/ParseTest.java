package cx.cad.keepachangelog;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ParseTest {

    @Test
    public void test_parser_instantiation() {
        new ChangelogParser();
    }

    @Test
    public void test_clean_parse() {
        ChangelogParser parser = a_parser();
        parser.parse(TestChangelogs.BASIC);
    }

    @Test
    public void test_parser_gets_projectName() {
        ChangelogParser parser = a_parser();
        ChangelogData data = parser.parse(TestChangelogs.BASIC);
        Assert.assertEquals("project name", data.getProjectName());
    }


    private ChangelogParser a_parser() {
        return new ChangelogParser();
    }
}
