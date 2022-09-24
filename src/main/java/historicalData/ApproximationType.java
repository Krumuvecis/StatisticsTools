package historicalData;

/**
 * An enum for various types of approximation to use,
 *  when selected year is not contained, yet within bounds.
 */
public enum ApproximationType {
    LOWER, //takes lower of two nearest years
    UPPER, //takes higher of two nearest years
    LINEAR //weighted average
}