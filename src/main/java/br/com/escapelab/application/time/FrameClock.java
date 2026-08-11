package br.com.escapelab.application.time;

/**
 * Converte timestamps do loop em deltas determinísticos e seguros para a simulação.
 */
public final class FrameClock {

    private static final double NANOS_PER_SECOND = 1_000_000_000.0;

    private final double maximumDeltaSeconds;

    private boolean initialized;
    private long previousFrameNanos;

    public FrameClock(double maximumDeltaSeconds) {
        if (!Double.isFinite(maximumDeltaSeconds) || maximumDeltaSeconds <= 0.0) {
            throw new IllegalArgumentException("maximumDeltaSeconds deve ser maior que zero e finito");
        }
        this.maximumDeltaSeconds = maximumDeltaSeconds;
    }

    public FrameTiming advance(long nowNanos) {
        if (nowNanos < 0L) {
            throw new IllegalArgumentException("nowNanos não pode ser negativo");
        }

        if (!initialized) {
            initialized = true;
            previousFrameNanos = nowNanos;
            return FrameTiming.INITIAL;
        }

        if (nowNanos <= previousFrameNanos) {
            return FrameTiming.INITIAL;
        }

        double elapsedSeconds = (nowNanos - previousFrameNanos) / NANOS_PER_SECOND;
        previousFrameNanos = nowNanos;

        return new FrameTiming(
                elapsedSeconds,
                Math.min(elapsedSeconds, maximumDeltaSeconds));
    }

    public void reset() {
        initialized = false;
        previousFrameNanos = 0L;
    }
}
