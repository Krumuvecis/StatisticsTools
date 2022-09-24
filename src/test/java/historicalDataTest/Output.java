package historicalDataTest;

import historicalData.HistoricalData;

import static consoleUtils.ConsoleUtils.printLine;
import static consoleUtils.ConsoleUtils.printSeparator;
import static consoleUtils.NumberFormatter.doubleToString;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A class for managing output for the HistoricalDataTest.
 */
class Output {
    private final @NotNull HistoricalData data; //reference

    /**
     * Creates a new Output object.
     *
     * @param data A HistoricalData object for reference.
     */
    protected Output(@NotNull HistoricalData data) {
        this.data = data;
    }

    /**
     * Prints bounds of the historical data.
     */
    protected void printBounds() {
        int @Nullable [] bounds = data.getBounds();
        String commonString = "Bounds: ";
        if (bounds == null) {
            printLine(commonString + "no records found");
        } else {
            printLine(commonString + bounds[0] + ", " + bounds[1]);
        }
    }

    /**
     * Prints information about a selected year.
     *
     * @param year The year.
     */
    protected void printYearInfo(int year) {
        printSeparator();
        printLine("Desired year:" + year);
        try {
            double temperature = getTemperature(year);
            printLine("Expected temperature: " + doubleToString(temperature, 2) + " C");
        } catch (IllegalStateException ignored) {
            printLine("Exception: Illegal state!");
        } catch (IndexOutOfBoundsException ignored) {
            int @Nullable [] bounds = data.getBounds();
            if (bounds == null) {
                printLine("Exception: Index out of bounds! No records found.");
            } else {
                if (year < bounds[0]) {
                    printLine("Out of bounds - year too small.");
                }
                if (year > bounds[1]) {
                    printLine("Out of bounds - year too great.");
                }
            }
        }
    }

    //in celsius
    private double getTemperature(int year) {
        return data.getValueAt(year);
    }
}