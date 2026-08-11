package br.com.escapelab.application.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.escapelab.application.time.FpsCounter;
import br.com.escapelab.application.time.FrameClock;
import br.com.escapelab.domain.model.Direction;
import br.com.escapelab.domain.model.Player;
import br.com.escapelab.domain.model.Position;
import br.com.escapelab.domain.model.WorldBounds;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class GameEngineTest {

    private static final WorldBounds WORLD = new WorldBounds(960.0, 540.0);

    @Test
    void runsUpdateSnapshotAndRenderWithTheCorrectDeltas() {
        List<RenderedFrame> frames = new ArrayList<>();
        Player player = new Player(new Position(100.0, 100.0), 30.0, 100.0);
        GameSession session = new GameSession(player, () -> new Direction(1.0, 0.0), WORLD);
        GameEngine engine = new GameEngine(
                session,
                (snapshot, metrics) -> frames.add(new RenderedFrame(snapshot, metrics)),
                new FrameClock(0.1),
                new FpsCounter(0.25));

        engine.advance(0L);
        assertEquals(0, frames.size());

        engine.start();
        engine.advance(0L);
        engine.advance(2_000_000_000L);

        assertEquals(2, frames.size());
        RenderedFrame frame = frames.get(1);
        assertEquals(110.0, frame.snapshot().player().position().x(), 0.000_001);
        assertEquals(0.1, frame.metrics().deltaSeconds());
        assertEquals(0.5, frame.metrics().framesPerSecond(), 0.000_001);
    }

    @Test
    void ignoresRepeatedRegressiveAndStoppedFrames() {
        List<RenderedFrame> frames = new ArrayList<>();
        Player player = new Player(new Position(100.0, 100.0), 30.0, 100.0);
        GameSession session = new GameSession(player, () -> Direction.NONE, WORLD);
        GameEngine engine = new GameEngine(
                session,
                (snapshot, metrics) -> frames.add(new RenderedFrame(snapshot, metrics)),
                new FrameClock(0.1),
                new FpsCounter(0.25));

        engine.start();
        engine.advance(100L);
        engine.advance(100L);
        engine.advance(90L);
        assertEquals(1, frames.size());

        engine.advance(110L);
        assertEquals(2, frames.size());

        engine.stop();
        engine.advance(1_000_000_000L);
        assertEquals(2, frames.size());
    }

    @Test
    void restartResetsClockFpsAndIsIdempotent() {
        List<RenderedFrame> frames = new ArrayList<>();
        Player player = new Player(new Position(100.0, 100.0), 30.0, 100.0);
        GameSession session = new GameSession(player, () -> Direction.NONE, WORLD);
        GameEngine engine = new GameEngine(
                session,
                (snapshot, metrics) -> frames.add(new RenderedFrame(snapshot, metrics)),
                new FrameClock(0.1),
                new FpsCounter(0.25));

        engine.start();
        engine.start();
        assertEquals(1, frames.size());

        engine.advance(0L);
        engine.advance(250_000_000L);
        assertEquals(4.0, frames.get(1).metrics().framesPerSecond(), 0.000_001);

        engine.stop();
        engine.start();
        assertEquals(GameMetrics.INITIAL, frames.get(2).metrics());

        engine.advance(1_000_000_000L);
        assertEquals(3, frames.size());
    }

    private record RenderedFrame(GameSnapshot snapshot, GameMetrics metrics) {
    }
}
