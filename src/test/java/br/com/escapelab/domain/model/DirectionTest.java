package br.com.escapelab.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DirectionTest {

    @Test
    void normalizesDiagonalDirection() {
        Direction direction = new Direction(1.0, 1.0);

        assertEquals(Math.sqrt(0.5), direction.horizontal(), 0.000_001);
        assertEquals(Math.sqrt(0.5), direction.vertical(), 0.000_001);
        assertEquals(1.0, Math.hypot(direction.horizontal(), direction.vertical()), 0.000_001);

        Direction asymmetric = new Direction(2.0, -1.0);
        assertEquals(2.0 / Math.sqrt(5.0), asymmetric.horizontal(), 0.000_001);
        assertEquals(-1.0 / Math.sqrt(5.0), asymmetric.vertical(), 0.000_001);
    }

    @Test
    void preservesCardinalDirection() {
        Direction direction = new Direction(-1.0, 0.0);

        assertEquals(-1.0, direction.horizontal());
        assertEquals(0.0, direction.vertical());
        assertTrue(direction.isMoving());
        assertFalse(Direction.NONE.isMoving());
    }

    @Test
    void rejectsNonFiniteAxes() {
        assertThrows(IllegalArgumentException.class, () -> new Direction(Double.NaN, 0.0));
        assertThrows(IllegalArgumentException.class, () -> new Direction(0.0, Double.POSITIVE_INFINITY));
        assertThrows(
                IllegalArgumentException.class,
                () -> new Direction(Double.MAX_VALUE, Double.MAX_VALUE));
    }
}
