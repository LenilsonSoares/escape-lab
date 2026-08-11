package br.com.escapelab.presentation.javafx;

import br.com.escapelab.application.game.GameEngine;
import java.util.Objects;
import javafx.animation.AnimationTimer;

/**
 * Adapter do pulso JavaFX para a sequência update → snapshot → render.
 */
public final class JavaFxGameLoop {

    private final GameEngine gameEngine;
    private final AnimationTimer animationTimer;

    private boolean running;

    public JavaFxGameLoop(GameEngine gameEngine) {
        this.gameEngine = Objects.requireNonNull(gameEngine, "gameEngine não pode ser nulo");
        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                JavaFxGameLoop.this.gameEngine.advance(now);
            }
        };
    }

    public void start() {
        if (running) {
            return;
        }

        gameEngine.start();
        running = true;
        animationTimer.start();
    }

    public void stop() {
        if (!running) {
            return;
        }

        animationTimer.stop();
        gameEngine.stop();
        running = false;
    }
}
