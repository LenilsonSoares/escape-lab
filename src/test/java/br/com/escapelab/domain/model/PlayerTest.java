package br.com.escapelab.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PlayerTest {

    private static final WorldBounds WORLD = new WorldBounds(960.0, 540.0);
    private static final double SIZE = 30.0;
    private static final double SPEED = 210.0;

    @Test
    void movesUsingSpeedAndDeltaTime() {
        Player player = new Player(new Position(100.0, 100.0), SIZE, SPEED);

        player.move(new Direction(1.0, 0.0), 0.5, WORLD);

        assertEquals(100.0 + SPEED * 0.5, player.position().x(), 0.000_001);
        assertEquals(100.0, player.position().y(), 0.000_001);
    }

    @Test
    void keepsTheSameSpeedOnDiagonals() {
        Player player = new Player(new Position(200.0, 200.0), SIZE, SPEED);

        player.move(new Direction(1.0, 1.0), 0.5, WORLD);

        double distance = Math.hypot(
                player.position().x() - 200.0,
                player.position().y() - 200.0);
        assertEquals(SPEED * 0.5, distance, 0.000_001);
    }

    @Test
    void remainsInsideWorldBounds() {
        Player player = new Player(new Position(20.0, 20.0), SIZE, SPEED);
        player.move(new Direction(-1.0, -1.0), 1.0, WORLD);

        assertEquals(SIZE / 2.0, player.position().x(), 0.000_001);
        assertEquals(SIZE / 2.0, player.position().y(), 0.000_001);

        player.move(new Direction(1.0, 1.0), 10.0, WORLD);
        assertEquals(WORLD.width() - SIZE / 2.0, player.position().x(), 0.000_001);
        assertEquals(WORLD.height() - SIZE / 2.0, player.position().y(), 0.000_001);
    }

    @Test
    void keepsFacingWhenStoppedAndUpdatesItWhenMoving() {
        Player player = new Player(new Position(100.0, 100.0), SIZE, SPEED);

        assertEquals(Direction.DOWN, player.facing());

        Direction right = new Direction(1.0, 0.0);
        player.move(right, 0.1, WORLD);
        player.move(Direction.NONE, 0.1, WORLD);

        assertEquals(right, player.facing());
    }

    @Test
    void zeroDeltaDoesNotChangePlayer() {
        Player player = new Player(new Position(100.0, 100.0), SIZE, SPEED);

        player.move(new Direction(1.0, 0.0), 0.0, WORLD);

        assertEquals(new Position(100.0, 100.0), player.position());
        assertEquals(Direction.DOWN, player.facing());
    }

    @Test
    void rejectsInvalidConfigurationAndDelta() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Player(new Position(0.0, 0.0), 0.0, SPEED));
        assertThrows(
                IllegalArgumentException.class,
                () -> new Player(new Position(0.0, 0.0), SIZE, Double.NaN));

        Player player = new Player(new Position(100.0, 100.0), SIZE, SPEED);
        assertThrows(
                IllegalArgumentException.class,
                () -> player.move(Direction.NONE, -0.1, WORLD));
        assertThrows(
                IllegalArgumentException.class,
                () -> player.move(Direction.NONE, Double.NaN, WORLD));
    }
}
