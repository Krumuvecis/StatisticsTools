package historicalData;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import static consoleUtils.ConsoleUtils.printLine;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HistoricalData {
    private static final @NotNull ApproximationType
            DEFAULT_APPROXIMATION_TYPE = ApproximationType.LINEAR;

    private @NotNull ApproximationType
            approximationType = DEFAULT_APPROXIMATION_TYPE;
    private final @NotNull Map<@NotNull Integer, @NotNull SimpleRecord> data;

    //creates new data container with default approximation type
    public HistoricalData() {
        this(null);
    }

    //creates new data container with custom approximation type
    public HistoricalData(@Nullable ApproximationType approximationType) {
        data = new HashMap<>();
        setApproximationType(approximationType);
    }

    public void setApproximationType(@Nullable ApproximationType approximationType) {
        this.approximationType = Objects.requireNonNullElse(
                approximationType,
                DEFAULT_APPROXIMATION_TYPE);
    }

    //adds new records, overwrites if duplicate year
    public void add(int year, @Nullable SimpleRecord record) {
        if (record != null) {
            data.put(year, record);
        } else {
            printLine("null records not allowed");
        }
    }

    //gets int[2] bounds {min, max}; min <= max
    public int @Nullable [] getBounds() {
        try {
            Integer[] orderedYears = getOrderedYears();
            return new int[]{
                    orderedYears[0],
                    orderedYears[orderedYears.length - 1]};
        } catch (IllegalStateException ignored) {
            return null;
        }
    }

    private @NotNull Integer @NotNull [] getOrderedYears() throws IllegalStateException {
        @NotNull Set<@NotNull Integer> containedYearsSet = data.keySet();
        if (data.isEmpty() || containedYearsSet.size() == 0) {
            throw new IllegalStateException();
        }
        @NotNull Integer @NotNull [] orderedYears = containedYearsSet.toArray(new Integer[0]);
        Arrays.sort(orderedYears);
        return orderedYears;
    }

    public double getLastValue() throws IllegalStateException {
        Integer[] orderedYears = getOrderedYears();
        return orderedYears[orderedYears.length - 1];
    }

    //if possible, either gets the record or approximates
    public double getValueAt(int year)
            throws IllegalStateException, IndexOutOfBoundsException {
        int @Nullable [] bounds = getBounds();
        if (bounds == null) {
            throw new IllegalStateException();
        } else {
            if (year < bounds[0] || year > bounds[1]) {
                //out of bounds
                throw new IndexOutOfBoundsException();
            } else {
                if (data.containsKey(year)) {
                    return data.get(year).getValue();
                } else {
                    int @NotNull [] closest = getTwoClosestYears(year);
                    switch (approximationType) {
                        case LOWER -> {
                            return closest[0];
                        }
                        case UPPER -> {
                            return closest[1];
                        }
                        case LINEAR -> {
                            double position = (year - closest[0]) / (double) (closest[1] - closest[0]);
                            double deltaValue = data.get(closest[1]).getValue() - data.get(closest[0]).getValue();
                            double myDelta = deltaValue * position;
                            return data.get(closest[0]).getValue() + myDelta;
                        }
                        default -> throw new IllegalStateException();
                    }
                }
            }
        }
    }

    private int @NotNull [] getTwoClosestYears(int year) {
        int prevYearIndex = 0;
        @NotNull Integer @NotNull [] containedYears = getOrderedYears();
        for (int i = 0; i < data.size() && year > containedYears[i]; i++) {
            prevYearIndex = i;
        }
        return new int[]{
                containedYears[prevYearIndex],
                containedYears[prevYearIndex + 1]};
    }
}