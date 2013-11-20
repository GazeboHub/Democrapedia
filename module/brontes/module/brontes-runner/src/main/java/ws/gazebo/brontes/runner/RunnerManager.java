package ws.gazebo.brontes.runner;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class RunnerManager {
	

	public static void main(String[] args) {
		// in-place test method

	    Logger rootLogger = Logger.getRootLogger();
	    rootLogger.setLevel(Level.INFO);
	    rootLogger.addAppender(new ConsoleAppender(
	               new PatternLayout("%-6r [%p] %c - %m%n")));

		
		System.out.println("Base directory: " + System.getProperty("user.dir"));
		
		System.out.println("Args:");
		for(String a : args)  {
			System.out.println("\t" + a);
		}
		
	
		
	}
}
