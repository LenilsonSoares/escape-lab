package br.com.escapelab.configuration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class GameConfigurationTest {

    @Test
    void acceptsPlayerThatExactlyFitsTheSmallestWorldDimension() {
        assertDoesNotThrow(() -> configuration(100, 100, 100.0));
    }

    @Test
    void rejectsPlayerLargerThanWorld() {
        assertThrows(IllegalArgumentException.class, () -> configuration(100, 100, 101.0));
    }

    @Test
    void rejectsDifferentWorldAndViewportUntilCameraExists() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new GameConfiguration(
                        "Escape Lab",
                        100,
                        100,
                        200,
                        100,
                        10,
                        20.0,
                        100.0,
                        0.1,
                        0.25));
    }

    private static GameConfiguration configuration(int width, int height, double playerSize) {
        return new GameConfiguration(
                "Escape Lab",
                width,
                height,
                width,
                height,
                10,
                playerSize,
                100.0,
                0.1,
                0.25);
    }
}
