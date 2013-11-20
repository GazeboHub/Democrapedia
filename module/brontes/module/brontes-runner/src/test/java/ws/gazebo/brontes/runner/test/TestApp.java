package ws.gazebo.brontes.runner.test;

import java.io.File;

import org.jboss.shrinkwrap.resolver.api.ConfiguredResolverSystemFactory;
import org.jboss.shrinkwrap.resolver.api.maven.ConfigurableMavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;

public class TestApp {

	public static void main(String[] args) {
		System.out.println("Hello World");
		System.err.println("Args were: ");
		for (String a : args) {
			System.out.println('\t' + a);
		}


		
		// FIXME: provide POM as an arg
		MavenResolverSystem rs = Maven.resolver();
		// rs.loadPomFromFile("application-test.xml");
		
		// This fails consistenly when used in the same classpath with maven-embedder.
		// So: Consider OSGI, alternately, as a runtime container, rather than Jena,
		// but continue using Shrinkwrap for dependency resolution 
		File it = rs.resolve ("junit:junit:4.10")
				.withoutTransitivity().asSingle(File.class);

		// for (File f : libs) {
			System.out.println("Resolved: " + it);
		// }

	}

}
