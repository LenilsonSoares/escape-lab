package br.com.escapelab.application.time;

/**
 * Calcula FPS em uma janela de amostragem usando o tempo real, sem sleeps.
 */
public final class FpsCounter {

    private final double sampleWindowSeconds;

    private double accumulatedSeconds;
    private int accumulatedFrames;
    private double framesPerSecond;

    public FpsCounter(double sampleWindowSeconds) {
        if (!Double.isFinite(sampleWindowSeconds) || sampleWindowSeconds <= 0.0) {
            throw new IllegalArgumentException("sampleWindowSeconds deve ser maior que zero e finito");
        }
        this.sampleWindowSeconds = sampleWindowSeconds;
    }

    public double recordFrame(double elapsedSeconds) {
        if (!Double.isFinite(elapsedSeconds)) {
            throw new IllegalArgumentException("elapsedSeconds deve ser finito");
        }
        if (elapsedSeconds < 0.0) {
            throw new IllegalArgumentException("elapsedSeconds não pode ser negativo");
        }
        if (elapsedSeconds == 0.0) {
            return framesPerSecond;
        }

        accumulatedSeconds += elapsedSeconds;
        accumulatedFrames++;

        if (accumulatedSeconds >= sampleWindowSeconds) {
            framesPerSecond = accumulatedFrames / accumulatedSeconds;
            accumulatedSeconds = 0.0;
            accumulatedFrames = 0;
        }

        return framesPerSecond;
    }

    public void reset() {
        accumulatedSeconds = 0.0;
        accumulatedFrames = 0;
        framesPerSecond = 0.0;
    }
}
