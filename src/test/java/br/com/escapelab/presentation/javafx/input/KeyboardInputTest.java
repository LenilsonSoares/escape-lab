package br.com.escapelab.presentation.javafx.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.escapelab.domain.model.Direction;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

class KeyboardInputTest {

    @Test
    void mapsWasdAndArrowKeys() {
        KeyboardInput input = new KeyboardInput();
        input.press(KeyCode.W);
        input.press(KeyCode.LEFT);

        assertEquals(new Direction(-1.0, -1.0), input.currentDirection());

        input.clear();
        input.press(KeyCode.S);
        input.press(KeyCode.RIGHT);

        assertEquals(new Direction(1.0, 1.0), input.currentDirection());
    }

    @Test
    void equivalentKeysRemainActiveUntilBothAreReleased() {
        KeyboardInput input = new KeyboardInput();
        input.press(KeyCode.W);
        input.press(KeyCode.UP);

        input.release(KeyCode.W);
        assertEquals(new Direction(0.0, -1.0), input.currentDirection());

        input.release(KeyCode.UP);
        assertEquals(Direction.NONE, input.currentDirection());
    }

    @Test
    void oppositeKeysCancelEachOther() {
        KeyboardInput input = new KeyboardInput();
        input.press(KeyCode.A);
        input.press(KeyCode.D);
        input.press(KeyCode.UP);
        input.press(KeyCode.DOWN);

        assertEquals(Direction.NONE, input.currentDirection());
    }

    @Test
    void repeatedAndUnrelatedKeysDoNotChangeStateIncorrectly() {
        KeyboardInput input = new KeyboardInput();
        input.press(KeyCode.D);
        input.press(KeyCode.D);
        input.press(KeyCode.SPACE);

        assertEquals(new Direction(1.0, 0.0), input.currentDirection());

        input.clear();
        assertEquals(Direction.NONE, input.currentDirection());
    }

}
