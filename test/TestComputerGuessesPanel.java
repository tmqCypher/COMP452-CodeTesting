import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestComputerGuessesPanel {
    @Test
    public void checkCalcLastGuess() {
        assertEquals(ComputerGuessesPanel.calcLastGuess(100, 50), 75);
        assertEquals(ComputerGuessesPanel.calcLastGuess(0, 0), 0);
    }
}
