package br.com.escapelab.application.port;

import br.com.escapelab.application.game.GameMetrics;
import br.com.escapelab.application.game.GameSnapshot;

/**
 * Porta de saída que recebe o estado e as métricas calculados para um frame.
 */
@FunctionalInterface
public interface GameOutput {

    void render(GameSnapshot snapshot, GameMetrics metrics);
}
