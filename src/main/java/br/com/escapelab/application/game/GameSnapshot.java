package br.com.escapelab.application.game;

import java.util.Objects;

/**
 * Estado imutável de um frame, consumido pela apresentação.
 */
public record GameSnapshot(PlayerSnapshot player) {

    public GameSnapshot {
        Objects.requireNonNull(player, "player não pode ser nulo");
    }
}
