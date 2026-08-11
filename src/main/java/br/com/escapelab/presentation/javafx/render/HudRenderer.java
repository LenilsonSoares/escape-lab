package br.com.escapelab.presentation.javafx.render;

import br.com.escapelab.application.game.GameMetrics;
import br.com.escapelab.application.game.GameSnapshot;
import java.util.Locale;
import javafx.scene.canvas.GraphicsContext;

/**
 * Desenha somente as informações de diagnóstico do protótipo.
 */
public final class HudRenderer {

    public void render(
            GraphicsContext graphics,
            GameSnapshot snapshot,
            GameMetrics metrics,
            double width,
            double height) {
        graphics.setFill(GameTheme.HUD_BACKGROUND);
        graphics.fillRoundRect(24, 22, 330, 112, 12, 12);
        graphics.setStroke(GameTheme.HUD_BORDER);
        graphics.setLineWidth(1.5);
        graphics.strokeRoundRect(24, 22, 330, 112, 12, 12);

        graphics.setFont(GameTheme.HUD_TITLE_FONT);
        graphics.setFill(GameTheme.ACCENT_BRIGHT);
        graphics.fillText("ESCAPE LAB // PROTÓTIPO 01", 40, 49);

        graphics.setFont(GameTheme.HUD_FONT);
        graphics.setFill(GameTheme.HUD_TEXT);
        graphics.fillText(
                String.format(
                        Locale.ROOT,
                        "POSIÇÃO  x: %6.1f  y: %6.1f",
                        snapshot.player().position().x(),
                        snapshot.player().position().y()),
                40,
                75);
        graphics.fillText(
                String.format(
                        Locale.ROOT,
                        "FPS: %5.1f   DELTA: %6.2f ms",
                        metrics.framesPerSecond(),
                        metrics.deltaSeconds() * 1_000.0),
                40,
                98);

        graphics.setFill(GameTheme.HUD_MUTED);
        graphics.fillText("MOVIMENTO: WASD ou SETAS", 40, 121);

        graphics.setFont(GameTheme.HUD_SMALL_FONT);
        graphics.setFill(GameTheme.HUD_SUBTLE);
        graphics.fillText(
                String.format(
                        Locale.ROOT,
                        "dt interno: %.4f s",
                        metrics.deltaSeconds()),
                width - 165,
                height - 24);
    }
}
