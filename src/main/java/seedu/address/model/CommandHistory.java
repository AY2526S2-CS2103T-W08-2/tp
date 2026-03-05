package seedu.address.model;

import java.util.ArrayDeque;
import java.util.Deque;

import seedu.address.logic.commands.Command;

/**
 * Represents the history of executed commands.
 * Modified from <a href="https://github.com/jolenechong/ip">jolenechong</a>
 */
public class CommandHistory {
    private final Deque<Command> stack = new ArrayDeque<>();

    /**
     * Pushes a command onto the history stack.
     *
     * @param cmd the command to be added to history.
     */
    public void push(Command cmd) {
        stack.push(cmd);
    }

    /**
     * Pops the most recently executed command from the history stack.
     *
     * @return the most recently executed command, or null if the stack is empty.
     */
    public Command pop() {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.pop();
    }

    /**
     * Checks if the history stack is empty.
     *
     * @return true if the history stack is empty, false otherwise.
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }

}
