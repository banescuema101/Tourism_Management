package org.example;

/**
 * The interface that will be implemented by the specific commands.
 */
public abstract class Command {

    // throws Exception to be able to throw in specific commands, personalized exceptions
    // for each specific order, they extend from the beginning the Exception class.
    public abstract void execute() throws Exception;
}
