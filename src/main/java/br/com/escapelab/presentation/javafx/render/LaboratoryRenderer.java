package br.com.escapelab.presentation.javafx.render;

import javafx.scene.canvas.GraphicsContext;

/**
 * Desenha o cenário provisório do laboratório.
 */
public final class LaboratoryRenderer {

    private static final double BORDER_INSET = 8.0;

    private final int tileSize;

    public LaboratoryRenderer(int tileSize) {
        if (tileSize <= 0) {
            throw new IllegalArgumentException("tileSize deve ser maior que zero");
        }
        this.tileSize = tileSize;
    }

    public void render(GraphicsContext graphics, double width, double height) {
        graphics.setFill(GameTheme.BACKGROUND);
        graphics.fillRect(0, 0, width, height);

        int columns = (int) Math.ceil(width / tileSize);
        int rows = (int) Math.ceil(height / tileSize);

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                graphics.setFill((row + column) % 2 == 0
                        ? GameTheme.TILE_PRIMARY
                        : GameTheme.TILE_SECONDARY);
                graphics.fillRect(
                        column * tileSize,
                        row * tileSize,
                        tileSize,
                        tileSize);
            }
        }

        graphics.setStroke(GameTheme.TILE_LINE);
        graphics.setLineWidth(1.0);
        for (int x = 0; x <= width; x += tileSize) {
            graphics.strokeLine(x, 0, x, height);
        }
        for (int y = 0; y <= height; y += tileSize) {
            graphics.strokeLine(0, y, width, y);
        }

        graphics.setStroke(GameTheme.ACCENT);
        graphics.setLineWidth(3.0);
        graphics.strokeRect(
                BORDER_INSET,
                BORDER_INSET,
                width - BORDER_INSET * 2.0,
                height - BORDER_INSET * 2.0);
    }
}
