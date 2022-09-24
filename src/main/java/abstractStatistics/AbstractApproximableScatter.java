package abstractStatistics;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class AbstractApproximableScatter {
    private static final @NotNull ApproximationType
            DEFAULT_APPROXIMATION_TYPE = ApproximationType.LINEAR;

    private @NotNull ApproximationType
            approximationType = DEFAULT_APPROXIMATION_TYPE;

    protected AbstractApproximableScatter(@Nullable ApproximationType approximationType) {
        setApproximationType(approximationType);
    }

    public void setApproximationType(@Nullable ApproximationType approximationType) {
        this.approximationType = Objects.requireNonNullElse(
                approximationType,
                DEFAULT_APPROXIMATION_TYPE);
    }

    public @NotNull ApproximationType getApproximationType() {
        return approximationType;
    }

    public double getApproximateValue(int x,
                                      int x1, double y1,
                                      int x2, double y2)
            throws IllegalStateException {
        switch (getApproximationType()) {
            case LOWER -> {
                return y1;
            }
            case UPPER -> {
                return y2;
            }
            case LINEAR -> {
                double position = (x - x1) / (double) (x2 - x1);
                double dy = y2 - y1;
                double dTotal = dy * position;
                return y1 + dTotal;
            }
            default -> throw new IllegalStateException();
        }
    }
}