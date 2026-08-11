package br.com.escapelab.application.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.escapelab.domain.model.Direction;
import br.com.escapelab.domain.model.Player;
import br.com.escapelab.domain.model.Position;
import br.com.escapelab.domain.model.WorldBounds;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

class GameSessionTest {

    private static final WorldBounds WORLD = new WorldBounds(960.0, 540.0);

    @Test
    void updatesPlayerFromMovementPort() {
        AtomicReference<Direction> input = new AtomicReference<>(new Direction(1.0, 0.0));
        Player player = new Player(new Position(100.0, 100.0), 30.0, 200.0);
        GameSession session = new GameSession(player, input::get, WORLD);

        session.update(0.5);

        assertEquals(200.0, session.snapshot().player().position().x(), 0.000_001);
    }

    @Test
    void producesImmutableSnapshotsAfterUpdate() {
        AtomicReference<Direction> input = new AtomicReference<>(Direction.NONE);
        Player player = new Player(new Position(100.0, 100.0), 30.0, 200.0);
        GameSession session = new GameSession(player, input::get, WORLD);
        GameSnapshot beforeMovement = session.snapshot();

        input.set(new Direction(0.0, 1.0));
        session.update(0.5);
        GameSnapshot afterMovement = session.snapshot();

        assertEquals(new Position(100.0, 100.0), beforeMovement.player().position());
        assertEquals(new Position(100.0, 200.0), afterMovement.player().position());
    }

    @Test
    void rejectsNullDirectionReturnedByPort() {
        Player player = new Player(new Position(100.0, 100.0), 30.0, 200.0);
        GameSession session = new GameSession(player, () -> null, WORLD);

        assertThrows(NullPointerException.class, () -> session.update(0.016));
    }

    @Test
    void rejectsPlayerThatDoesNotFitAtSpawn() {
        Player outside = new Player(new Position(5.0, 5.0), 30.0, 200.0);
        assertThrows(
                IllegalArgumentException.class,
                () -> new GameSession(outside, () -> Direction.NONE, WORLD));

        Player oversized = new Player(new Position(480.0, 270.0), 600.0, 200.0);
        assertThrows(
                IllegalArgumentException.class,
                () -> new GameSession(oversized, () -> Direction.NONE, WORLD));
    }
}
