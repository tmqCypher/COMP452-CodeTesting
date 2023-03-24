import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestStatsBins {
    @Test
    public void testEdgesLength() {
        int expected = StatsBins.EDGES.length;
        assertEquals(StatsBins.length(), expected);
    }

    @Test
    public void testBinNameLast() {
        int lastIndex = StatsBins.EDGES.length - 1;
        String expected = StatsBins.EDGES[lastIndex] + " or more";
        assertEquals(StatsBins.getBinName(lastIndex), expected);
    }

    @Test
    public void testBinNameWithUpperBounds() {
        for(int i = 0; i < StatsBins.EDGES.length; i++) {
            if(StatsBins.EDGES[i+1] - 1 > StatsBins.EDGES[i]) {
                String expected = StatsBins.EDGES[i] + "-" + (StatsBins.EDGES[i+1] - 1);
                assertEquals(StatsBins.getBinName(i), expected);
            }
        }
    }

    @Test
    public void testBinNameNormal() {
        for(int i = 0; i < StatsBins.EDGES.length; i++) {
            if(!(StatsBins.EDGES[i+1] - 1 > StatsBins.EDGES[i])) {
                String expected = Integer.toString(StatsBins.EDGES[i]);
                assertEquals(StatsBins.getBinName(i), expected);
            }
        }
    }
}
