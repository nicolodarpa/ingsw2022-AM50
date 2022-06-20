package it.polimi.ingsw.client;

import java.io.IOException;

/**
 * Used for the command mapped in {@link LineClient} and {@link DashboardController}
 */
public interface Command {
    /**
     * Runs a method
     * @throws IOException If running the called method an error occurred
     */
    void runCommand() throws IOException;
}
