package pl.polsl.lukasz.latusik.lab.server;

/**
 The interface Output sender.
 @author Łukasz Latusik
 @version 1.0
 */
@FunctionalInterface
public interface OutputSender {
	/**
	 Manage output.
	 
	 @param message the message
	 */
	void manageOutput(String message);
}
