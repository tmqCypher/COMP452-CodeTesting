import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// the tests in this file use dependency injection
public class TestHumanGuessesGame {
    @Test
    public void lowGuess() {
        int target = 51;
        int guess = 25;
        MockRandom mr = new MockRandom(target);
        HumanGuessesGame hgg = new HumanGuessesGame(mr);

        assertEquals(GuessResult.LOW, hgg.makeGuess(guess));
    }

    @Test
    public void highGuess() {
        int target = 51;
        int guess = 75;
        MockRandom mr = new MockRandom(target);
        HumanGuessesGame hgg = new HumanGuessesGame(mr);

        assertEquals(GuessResult.HIGH, hgg.makeGuess(guess));
    }

    @Test
    public void equalGuess() {
        int target = 51;
        int guess = 51;
        MockRandom mr = new MockRandom(target);
        HumanGuessesGame hgg = new HumanGuessesGame(mr);

        // guess + 1 because the constructor adds 1 to the value received
        assertEquals(GuessResult.CORRECT, hgg.makeGuess(guess+1));
    }

    @Test
    public void countGuesses() {
        int target = 51;
        int guess = 25;
        MockRandom mr = new MockRandom(target);
        HumanGuessesGame hgg = new HumanGuessesGame(mr);

        assertEquals(0, hgg.getNumGuesses());

        hgg.makeGuess(guess);

        assertEquals(1, hgg.getNumGuesses());

        int guessCount = 14;
        for (int i = 1; i < guessCount; i++) {
            hgg.makeGuess(guess);
        }

        assertEquals(14, hgg.getNumGuesses());
    }
}
