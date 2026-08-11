package br.com.escapelab.application.time;

/**
 * Tempo real do frame e delta limitado usado pela simulação.
 */
public record FrameTiming(double elapsedSeconds, double simulationDeltaSeconds) {

    public static final FrameTiming INITIAL = new FrameTiming(0.0, 0.0);

    public FrameTiming {
        requireNonNegativeFinite(elapsedSeconds, "elapsedSeconds");
        requireNonNegativeFinite(simulationDeltaSeconds, "simulationDeltaSeconds");

        if (simulationDeltaSeconds > elapsedSeconds) {
            throw new IllegalArgumentException("O delta da simulação não pode superar o tempo real");
        }
    }

    private static void requireNonNegativeFinite(double value, String fieldName) {
        if (!Double.isFinite(value) || value < 0.0) {
            throw new IllegalArgumentException(fieldName + " deve ser finito e não negativo");
        }
    }
}
