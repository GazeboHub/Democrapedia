package ws.gazebo.brontes;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.apache.maven.settings.Settings;
import org.apache.maven.settings.building.SettingsBuildingException;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.wagon.WagonTransporterFactory;

public class Resolver {

	public ResolverConfig config;

	public Resolver(ResolverConfig config) {
		super();
		this.config = config;
	}

	public ResolverConfig getConfig() {
		return config;
	}

	public void setConfig(ResolverConfig config) {
		this.config = config;
	}

	public static void main(String[] args) {
		// cf.
		// http://blog.sonatype.com/people/2011/01/how-to-use-aether-in-maven-plugins/
		// NOTE: This does not download archives, only resolves those that exist
		// locally in the appropriate configuration locations...

		// FIXME: Static/instance method discrepancy
		ResolverConfig cfg = new ResolverConfig();
		try {
			cfg.ensureSettingsDefault();
		} catch (SettingsBuildingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Resolver rsv = new Resolver(cfg);
		RepositorySystem rs = makeDefaultRepositorySystem();
		RepositorySystemSession ses = rsv.newSession(rs);
		// ^ fixme: make session an instance field, or decouple the repository
		// system framework from the session configuration framework as
		// represented in this file

		try {
			ArtifactRequest r = new ArtifactRequest();
			r.setArtifact(new DefaultArtifact(
					"org.apache.maven:maven-model:3.1.0"));
			ArtifactResult result = rs.resolveArtifact(ses, r); // query part
			System.out.println("Resolved: " + result.getArtifact().getFile());
		} catch (ArtifactResolutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static RepositorySystem makeDefaultRepositorySystem() {
		return makeDefaultRepositorySystem(MavenRepositorySystemUtils
				.newServiceLocator());
	}

	public static RepositorySystem makeDefaultRepositorySystem(
			DefaultServiceLocator loc) {
		// cf. http://wiki.eclipse.org/Aether/Setting_Aether_Up
		loc.addService(RepositoryConnectorFactory.class,
				BasicRepositoryConnectorFactory.class);
		loc.addService(TransporterFactory.class, WagonTransporterFactory.class);
		return loc.getService(RepositorySystem.class);
	}

	public RepositorySystemSession newSession(RepositorySystem system) {
		// cf
		// http://wiki.eclipse.org/Aether/Creating_a_Repository_System_Session
		// "creating such a session that mimics Maven's setup" (???)

		DefaultRepositorySystemSession session = MavenRepositorySystemUtils
				.newSession();

		LocalRepository localRepo = new LocalRepository(getConfig()
				.getLocalRepository());

		session.setLocalRepositoryManager(system.newLocalRepositoryManager(
				session, localRepo));
		return session;
	}

}
