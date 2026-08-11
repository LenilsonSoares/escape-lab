package br.com.escapelab.configuration;

import java.util.Objects;

/**
 * Parâmetros usados pelo composition root para montar o protótipo.
 */
public record GameConfiguration(
        String windowTitle,
        int viewportWidth,
        int viewportHeight,
        int worldWidth,
        int worldHeight,
        int tileSize,
        double playerSize,
        double playerSpeed,
        double maximumDeltaSeconds,
        double fpsSampleWindowSeconds) {

    public GameConfiguration {
        Objects.requireNonNull(windowTitle, "windowTitle não pode ser nulo");
        if (windowTitle.isBlank()) {
            throw new IllegalArgumentException("windowTitle não pode ser vazio");
        }

        requirePositive(viewportWidth, "viewportWidth");
        requirePositive(viewportHeight, "viewportHeight");
        requirePositive(worldWidth, "worldWidth");
        requirePositive(worldHeight, "worldHeight");
        requirePositive(tileSize, "tileSize");
        requirePositiveFinite(playerSize, "playerSize");
        requirePositiveFinite(playerSpeed, "playerSpeed");
        requirePositiveFinite(maximumDeltaSeconds, "maximumDeltaSeconds");
        requirePositiveFinite(fpsSampleWindowSeconds, "fpsSampleWindowSeconds");

        if (playerSize > worldWidth || playerSize > worldHeight) {
            throw new IllegalArgumentException("playerSize deve caber dentro do mundo");
        }
        if (worldWidth != viewportWidth || worldHeight != viewportHeight) {
            throw new IllegalArgumentException(
                    "Mundo e viewport devem ter o mesmo tamanho enquanto não houver câmera");
        }
    }

    public static GameConfiguration defaultConfiguration() {
        return new GameConfiguration(
                "Escape Lab — Protótipo",
                960,
                540,
                960,
                540,
                48,
                30.0,
                210.0,
                0.1,
                0.25);
    }

    private static void requirePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " deve ser maior que zero");
        }
    }

    private static void requirePositiveFinite(double value, String fieldName) {
        if (!Double.isFinite(value) || value <= 0.0) {
            throw new IllegalArgumentException(fieldName + " deve ser maior que zero e finito");
        }
    }
}
