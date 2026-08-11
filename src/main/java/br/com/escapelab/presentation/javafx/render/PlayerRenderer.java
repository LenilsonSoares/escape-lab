package br.com.escapelab.presentation.javafx.render;

import br.com.escapelab.application.game.PlayerSnapshot;
import javafx.scene.canvas.GraphicsContext;

/**
 * Representação JavaFX do jogador. Nenhuma regra de movimento vive aqui.
 */
public final class PlayerRenderer {

    public void render(GraphicsContext graphics, PlayerSnapshot player) {
        double size = player.size();
        double halfSize = size / 2.0;
        double x = player.position().x();
        double y = player.position().y();
        double left = x - halfSize;
        double top = y - halfSize;
        double cornerRadius = size * 0.23;
        double bodyLineWidth = Math.max(0.5, size / 12.0);

        graphics.setFill(GameTheme.SHADOW);
        graphics.fillOval(
                left + size * 0.10,
                top + size * 0.87,
                size * 0.80,
                size * 0.30);

        graphics.setFill(GameTheme.PLAYER_BODY);
        graphics.fillRoundRect(left, top, size, size, cornerRadius, cornerRadius);
        graphics.setStroke(GameTheme.ACCENT);
        graphics.setLineWidth(bodyLineWidth);
        graphics.strokeRoundRect(left, top, size, size, cornerRadius, cornerRadius);

        graphics.setFill(GameTheme.PLAYER_VISOR);
        graphics.fillRoundRect(
                left + size / 6.0,
                top + size * 0.20,
                size * 2.0 / 3.0,
                size * 0.30,
                size * 0.13,
                size * 0.13);
        graphics.setFill(GameTheme.ACCENT_BRIGHT);
        graphics.fillRect(
                left + size * 0.27,
                top + size * 0.30,
                size * 0.46,
                size * 0.10);

        graphics.setStroke(GameTheme.ACCENT);
        graphics.setLineWidth(Math.max(0.5, size / 15.0));
        graphics.strokeLine(
                x,
                y,
                x + player.facing().horizontal() * size * 0.37,
                y + player.facing().vertical() * size * 0.37);
    }
}
