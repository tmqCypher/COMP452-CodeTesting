import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class TestStatsFile {
    @Test
    public void testLoadAfter() {
        int NUM_GAMES = 10;
        LocalDateTime START_TIME = LocalDateTime.MIN;
        LocalDateTime END_TIME = LocalDateTime.MAX;

        String[][] testData = new String[NUM_GAMES][2];
        for (int g = 0; g < NUM_GAMES; g++) {
            LocalDateTime gameTime = g < NUM_GAMES/2?
                    LocalDateTime.MAX.minusMonths(1)
                    : LocalDateTime.MIN.plusMonths(1);
            testData[g] = new String[] {
                    gameTime.toString(),
                    Integer.toString(g)
            };
        }

        StatsFile stats;
        stats= new StatsFile(new MockCSVReader(testData), LocalDateTime.MAX);
        assertEquals(0, stats.size());
        stats = new StatsFile(new MockCSVReader(testData), LocalDateTime.now());
        assertEquals(NUM_GAMES/2, stats.size());
        stats = new StatsFile(new MockCSVReader(testData), LocalDateTime.MIN);
        assertEquals(testData.length, stats.size());
    }

    @Test
    public void testNumGames() {
        int NUM_GAMES = 10;
        int TEST_VALUE = 1;

        String[][] testData = new String[NUM_GAMES][2];
        for (int g = 0; g < NUM_GAMES; g++) {
            testData[g] = new String[] {
                    LocalDateTime.now().toString(),
                    Integer.toString(g < NUM_GAMES/2? TEST_VALUE : g)
            };
        }

        StatsFile stats = new StatsFile(new MockCSVReader(testData), LocalDateTime.MIN);
        assertEquals(0, stats.numGames(Integer.MAX_VALUE));
        assertEquals(NUM_GAMES/2, stats.numGames(TEST_VALUE));
    }

    @Test
    public void testMaxNumGuesses() {
        int NUM_GAMES = 10;

        String[][] testData = new String[NUM_GAMES][2];
        for (int g = 0; g < NUM_GAMES; g++) {
            testData[g] = new String[] {
                    LocalDateTime.now().toString(),
                    Integer.toString(g)
            };
        }

        StatsFile stats = new StatsFile(new MockCSVReader(testData), LocalDateTime.MIN);
        assertEquals(NUM_GAMES-1, stats.maxNumGuesses());
    }

    @Test
    public void testCountGames() {
        int NUM_GAMES = 10;

        String[][] testData = new String[NUM_GAMES][2];
        for (int g = 0; g < NUM_GAMES; g++) {
            testData[g] = new String[] {
                    LocalDateTime.now().toString(),
                    Integer.toString(g)
            };
        }

        StatsFile stats = new StatsFile(new MockCSVReader(testData), LocalDateTime.MIN);
        for (int g = 0; g < NUM_GAMES; g++) { assertEquals(g, stats.countGames(0, g)); }
        for (int g = 0; g < NUM_GAMES; g++) { assertEquals(NUM_GAMES - g, stats.countGames(g, NUM_GAMES)); }
    }
}
