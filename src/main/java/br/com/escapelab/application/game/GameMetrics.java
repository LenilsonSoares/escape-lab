package br.com.escapelab.application.game;

/**
 * Métricas do frame expostas para diagnóstico, sem detalhes de renderização.
 */
public record GameMetrics(double framesPerSecond, double deltaSeconds) {

    public static final GameMetrics INITIAL = new GameMetrics(0.0, 0.0);

    public GameMetrics {
        requireNonNegativeFinite(framesPerSecond, "framesPerSecond");
        requireNonNegativeFinite(deltaSeconds, "deltaSeconds");
    }

    private static void requireNonNegativeFinite(double value, String fieldName) {
        if (!Double.isFinite(value) || value < 0.0) {
            throw new IllegalArgumentException(fieldName + " deve ser finito e não negativo");
        }
    }
}
