package org.example;

import java.io.PrintWriter;

/**
 * Interfata pe care o va implementa clasa Person, pentru designul Observer.
 */
public interface Observer {
    void update(String message, PrintWriter pw);
}
