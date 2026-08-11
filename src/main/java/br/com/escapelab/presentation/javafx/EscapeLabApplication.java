package br.com.escapelab.presentation.javafx;

import br.com.escapelab.application.game.GameEngine;
import br.com.escapelab.application.game.GameSession;
import br.com.escapelab.application.time.FpsCounter;
import br.com.escapelab.application.time.FrameClock;
import br.com.escapelab.configuration.GameConfiguration;
import br.com.escapelab.domain.model.Player;
import br.com.escapelab.domain.model.WorldBounds;
import br.com.escapelab.presentation.javafx.input.KeyboardInput;
import br.com.escapelab.presentation.javafx.render.CanvasGameRenderer;
import br.com.escapelab.presentation.javafx.render.HudRenderer;
import br.com.escapelab.presentation.javafx.render.LaboratoryRenderer;
import br.com.escapelab.presentation.javafx.render.PlayerRenderer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Composition root: cria as dependências e controla o ciclo de vida do JavaFX.
 */
public final class EscapeLabApplication extends Application {

    private JavaFxGameLoop gameLoop;
    private KeyboardInput keyboardInput;

    public static void launchApplication(String[] args) {
        Application.launch(EscapeLabApplication.class, args);
    }

    @Override
    public void start(Stage stage) {
        GameConfiguration configuration = GameConfiguration.defaultConfiguration();
        WorldBounds worldBounds = new WorldBounds(
                configuration.worldWidth(),
                configuration.worldHeight());
        Player player = new Player(
                worldBounds.center(),
                configuration.playerSize(),
                configuration.playerSpeed());

        Canvas canvas = new Canvas(
                configuration.viewportWidth(),
                configuration.viewportHeight());
        canvas.setFocusTraversable(true);

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(
                root,
                configuration.viewportWidth(),
                configuration.viewportHeight());

        keyboardInput = new KeyboardInput();
        GameSession session = new GameSession(player, keyboardInput, worldBounds);
        CanvasGameRenderer renderer = new CanvasGameRenderer(
                canvas,
                new LaboratoryRenderer(configuration.tileSize()),
                new PlayerRenderer(),
                new HudRenderer());

        GameEngine gameEngine = new GameEngine(
                session,
                renderer,
                new FrameClock(configuration.maximumDeltaSeconds()),
                new FpsCounter(configuration.fpsSampleWindowSeconds()));
        gameLoop = new JavaFxGameLoop(gameEngine);

        stage.setTitle(configuration.windowTitle());
        stage.setScene(scene);
        stage.setResizable(false);

        stage.setOnShown(event -> {
            keyboardInput.bind(scene, stage);
            canvas.requestFocus();
            gameLoop.start();
        });
        stage.setOnHidden(event -> shutdown());
        stage.show();
    }

    @Override
    public void stop() {
        shutdown();
    }

    private void shutdown() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        if (keyboardInput != null) {
            keyboardInput.unbind();
        }
    }
}
