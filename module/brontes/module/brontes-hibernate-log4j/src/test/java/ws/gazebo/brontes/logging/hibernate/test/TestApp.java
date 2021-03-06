package ws.gazebo.brontes.logging.hibernate.test;

public class TestApp {

	// Note: The class org.hsqldb.util.MainInvoker doesn't seem to launch the
	// commands in parallel.
	//
	// This TestApp class uses a separate thread for the HSQLDB database

	public static void main(String[] args) {
		final String database = args[0];

		Thread db = new Thread() {
			// Note that the sever uses Log4J
			// The file log4j.properties defines a console appender,
			// for developer review of console output from the server
			public void run() {
				org.hsqldb.server.Server.main(new String[] { "--database.0",
						database });
			}
		};
		db.run();
	}

}
