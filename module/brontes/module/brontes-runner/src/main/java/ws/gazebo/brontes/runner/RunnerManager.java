package ws.gazebo.brontes.runner;

import org.apache.maven.cli.CLIReportingUtils;
import org.apache.maven.cli.MavenCli;
import org.codehaus.plexus.classworlds.ClassWorld;

public class RunnerManager {

	public static void main(String[] args) {
		// in-place test method

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
		ClassWorld cw = new ClassWorld();

		CliRunner runner = new CliRunner(cw, args,
				System.getProperty("user.dir"), System.out, System.err);
		Thread runnerThread = new Thread(runner);
		runnerThread.run();
		
	}
}
