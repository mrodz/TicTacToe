package tictactoe;

import external.LogMessage;

/**
 * This class serves as the entrypoint to the program.
 */
public class ApplicationRunner {

    static {
        // Set default logger to write messages to System#out
        LogMessage.setDefaultOutputStreams(System.out);
    }

    /**
     * Main method
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            Runnable r = TicTacToe::new;
            javax.swing.SwingUtilities.invokeAndWait(r);
        } catch (Exception t) {
            new external.LogMessage("The application encountered a fatal " +
                    "exception ("+t+") while running that terminated this instance of " +
                    "the program. <Message = "+t.getMessage()+">", external.LogMessage.FATAL);
            if (tictactoe.internal.AppConstants.STACK_TRACE_ON_EXCEPTION) t.printStackTrace();
        } finally {
            System.out.println("Launched!");
        }
    }
}