package abstractStatistics;

/**
 * An enum for various types of approximation to use,
 *  when selected year is not contained, yet within bounds.
 */
public enum ApproximationType {
    LOWER, //takes lower of two nearest values
    UPPER, //takes higher of two nearest values
    LINEAR //weighted average
}