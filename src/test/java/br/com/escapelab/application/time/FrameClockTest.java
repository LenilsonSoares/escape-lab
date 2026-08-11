package br.com.escapelab.application.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FrameClockTest {

    @Test
    void firstFrameIsZeroEvenWhenClockStartsAtZero() {
        FrameClock clock = new FrameClock(0.1);

        assertEquals(FrameTiming.INITIAL, clock.advance(0L));
        assertEquals(new FrameTiming(0.016, 0.016), clock.advance(16_000_000L));
    }

    @Test
    void limitsOnlySimulationDeltaAfterLongPause() {
        FrameClock clock = new FrameClock(0.1);
        clock.advance(1_000_000_000L);

        FrameTiming timing = clock.advance(3_000_000_000L);

        assertEquals(2.0, timing.elapsedSeconds());
        assertEquals(0.1, timing.simulationDeltaSeconds());
    }

    @Test
    void ignoresRepeatedAndRegressiveTimestamps() {
        FrameClock clock = new FrameClock(0.1);
        clock.advance(100L);

        assertEquals(FrameTiming.INITIAL, clock.advance(100L));
        assertEquals(FrameTiming.INITIAL, clock.advance(90L));
        assertEquals(0.000_000_010, clock.advance(110L).elapsedSeconds(), 0.000_000_000_1);
    }

    @Test
    void resetStartsANewSample() {
        FrameClock clock = new FrameClock(0.1);
        clock.advance(100L);
        clock.advance(200L);

        clock.reset();

        assertEquals(FrameTiming.INITIAL, clock.advance(10_000L));
    }

    @Test
    void rejectsInvalidConfigurationAndTimestamp() {
        assertThrows(IllegalArgumentException.class, () -> new FrameClock(0.0));

        FrameClock clock = new FrameClock(0.1);
        assertThrows(IllegalArgumentException.class, () -> clock.advance(-1L));
    }
}
