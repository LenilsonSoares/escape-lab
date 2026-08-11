package br.com.escapelab.presentation.javafx.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.escapelab.domain.model.Direction;
import java.awt.GraphicsEnvironment;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class KeyboardInputBindingTest {

    private static boolean toolkitStarted;

    @BeforeAll
    static void startJavaFxToolkit() throws InterruptedException {
        Assumptions.assumeTrue(displayIsAvailable(), "Teste JavaFX requer um ambiente gráfico");

        CountDownLatch started = new CountDownLatch(1);
        Platform.startup(started::countDown);
        if (!started.await(5, TimeUnit.SECONDS)) {
            throw new IllegalStateException("JavaFX não iniciou dentro do tempo esperado");
        }
        toolkitStarted = true;
    }

    @AfterAll
    static void stopJavaFxToolkit() {
        if (toolkitStarted) {
            Platform.exit();
        }
    }

    @Test
    void bindsEventsClearsOnFocusLossAndUnbinds() {
        KeyboardInput input = new KeyboardInput();
        Scene scene = new Scene(new Group());
        SimpleBooleanProperty focused = new SimpleBooleanProperty(true);
        AtomicInteger eventsDeliveredAfterFilters = new AtomicInteger();
        scene.addEventHandler(
                KeyEvent.KEY_PRESSED,
                event -> eventsDeliveredAfterFilters.incrementAndGet());
        input.bind(scene, focused);

        Event.fireEvent(scene, keyPressed(KeyCode.W));

        assertEquals(0, eventsDeliveredAfterFilters.get());
        assertEquals(new Direction(0.0, -1.0), input.currentDirection());

        Event.fireEvent(scene, keyPressed(KeyCode.SPACE));
        assertEquals(1, eventsDeliveredAfterFilters.get());

        focused.set(false);
        assertEquals(Direction.NONE, input.currentDirection());

        input.unbind();
        Event.fireEvent(scene, keyPressed(KeyCode.D));
        assertEquals(Direction.NONE, input.currentDirection());
    }

    @Test
    void rebindRemovesHandlersFromPreviousScene() {
        KeyboardInput input = new KeyboardInput();
        Scene firstScene = new Scene(new Group());
        Scene secondScene = new Scene(new Group());

        input.bind(firstScene, new SimpleBooleanProperty(true));
        input.bind(secondScene, new SimpleBooleanProperty(true));

        Event.fireEvent(firstScene, keyPressed(KeyCode.A));
        assertEquals(Direction.NONE, input.currentDirection());

        Event.fireEvent(secondScene, keyPressed(KeyCode.D));
        assertEquals(new Direction(1.0, 0.0), input.currentDirection());
    }

    private static boolean displayIsAvailable() {
        if (GraphicsEnvironment.isHeadless()) {
            return false;
        }

        String operatingSystem = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
        if (!operatingSystem.contains("linux")) {
            return true;
        }

        return System.getenv("DISPLAY") != null || System.getenv("WAYLAND_DISPLAY") != null;
    }

    private static KeyEvent keyPressed(KeyCode code) {
        return new KeyEvent(
                KeyEvent.KEY_PRESSED,
                "",
                "",
                code,
                false,
                false,
                false,
                false);
    }
}
