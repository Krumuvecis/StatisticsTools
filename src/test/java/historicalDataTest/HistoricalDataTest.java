package historicalDataTest;

import abstractStatistics.SimpleRecord;
import historicalData.HistoricalData;

import org.jetbrains.annotations.NotNull;

/**
 * A general test for HistoricalData object.
 */
public class HistoricalDataTest {
    private static final int[]
            RECORDED_YEARS = new int[] {10, 15, 20, 12},
            TESTABLE_YEARS = new int[] {5, 10, 17, 30};
    private static final @NotNull HistoricalData
            historicalData = new HistoricalData();
    private static final @NotNull Output
            output = new Output(historicalData);

    /**
     * Main method of this test.
     * Run this to test.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        output.printBounds();
        addYears();
        output.printBounds();
        printYears();
    }

    private static void addYears() {
        double valueCoefficient = 10.0;
        for (int year : RECORDED_YEARS) {
            historicalData.add(
                    year,
                    new SimpleRecord(year * valueCoefficient));
        }
    }

    private static void printYears() {
        for (int year : TESTABLE_YEARS) {
            output.printYearInfo(year);
        }
    }
}