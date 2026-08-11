package br.com.escapelab.application.game;

import br.com.escapelab.application.port.MovementInput;
import br.com.escapelab.domain.model.Direction;
import br.com.escapelab.domain.model.Player;
import br.com.escapelab.domain.model.WorldBounds;
import java.util.Objects;

/**
 * Caso de uso que coordena o estado da partida sem conhecer JavaFX.
 */
public final class GameSession {

    private final Player player;
    private final MovementInput movementInput;
    private final WorldBounds worldBounds;

    public GameSession(Player player, MovementInput movementInput, WorldBounds worldBounds) {
        this.player = Objects.requireNonNull(player, "player não pode ser nulo");
        this.movementInput = Objects.requireNonNull(movementInput, "movementInput não pode ser nulo");
        this.worldBounds = Objects.requireNonNull(worldBounds, "worldBounds não pode ser nulo");

        if (!worldBounds.constrain(player.position(), player.size() / 2.0).equals(player.position())) {
            throw new IllegalArgumentException("A posição inicial do jogador deve estar dentro do mundo");
        }
    }

    public void update(double deltaSeconds) {
        Direction direction = Objects.requireNonNull(
                movementInput.currentDirection(),
                "MovementInput não pode retornar uma direção nula");
        player.move(direction, deltaSeconds, worldBounds);
    }

    public GameSnapshot snapshot() {
        return new GameSnapshot(PlayerSnapshot.from(player));
    }
}
