package historicalData;

import abstractStatistics.UniqueScatter;
import abstractStatistics.ApproximationType;
import abstractStatistics.SimpleRecord;

import org.jetbrains.annotations.Nullable;

public class HistoricalData extends UniqueScatter {

    //creates new data container with default approximation type
    public HistoricalData() {
        this(null);
    }

    //creates new data container with custom approximation type
    public HistoricalData(@Nullable ApproximationType approximationType) {
        super(approximationType);
        setApproximationType(approximationType);
    }

    //adds new records, overwrites if duplicate year
    @Override
    public void add(int year, @Nullable SimpleRecord record) {
        super.add(year, record);
    }
}