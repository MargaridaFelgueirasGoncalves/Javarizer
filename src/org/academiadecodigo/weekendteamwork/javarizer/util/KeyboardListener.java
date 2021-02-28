package org.academiadecodigo.weekendteamwork.javarizer.util;

import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class KeyboardListener implements KeyboardHandler {

    // constructor
    public KeyboardListener() {

        this.setup();
    }

    public void setup() {
        Keyboard keyboard = new Keyboard(this);

        KeyboardEvent q = new KeyboardEvent();
        q.setKey(KeyboardEvent.KEY_Q);
        q.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(q);

    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_Q) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
    }
}
