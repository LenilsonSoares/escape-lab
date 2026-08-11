package br.com.escapelab.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class WorldBoundsTest {

    @Test
    void calculatesWorldCenter() {
        WorldBounds bounds = new WorldBounds(960.0, 540.0);

        assertEquals(new Position(480.0, 270.0), bounds.center());
    }

    @Test
    void constrainsPositionAtEveryEdge() {
        WorldBounds bounds = new WorldBounds(100.0, 80.0);

        assertEquals(new Position(10.0, 10.0), bounds.constrain(new Position(-20.0, -30.0), 10.0));
        assertEquals(new Position(90.0, 70.0), bounds.constrain(new Position(120.0, 100.0), 10.0));
    }

    @Test
    void rejectsInvalidWorldAndOversizedEntity() {
        assertThrows(IllegalArgumentException.class, () -> new WorldBounds(0.0, 100.0));
        assertThrows(IllegalArgumentException.class, () -> new WorldBounds(Double.NaN, 100.0));

        WorldBounds bounds = new WorldBounds(20.0, 20.0);
        assertThrows(
                IllegalArgumentException.class,
                () -> bounds.constrain(new Position(10.0, 10.0), 11.0));
    }
}
