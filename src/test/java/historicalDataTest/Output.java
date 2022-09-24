package historicalDataTest;

import historicalData.HistoricalData;

import static consoleUtils.ConsoleUtils.printLine;
import static consoleUtils.ConsoleUtils.printSeparator;
import static consoleUtils.NumberFormatter.doubleToString;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class Output {
    //reference
    private final @NotNull HistoricalData data;

    protected Output(@NotNull HistoricalData data) {
        this.data = data;
    }

    protected void printBounds() {
        int @Nullable [] bounds = data.getBounds();
        String commonString = "Bounds: ";
        if (bounds == null) {
            printLine(commonString + "no records found");
        } else {
            printLine(commonString + bounds[0] + ", " + bounds[1]);
        }
    }

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