package br.com.escapelab.application.game;

import br.com.escapelab.domain.model.Direction;
import br.com.escapelab.domain.model.Player;
import br.com.escapelab.domain.model.Position;
import java.util.Objects;

/**
 * Visão imutável do jogador entregue para as camadas externas.
 */
public record PlayerSnapshot(Position position, Direction facing, double size) {

    public PlayerSnapshot {
        Objects.requireNonNull(position, "position não pode ser nula");
        Objects.requireNonNull(facing, "facing não pode ser nula");
        if (!Double.isFinite(size) || size <= 0.0) {
            throw new IllegalArgumentException("size deve ser maior que zero e finito");
        }
    }

    public static PlayerSnapshot from(Player player) {
        Objects.requireNonNull(player, "player não pode ser nulo");
        return new PlayerSnapshot(player.position(), player.facing(), player.size());
    }
}
