package tictactoe.components;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A type of collection, used to loop through elements of an array.
 * @param <T> the type of the data to be stored.
 * @see ControlPanel
 */
public class Wheel<T> {
    /** Internal {@link Wheel} state. */
    ArrayList<T> messages;

    /**
     * Construct a {@link Wheel} from a {@code varargs} sequence that
     * will serve as this collection's internal state.
     * @param messages the {@link Object Objects} to be stored.
     */
    @SafeVarargs
    public Wheel(T... messages) {
        this.messages = new ArrayList<>(Arrays.asList(messages));
    }

    /**
     * Get the element that is currently in view of the wheel.
     * @return said element.
     */
    public T getCurrentElement() {
        return messages.get(0);
    }

    /**
     * Adjust the wheel 1 step in reverse.
     */
    public void retreat() {
        retreat(1);
    }

    /**
     * Adjust the wheel a custom amount of steps in reverse.
     * @param steps the amount of steps the wheel should be shifted.
     */
    public void retreat(int steps) {
        for (int i = 0; i < steps; i++) {
            T str = messages.get(messages.size() - 1);
            messages.remove(messages.size() - 1);
            messages.add(0, str);
        }
    }

    /**
     * Get the element proceeding {@link #getCurrentElement()} by shifting the internal array.
     * @return the previous element in this sequence.
     */
    public T getPreviousElement() {
        retreat();
        return messages.get(0);
    }

    /**
     * Advance the wheel 1 step forwards.
     */
    public void advance() {
        advance(1);
    }

    /**
     * Advance the wheel a custom amount of steps forwards.
     * @param steps the amount of steps the wheel should be shifted.
     */
    public void advance(int steps) {
        for (int i = 0; i < steps; i++) {
            T str = messages.get(0);
            messages.remove(0);
            messages.add(str);
        }
    }

    /**
     * Get the element following {@link #getCurrentElement()} by shifting the internal array.
     * @return the next element in this sequence.
     */
    public T getNextElement() {
        advance();
        return messages.get(0);
    }

    /**
     * Returns a {@link String} representation of the internal array. The element at
     * {@code index[0]} is the same as the value that would be returned by
     * {@link #getCurrentElement()}.
     * @return {@link ArrayList#toString()}
     */
    @Override
    public String toString() {
        return messages.toString();
    }
}
