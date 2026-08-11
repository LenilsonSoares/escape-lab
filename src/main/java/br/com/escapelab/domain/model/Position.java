package br.com.escapelab.domain.model;

/**
 * Posição imutável no mundo do jogo.
 */
public record Position(double x, double y) {

    public Position {
        requireFinite(x, "x");
        requireFinite(y, "y");
    }

    public Position translate(double deltaX, double deltaY) {
        requireFinite(deltaX, "deltaX");
        requireFinite(deltaY, "deltaY");
        return new Position(x + deltaX, y + deltaY);
    }

    private static void requireFinite(double value, String fieldName) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(fieldName + " deve ser um número finito");
        }
    }
}
