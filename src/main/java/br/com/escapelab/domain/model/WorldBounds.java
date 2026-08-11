package br.com.escapelab.domain.model;

import java.util.Objects;

/**
 * Limites retangulares do mundo. O tamanho do mundo é independente do viewport.
 */
public record WorldBounds(double width, double height) {

    public WorldBounds {
        requirePositiveFinite(width, "width");
        requirePositiveFinite(height, "height");
    }

    public Position center() {
        return new Position(width / 2.0, height / 2.0);
    }

    public Position constrain(Position position, double halfExtent) {
        Objects.requireNonNull(position, "position não pode ser nula");
        requireNonNegativeFinite(halfExtent, "halfExtent");

        if (halfExtent * 2.0 > width || halfExtent * 2.0 > height) {
            throw new IllegalArgumentException("A entidade deve caber dentro dos limites do mundo");
        }

        return new Position(
                clamp(position.x(), halfExtent, width - halfExtent),
                clamp(position.y(), halfExtent, height - halfExtent));
    }

    private static double clamp(double value, double minimum, double maximum) {
        return Math.max(minimum, Math.min(value, maximum));
    }

    private static void requirePositiveFinite(double value, String fieldName) {
        if (!Double.isFinite(value) || value <= 0.0) {
            throw new IllegalArgumentException(fieldName + " deve ser maior que zero e finito");
        }
    }

    private static void requireNonNegativeFinite(double value, String fieldName) {
        if (!Double.isFinite(value) || value < 0.0) {
            throw new IllegalArgumentException(fieldName + " não pode ser negativo ou infinito");
        }
    }
}
