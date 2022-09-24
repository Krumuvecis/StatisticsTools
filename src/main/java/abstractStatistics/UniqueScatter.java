package abstractStatistics;

import java.util.*;

import static consoleUtils.ConsoleUtils.printLine;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class UniqueScatter extends AbstractApproximableScatter {
    public final @NotNull Map<@NotNull Integer, @NotNull SimpleRecord> data;

    //creates new data container with custom approximation type
    public UniqueScatter(@Nullable ApproximationType approximationType) {
        super(approximationType);
        data = new HashMap<>();
    }

    //adds new records, overwrites if duplicate
    public void add(int key, @Nullable SimpleRecord record) {
        if (record != null) {
            data.put(key, record);
        } else {
            printLine("Null records not allowed yet");
        }
    }

    //gets int[2] of bounds {min, max}; min <= max
    public int @Nullable [] getBounds() {
        try {
            Integer[] orderedKeys = getOrderedKeys();
            return new int[]{
                    orderedKeys[0],
                    orderedKeys[orderedKeys.length - 1]};
        } catch (IllegalStateException ignored) {
            return null;
        }
    }

    private @NotNull Integer @NotNull [] getOrderedKeys() throws IllegalStateException {
        @NotNull Set<@NotNull Integer> keySet = data.keySet();
        if (data.isEmpty()) {
            throw new IllegalStateException();
        }
        @NotNull Integer @NotNull [] orderedKeys = keySet.toArray(new Integer[0]);
        Arrays.sort(orderedKeys);
        return orderedKeys;
    }

    public double getLastValue() throws IllegalStateException {
        Integer[] orderedKeys = getOrderedKeys();
        return orderedKeys[orderedKeys.length - 1];
    }

    //if possible, either gets the record or approximates
    public double getValueAt(int key)
            throws IllegalStateException, IndexOutOfBoundsException {
        int @Nullable [] bounds = getBounds();
        if (bounds == null) {
            throw new IllegalStateException();
        } else {
            if (key < bounds[0] || key > bounds[1]) {
                //out of bounds
                throw new IndexOutOfBoundsException();
            } else {
                if (data.containsKey(key)) {
                    return data.get(key).getValue();
                } else {
                    int @NotNull [] closest = getTwoClosestKeys(key);
                    switch (getApproximationType()) {
                        case LOWER -> {
                            return closest[0];
                        }
                        case UPPER -> {
                            return closest[1];
                        }
                        case LINEAR -> {
                            double position = (key - closest[0]) / (double) (closest[1] - closest[0]);
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

    private int @NotNull [] getTwoClosestKeys(int key) {
        int prevKeyIndex = 0;
        @NotNull Integer @NotNull [] containedKeys = getOrderedKeys();
        for (int i = 0; i < data.size() && key > containedKeys[i]; i++) {
            prevKeyIndex = i;
        }
        return new int[]{
                containedKeys[prevKeyIndex],
                containedKeys[prevKeyIndex + 1]};
    }
}