package ws.gazebo.brontes.runner;

import java.io.PrintStream;

import org.apache.maven.cli.MavenCli;
import org.codehaus.plexus.classworlds.ClassWorld;

public class CliRunner implements Runnable {

	protected final String[] args;
	protected final String workingDirectory;
	protected final PrintStream stdout;
	protected final PrintStream stderr;
	protected final ClassWorld classworld;
	protected final MavenCli cliInstance;
	protected int returnValue;

	public CliRunner(ClassWorld classworld, String[] args,
			String workingDirectory, PrintStream stdout, PrintStream stderr) {
		super();
		this.args = args;
		this.workingDirectory = workingDirectory;
		this.stdout = stdout;
		this.stderr = stderr;
		this.classworld = classworld;
		this.cliInstance = new MavenCli(classworld);
		returnValue = -1;
	}

	@Override
	public void run() {
		System.err.println("DEBUG - Start of run");
		this.returnValue = this.cliInstance.doMain(getArgs(), getWorkingDirectory(),
				getStdout(), getStderr());
		System.err.println("DEBUG - End of run");
	}

	public String[] getArgs() {
		return args;
	}

	public String getWorkingDirectory() {
		return workingDirectory;
	}

	public PrintStream getStdout() {
		return stdout;
	}

	public PrintStream getStderr() {
		return stderr;
	}

	public ClassWorld getClassworld() {
		return classworld;
	}

	public MavenCli getCliInstance() {
		return cliInstance;
	}

	/**
	 * @return the return value from the previous
	 *         {@link MavenCli#doMain(String[], String, PrintStream, PrintStream)}
	 *         call, or -1 if no return value is available
	 */
	public int getReturnValue() {
		return returnValue;
	}

}
