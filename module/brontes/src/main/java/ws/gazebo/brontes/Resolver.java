package ws.gazebo.brontes;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.apache.maven.settings.building.SettingsBuildingException;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.wagon.WagonTransporterFactory;

public class Resolver {

	private ResolverConfig config;
	private RepositorySystem repositorySystem;
	private RepositorySystemSession session = null;

	public Resolver(ResolverConfig config) {
		super();
		this.config = config;
	}

	public Resolver(ResolverConfig config, RepositorySystem rs) {
		this(config);
		repositorySystem = rs;
	}

	public ResolverConfig getConfig() {
		return config;
	}

	public void setConfig(ResolverConfig config) {
		this.config = config;
	}

	public RepositorySystem getRepositorySystem() {
		return repositorySystem;
	}

	public void setRepositorySystem(RepositorySystem repositorySystem) {
		this.repositorySystem = repositorySystem;
	}

	public RepositorySystemSession getSession() {
		return session;
	}

	public void setSession(RepositorySystemSession session) {
		this.session = session;
	}

	public ArtifactResult resolve(ArtifactRequest request)
			throws ArtifactResolutionException {
		return getRepositorySystem().resolveArtifact(ensureSession(), request);
	}

	public ArtifactResult resolve(Artifact artifact)
			throws ArtifactResolutionException {
		ArtifactRequest req = new ArtifactRequest();
		req.setArtifact(artifact);
		return resolve(req);
	}

	public ArtifactResult resolve(String artifact)
			throws ArtifactResolutionException {
		Artifact a = new DefaultArtifact(artifact);
		return resolve(a);
	}

	public RepositorySystemSession ensureSession() {
		RepositorySystemSession s = getSession();
		if (s == null) {
			s = newDefaultSession(getRepositorySystem());
			setSession(s);
		}
		return s;
	}

	public static void main(String[] args) throws SettingsBuildingException {
		// "Built in test method"

		// cf.
		// http://blog.sonatype.com/people/2011/01/how-to-use-aether-in-maven-plugins/
		// NOTE: This does not download archives, only resolves those that exist
		// locally in the appropriate configuration locations...

		ResolverConfig cfg = new ResolverConfig();
		cfg.ensureSettingsDefault();

		RepositorySystem rs = makeDefaultRepositorySystem();
		Resolver resolver = new Resolver(cfg, rs);
		resolver.ensureSession();

		try {
			// FIXME: Does not download, only resolves existing resources
			// (need to copy settings over)
			ArtifactResult result = resolver
					.resolve("org.apache.maven:maven-model:3.1.0");
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

	public RepositorySystemSession newDefaultSession(RepositorySystem sys) {
		// cf
		// http://wiki.eclipse.org/Aether/Creating_a_Repository_System_Session
		// "creating such a session that mimics Maven's setup"

		// FIXME: How can the 'session' be handled so as to couple it with
		// User's Maven setings?
		DefaultRepositorySystemSession session = MavenRepositorySystemUtils
				.newSession();

		// session.setFoo(getConfig().getSettings().getFoo()))
		// ^ FIXME call that many times, to transfer settings from the Settings
		// object onto the Session object, manually

		LocalRepository localRepo = new LocalRepository(getConfig()
				.getLocalRepository());
		LocalRepositoryManager lMgr = sys.newLocalRepositoryManager(session,
				localRepo);

		session.setLocalRepositoryManager(lMgr);
		session.setReadOnly(); // FIXME : Note that call, in the documentation
		return session;
	}

}
