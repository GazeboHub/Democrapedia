package ws.gazebo.brontes.runner;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.codehaus.plexus.classworlds.ClassWorld;

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
		
		// cf.
		// http://stackoverflow.com/questions/5141788/how-to-run-maven-from-java
		// second answer

		// FIXME: how to use classworlds in MavenCli ?
		// http://classworlds.codehaus.org/
		// http://classworlds.codehaus.org/apiusage.html
		// Is not the same as
		// http://plexus.codehaus.org/plexus-classworlds/
		// which is more up to date (?)
		// see also
		// http://stackoverflow.com/questions/6223476/difference-between-plain-classworlds-and-plexus-classworlds
		// ClassWorld cw = new ClassWorld();
		ClassWorld cw = null; // ClassWorld needs plexus.core container class realm (?)

		CliRunner runner = new CliRunner(cw, args,
				System.getProperty("user.dir"), System.out, System.err);
		Thread runnerThread = new Thread(runner);
		runnerThread.run(); // No output. WTF?
		System.out.println("Returned: " + runner.getReturnValue()); // non-zero return ?
		
	}
}
