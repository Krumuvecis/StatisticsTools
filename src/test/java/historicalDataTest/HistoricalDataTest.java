package historicalDataTest;

import historicalData.Record;
import historicalData.HistoricalData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static consoleUtils.ConsoleUtils.printLine;
import static consoleUtils.ConsoleUtils.printSeparator;
import static consoleUtils.NumberFormatter.doubleToString;

public class HistoricalDataTest {
    private static final int
            DESIRED_YEAR_BEFORE = 5,
            DESIRED_YEAR_EXACT = 10,
            DESIRED_YEAR_WITHIN = 17,
            DESIRED_YEAR_AFTER = 30;
    private static final @NotNull HistoricalData
            historicalData = new HistoricalData();

    public static void main(String[] args) {
        prepareHistoricalData();
        output(DESIRED_YEAR_BEFORE);
        output(DESIRED_YEAR_EXACT);
        output(DESIRED_YEAR_WITHIN);
        output(DESIRED_YEAR_AFTER);
    }

    private static void prepareHistoricalData() {
        printBounds();
        historicalData.add(10, new Record(100));
        historicalData.add(15, new Record(150));
        historicalData.add(20, new Record(200));
        historicalData.add(12, new Record(120));
        printBounds();
    }

    private static void printBounds() {
        int @Nullable [] bounds = historicalData.getBounds();
        String commonString = "Bounds: ";
        if (bounds == null) {
            printLine(commonString + "no records found");
        } else {
            printLine(commonString + bounds[0] + ", " + bounds[1]);
        }
    }

    private static void output(int year) {
        printSeparator();
        printLine("Desired year:" + year);
        try {
            double temperature = getTemperature(year);
            printLine("Expected temperature: " + doubleToString(temperature, 2) + " C");
        } catch (IllegalStateException ignored) {
            printLine("Exception: Illegal state!");
        } catch (IndexOutOfBoundsException ignored) {
            int @Nullable [] bounds = historicalData.getBounds();
            if (bounds == null) {
                printLine("Exception: Index out of bounds! Null bounds!");
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
    private static double getTemperature(int year) {
        return historicalData.getValueAt(year);
    }
}