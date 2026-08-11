package br.com.escapelab.presentation.javafx.render;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Recursos visuais imutáveis e reutilizados entre frames.
 */
final class GameTheme {

    static final Color BACKGROUND = Color.web("#091117");
    static final Color TILE_PRIMARY = Color.web("#111e26");
    static final Color TILE_SECONDARY = Color.web("#13232c");
    static final Color TILE_LINE = Color.web("#203640");
    static final Color ACCENT = Color.web("#27d3b2");
    static final Color ACCENT_BRIGHT = Color.web("#7fffe8");
    static final Color PLAYER_BODY = Color.web("#e7f5f4");
    static final Color PLAYER_VISOR = Color.web("#15333b");
    static final Color SHADOW = Color.web("#000000", 0.35);
    static final Color HUD_BACKGROUND = Color.web("#050a0e", 0.88);
    static final Color HUD_BORDER = Color.web("#27d3b2", 0.75);
    static final Color HUD_TEXT = Color.web("#d6e7ea");
    static final Color HUD_MUTED = Color.web("#8da5aa");
    static final Color HUD_SUBTLE = Color.web("#6f8d93");

    static final Font HUD_TITLE_FONT = Font.font("Monospaced", FontWeight.BOLD, 17);
    static final Font HUD_FONT = Font.font("Monospaced", 14);
    static final Font HUD_SMALL_FONT = Font.font("Monospaced", 12);

    private GameTheme() {
    }
}
