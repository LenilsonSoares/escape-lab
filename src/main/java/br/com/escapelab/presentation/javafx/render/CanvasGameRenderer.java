package br.com.escapelab.presentation.javafx.render;

import br.com.escapelab.application.game.GameMetrics;
import br.com.escapelab.application.game.GameSnapshot;
import br.com.escapelab.application.port.GameOutput;
import java.util.Objects;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Orquestra os renderizadores especializados sobre um Canvas JavaFX.
 */
public final class CanvasGameRenderer implements GameOutput {

    private final Canvas canvas;
    private final GraphicsContext graphics;
    private final LaboratoryRenderer laboratoryRenderer;
    private final PlayerRenderer playerRenderer;
    private final HudRenderer hudRenderer;

    public CanvasGameRenderer(
            Canvas canvas,
            LaboratoryRenderer laboratoryRenderer,
            PlayerRenderer playerRenderer,
            HudRenderer hudRenderer) {
        this.canvas = Objects.requireNonNull(canvas, "canvas não pode ser nulo");
        this.graphics = canvas.getGraphicsContext2D();
        this.laboratoryRenderer = Objects.requireNonNull(
                laboratoryRenderer,
                "laboratoryRenderer não pode ser nulo");
        this.playerRenderer = Objects.requireNonNull(playerRenderer, "playerRenderer não pode ser nulo");
        this.hudRenderer = Objects.requireNonNull(hudRenderer, "hudRenderer não pode ser nulo");
    }

    @Override
    public void render(GameSnapshot snapshot, GameMetrics metrics) {
        Objects.requireNonNull(snapshot, "snapshot não pode ser nulo");
        Objects.requireNonNull(metrics, "metrics não pode ser nula");

        graphics.save();
        try {
            laboratoryRenderer.render(graphics, canvas.getWidth(), canvas.getHeight());
        } finally {
            graphics.restore();
        }

        graphics.save();
        try {
            playerRenderer.render(graphics, snapshot.player());
        } finally {
            graphics.restore();
        }

        graphics.save();
        try {
            hudRenderer.render(graphics, snapshot, metrics, canvas.getWidth(), canvas.getHeight());
        } finally {
            graphics.restore();
        }
    }
}
