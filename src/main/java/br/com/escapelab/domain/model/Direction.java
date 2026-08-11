package br.com.escapelab.domain.model;

/**
 * Vetor de direção com magnitude limitada a um, usado pelas regras de movimento.
 */
public record Direction(double horizontal, double vertical) {

    public static final Direction NONE = new Direction(0.0, 0.0);
    public static final Direction DOWN = new Direction(0.0, 1.0);

    public Direction {
        requireFinite(horizontal, "horizontal");
        requireFinite(vertical, "vertical");

        double length = Math.hypot(horizontal, vertical);
        if (!Double.isFinite(length)) {
            throw new IllegalArgumentException("A magnitude da direção é grande demais");
        }
        if (length > 1.0) {
            horizontal /= length;
            vertical /= length;
        }
    }

    public boolean isMoving() {
        return horizontal != 0.0 || vertical != 0.0;
    }

    private static void requireFinite(double value, String fieldName) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(fieldName + " deve ser um número finito");
        }
    }
}
