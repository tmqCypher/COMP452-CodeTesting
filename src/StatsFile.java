import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * File-backed implementation of GameStats
 * Returns the number of games *within the last 30 days* where the person took a given number of guesses
 */
public class StatsFile extends GameStats {

    public static final String FILENAME = "guess-the-number-stats.csv";
    private CSVReader csv;

    // maps the number of guesses required to the number of games within
    // the past 30 days where the person took that many guesses
    private SortedMap<Integer, Integer> statsMap;

    public StatsFile(CSVReader csv, LocalDateTime limit) {
        this.csv = csv;
        statsMap = new TreeMap<>();
        for (int numGuesses : loadStatsAfter(limit)) {
            statsMap.put(numGuesses, 1 + statsMap.getOrDefault(numGuesses, 0));
        }
    }

    public StatsFile() {
        this(loadCSV(FILENAME), LocalDateTime.now().minusDays(30));
    }

    private ArrayList<Integer> loadStatsAfter(LocalDateTime limit) {
        ArrayList<Integer> results = new ArrayList<>();
        try {
            String[] values;
            while ((values = csv.readNext()) != null) {
                // values should have the date and the number of guesses as the two fields
                try {
                    LocalDateTime timestamp = LocalDateTime.parse(values[0]);
                    int numGuesses = Integer.parseInt(values[1]);
                    if (timestamp.isAfter(limit)) results.add(numGuesses);
                } catch(NumberFormatException nfe){
                    // NOTE: In a full implementation, we would log this error and possibly alert the user
                    throw nfe;
                } catch(DateTimeParseException dtpe){
                    // NOTE: In a full implementation, we would log this error and possibly alert the user
                    throw dtpe;
                }
            }
        } catch (CsvValidationException e) {
            // NOTE: In a full implementation, we would log this error and alert the user
            // NOTE: For this project, you do not need unit tests for handling this exception.
        } catch (IOException e) {
            // NOTE: In a full implementation, we would log this error and alert the user
            // NOTE: For this project, you do not need unit tests for handling this exception.
        }
        return results;
    }

    public static CSVReader loadCSV(String path) {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(path));
        } catch (IOException e) {
            // NOTE: In a full implementation, we would log this error and alert the user
            // NOTE: For this project, you do not need unit tests for handling this exception.
        }
        return reader;
    }

    public int size() { return statsMap.size(); }

    @Override
    public int numGames(int numGuesses) {
        return statsMap.getOrDefault(numGuesses, 0);
    }

    @Override
    public int maxNumGuesses() {
        return (statsMap.isEmpty() ? 0 : statsMap.lastKey());
    }

    @Override
    public int countGames(int minGuesses, int maxGuesses) {
        int result = 0;
        for(int numGuesses=minGuesses; numGuesses<maxGuesses; numGuesses++){
            result += this.numGames(numGuesses);
        }
        return result;
    }
}
