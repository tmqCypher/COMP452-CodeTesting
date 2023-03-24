import com.opencsv.CSVReader;
import java.io.StringReader;

public class MockCSVReader extends CSVReader {

    private String[][] lines;
    private int cursor = 0;

    public MockCSVReader(String[][] lines) {
        super(new StringReader(""));
        this.lines = lines;
    }

    @Override
    public String[] readNext() {
        return cursor < lines.length? lines[cursor++] : null;
    }
}
