import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Displays statistics about how many guesses the person took during past games
 * Loads data from the file and displays in a JPanel
 * TODO: refactor this class
 */
public class StatsPanel extends JPanel {

    // Stats will display the number of games in each "bin"
    // A bin goes from BIN_EDGES[i] through BIN_EDGES[i+1]-1, inclusive
    private static final int [] BIN_EDGES = {1, 2, 4, 6, 8, 10, 12, 14};
    private final ArrayList<JLabel> resultsLabels;

    public StatsPanel(JPanel cardsPanel) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(makeCenteredLabel(("Your Stats")));
        this.add(makeCenteredLabel("(Past 30 Days)"));
        this.add(Box.createRigidArea(new Dimension(0,40)));

        this.resultsLabels = makeResultsLabels();
        this.add(makeResultsPanel(this.resultsLabels));
        this.add(Box.createVerticalGlue());

        this.add(makeQuitButton(cardsPanel));
        this.add(Box.createRigidArea(new Dimension(0,20)));

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent e) {
                updateResultsLabels(resultsLabels);
            }
        });
    }

    // TODO: Unit test this
    private static String getBinName(int index) {
        String binName = Integer.toString(BIN_EDGES[index]);
        if(index == BIN_EDGES.length-1) {
            // last bin
            binName += " or more";
        }
        else if(BIN_EDGES[index+1] - 1 > BIN_EDGES[index]) {
            // upper bound
            binName += "-" + (BIN_EDGES[index+1] - 1);
        }
        return binName;
    }

    private static JLabel makeCenteredLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private static JButton makeQuitButton(JPanel panel) {
        JButton quit = new JButton("Back to Home");
        quit.addActionListener(e -> {
            // See itemStateChanged in https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/CardLayoutDemoProject/src/layout/CardLayoutDemo.java
            CardLayout cardLayout = (CardLayout) panel.getLayout();
            cardLayout.show(panel, ScreenID.HOME.name());
        });
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);
        return quit;
    }

    private static ArrayList<JLabel> makeResultsLabels() {
        ArrayList<JLabel> labels = new ArrayList<>();
        for(int binIndex=0; binIndex<BIN_EDGES.length; binIndex++) {
            labels.add(new JLabel("--"));
        }
        return labels;
    }

    private static JPanel makeResultsPanel(ArrayList<JLabel> labels) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        panel.add(new JLabel("Guesses"));
        panel.add(new JLabel("Games"));
        for(int binIndex=0; binIndex<BIN_EDGES.length; binIndex++){
            String binName = getBinName(binIndex);
            panel.add(new JLabel(binName));
            panel.add(labels.get(binIndex));
        }
        panel.setMinimumSize(new Dimension(120, 120));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateResultsLabels(labels);
        return panel;
    }

    private static void clearResults(ArrayList<JLabel> labels) {
        for(JLabel lbl : labels) lbl.setText("--");
    }

    private static void updateResultsLabels(ArrayList<JLabel> labels) {
        clearResults(labels);
        GameStats stats = new StatsFile();

        for(int binIndex=0; binIndex<BIN_EDGES.length; binIndex++) {
            final int lowerBound = BIN_EDGES[binIndex];
            final int upperBound = binIndex == BIN_EDGES.length-1?
                    stats.maxNumGuesses() : BIN_EDGES[binIndex+1];
            int numGames = stats.sumGames(lowerBound, upperBound);

            JLabel resultLabel = labels.get(binIndex);
            resultLabel.setText(Integer.toString(numGames));
        }
    }
}
