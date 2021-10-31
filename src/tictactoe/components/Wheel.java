package tictactoe.components;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A type of collection, used to loop through elements of an array.
 * @param <T> the type of the data to be stored.
 * @see ControlPanel
 */
public class Wheel<T> {
    ArrayList<T> messages;

    @SafeVarargs
    public Wheel(T... messages) {
        this.messages = new ArrayList<>(Arrays.asList(messages));
    }

    public T getPreviousElement() {
        retreat();
        return messages.get(0);
    }

    public T getCurrentElement() {
        return messages.get(0);
    }

    public void retreat() {
        retreat(1);
    }

    public void retreat(int steps) {
        for (int i = 0; i < steps; i++) {
            T str = messages.get(messages.size() - 1);
            messages.remove(messages.size() - 1);
            messages.add(0, str);
        }
    }

    public void advance(int steps) {
        for (int i = 0; i < steps; i++) {
            T str = messages.get(0);
            messages.remove(0);
            messages.add(str);
        }
    }

    public void advance() {
        advance(1);
    }

    public T getNextElement() {
        advance();
        return messages.get(0);
    }

    @Override
    public String toString() {
        return messages.toString();
    }
}
