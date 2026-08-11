package br.com.escapelab.domain.model;

import java.util.Objects;

/**
 * Entidade do jogador. Contém somente estado e regras de domínio.
 */
public final class Player {

    private final double size;
    private final double speed;

    private Position position;
    private Direction facing = Direction.DOWN;

    public Player(Position initialPosition, double size, double speed) {
        this.position = Objects.requireNonNull(initialPosition, "initialPosition não pode ser nula");
        this.size = requirePositiveFinite(size, "size");
        this.speed = requirePositiveFinite(speed, "speed");
    }

    public void move(Direction direction, double deltaSeconds, WorldBounds worldBounds) {
        Objects.requireNonNull(direction, "direction não pode ser nula");
        Objects.requireNonNull(worldBounds, "worldBounds não pode ser nulo");
        requireValidDelta(deltaSeconds);

        if (deltaSeconds == 0.0) {
            return;
        }

        if (direction.isMoving()) {
            facing = direction;
        }

        double distance = speed * deltaSeconds;
        Position nextPosition = position.translate(
                direction.horizontal() * distance,
                direction.vertical() * distance);

        position = worldBounds.constrain(nextPosition, size / 2.0);
    }

    public Position position() {
        return position;
    }

    public Direction facing() {
        return facing;
    }

    public double size() {
        return size;
    }

    public double speed() {
        return speed;
    }

    private static double requirePositiveFinite(double value, String fieldName) {
        if (!Double.isFinite(value) || value <= 0.0) {
            throw new IllegalArgumentException(fieldName + " deve ser maior que zero e finito");
        }
        return value;
    }

    private static void requireValidDelta(double deltaSeconds) {
        if (!Double.isFinite(deltaSeconds) || deltaSeconds < 0.0) {
            throw new IllegalArgumentException("deltaSeconds deve ser finito e não negativo");
        }
    }
}
