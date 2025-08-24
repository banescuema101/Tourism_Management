package org.example;

import java.io.PrintWriter;

/**
* Interface that will be implemented by the Person class for the Observer design pattern.
*/
public interface Observer {
    void update(String message, PrintWriter pw);
}
