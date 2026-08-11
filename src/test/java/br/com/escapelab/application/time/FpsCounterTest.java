package br.com.escapelab.application.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FpsCounterTest {

    @Test
    void calculatesFpsAtSampleBoundary() {
        FpsCounter counter = new FpsCounter(0.25);

        assertEquals(0.0, counter.recordFrame(0.10));
        assertEquals(8.0, counter.recordFrame(0.15), 0.000_001);
    }

    @Test
    void usesRealElapsedTimeWhenSampleOvershoots() {
        FpsCounter counter = new FpsCounter(0.25);

        counter.recordFrame(0.10);

        assertEquals(2.0 / 0.30, counter.recordFrame(0.20), 0.000_001);
    }

    @Test
    void zeroTimeIsANoOpAndResetClearsPartialSample() {
        FpsCounter counter = new FpsCounter(0.25);
        counter.recordFrame(0.10);

        assertEquals(0.0, counter.recordFrame(0.0));

        counter.reset();
        assertEquals(0.0, counter.recordFrame(0.15));
        assertEquals(8.0, counter.recordFrame(0.10), 0.000_001);
    }

    @Test
    void rejectsInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> new FpsCounter(0.0));

        FpsCounter counter = new FpsCounter(0.25);
        assertThrows(IllegalArgumentException.class, () -> counter.recordFrame(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> counter.recordFrame(-0.1));
    }
}
