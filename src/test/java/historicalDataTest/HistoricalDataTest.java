package historicalDataTest;

import historicalData.Record;
import historicalData.HistoricalData;

import org.jetbrains.annotations.NotNull;

public class HistoricalDataTest {
    private static final int
            DESIRED_YEAR_BEFORE = 5,
            DESIRED_YEAR_EXACT = 10,
            DESIRED_YEAR_WITHIN = 17,
            DESIRED_YEAR_AFTER = 30;
    private static final @NotNull HistoricalData
            historicalData = new HistoricalData();
    private static final @NotNull Output output = new Output(historicalData);

    public static void main(String[] args) {
        prepareHistoricalData();
        output.printYearInfo(DESIRED_YEAR_BEFORE);
        output.printYearInfo(DESIRED_YEAR_EXACT);
        output.printYearInfo(DESIRED_YEAR_WITHIN);
        output.printYearInfo(DESIRED_YEAR_AFTER);
    }

    private static void prepareHistoricalData() {
        output.printBounds();
        historicalData.add(10, new Record(100));
        historicalData.add(15, new Record(150));
        historicalData.add(20, new Record(200));
        historicalData.add(12, new Record(120));
        output.printBounds();
    }
}