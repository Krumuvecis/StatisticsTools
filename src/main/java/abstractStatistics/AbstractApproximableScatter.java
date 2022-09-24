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
}