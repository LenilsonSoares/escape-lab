package br.com.escapelab.presentation.javafx.input;

import br.com.escapelab.application.port.MovementInput;
import br.com.escapelab.domain.model.Direction;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;

/**
 * Adapter JavaFX responsável somente pelo estado do teclado.
 */
public final class KeyboardInput implements MovementInput {

    private final Set<KeyCode> pressedKeys = EnumSet.noneOf(KeyCode.class);
    private final EventHandler<KeyEvent> keyPressedHandler = this::handleKeyPressed;
    private final EventHandler<KeyEvent> keyReleasedHandler = this::handleKeyReleased;
    private final ChangeListener<Boolean> focusListener =
            (observable, wasFocused, isFocused) -> {
                if (Boolean.FALSE.equals(isFocused)) {
                    clear();
                }
            };

    private Scene boundScene;
    private ObservableValue<Boolean> boundFocus;

    public void bind(Scene scene, Window window) {
        Objects.requireNonNull(window, "window não pode ser nula");
        bind(scene, window.focusedProperty());
    }

    void bind(Scene scene, ObservableValue<Boolean> focus) {
        Objects.requireNonNull(scene, "scene não pode ser nula");
        Objects.requireNonNull(focus, "focus não pode ser nulo");

        unbind();
        boundScene = scene;
        boundFocus = focus;

        boundScene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedHandler);
        boundScene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedHandler);
        boundFocus.addListener(focusListener);
    }

    public void unbind() {
        if (boundScene != null) {
            boundScene.removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedHandler);
            boundScene.removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedHandler);
        }
        if (boundFocus != null) {
            boundFocus.removeListener(focusListener);
        }

        boundScene = null;
        boundFocus = null;
        clear();
    }

    @Override
    public Direction currentDirection() {
        int left = isPressed(KeyCode.A, KeyCode.LEFT) ? 1 : 0;
        int right = isPressed(KeyCode.D, KeyCode.RIGHT) ? 1 : 0;
        int up = isPressed(KeyCode.W, KeyCode.UP) ? 1 : 0;
        int down = isPressed(KeyCode.S, KeyCode.DOWN) ? 1 : 0;

        return new Direction(right - left, down - up);
    }

    public void clear() {
        pressedKeys.clear();
    }

    void press(KeyCode code) {
        Objects.requireNonNull(code, "code não pode ser nulo");
        if (isMovementKey(code)) {
            pressedKeys.add(code);
        }
    }

    void release(KeyCode code) {
        Objects.requireNonNull(code, "code não pode ser nulo");
        pressedKeys.remove(code);
    }

    private void handleKeyPressed(KeyEvent event) {
        if (isMovementKey(event.getCode())) {
            press(event.getCode());
            event.consume();
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        if (isMovementKey(event.getCode())) {
            release(event.getCode());
            event.consume();
        }
    }

    private boolean isPressed(KeyCode first, KeyCode second) {
        return pressedKeys.contains(first) || pressedKeys.contains(second);
    }

    private boolean isMovementKey(KeyCode code) {
        return switch (code) {
            case W, A, S, D, UP, DOWN, LEFT, RIGHT -> true;
            default -> false;
        };
    }
}
