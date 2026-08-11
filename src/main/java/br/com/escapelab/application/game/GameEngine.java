package br.com.escapelab.application.game;

import br.com.escapelab.application.port.GameOutput;
import br.com.escapelab.application.time.FpsCounter;
import br.com.escapelab.application.time.FrameClock;
import br.com.escapelab.application.time.FrameTiming;
import java.util.Objects;

/**
 * Coordena um frame completo sem depender do mecanismo que fornece os pulsos.
 */
public final class GameEngine {

    private final GameSession session;
    private final GameOutput output;
    private final FrameClock frameClock;
    private final FpsCounter fpsCounter;

    private boolean running;

    public GameEngine(
            GameSession session,
            GameOutput output,
            FrameClock frameClock,
            FpsCounter fpsCounter) {
        this.session = Objects.requireNonNull(session, "session não pode ser nula");
        this.output = Objects.requireNonNull(output, "output não pode ser nulo");
        this.frameClock = Objects.requireNonNull(frameClock, "frameClock não pode ser nulo");
        this.fpsCounter = Objects.requireNonNull(fpsCounter, "fpsCounter não pode ser nulo");
    }

    public void start() {
        if (running) {
            return;
        }

        frameClock.reset();
        fpsCounter.reset();
        output.render(session.snapshot(), GameMetrics.INITIAL);
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void advance(long nowNanos) {
        if (!running) {
            return;
        }

        FrameTiming timing = frameClock.advance(nowNanos);
        if (timing.elapsedSeconds() == 0.0) {
            return;
        }

        session.update(timing.simulationDeltaSeconds());
        double framesPerSecond = fpsCounter.recordFrame(timing.elapsedSeconds());
        GameMetrics metrics = new GameMetrics(
                framesPerSecond,
                timing.simulationDeltaSeconds());

        output.render(session.snapshot(), metrics);
    }
}
