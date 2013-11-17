package ws.gazebo.util.region.state;

import java.net.URL;

import com.hp.hpl.jena.util.FileManager;

public class ClasspathFileManager extends FileManager {

	@Override
	public String mapURI(String filenameOrURI) {
		String r1 = super.mapURI(filenameOrURI);
		System.err.println("URI Mapping input: " + filenameOrURI);
		// broad brush:
		if (r1 != null) {
			System.err.println("...Superclass mapping result: " + r1);
			URL u = this.getClass().getClassLoader().getResource(r1);
			if(u != null) {
				System.err.println("......Classpath mapping result: " + u);
				return u.toString();				
			} else {
				return r1;
			}

		} else {
			return r1;
		}
	}

}
