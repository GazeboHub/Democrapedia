package ws.gazebo.brontes;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.apache.maven.settings.Settings;
import org.apache.maven.settings.building.SettingsBuildingException;
import org.apache.maven.wagon.WagonConstants;
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
import org.eclipse.aether.transport.wagon.WagonConfigurator;
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

	/**
	 * Ensure that this instance is configured with a
	 * {@link RepositorySystemSession}. When {@link #getSession()} returns
	 * {@code null}, this method calls {@link #newDefaultSession()} and sets the
	 * resulting session object as the {@link RepositorySystemSession} for the
	 * instance, then returns the session object. Else, this method returns the
	 * non-null result of the initial call to {@link #getSession()}
	 * 
	 * @return the {@link RepositorySystemSession} object for this
	 *         {@link Resolver}
	 */
	public RepositorySystemSession ensureSession() {
		RepositorySystemSession s = getSession();
		if (s == null) {
			s = newDefaultSession();
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
			// FIXME: May need to configure the Wagon framework, for artifact
			// transport
			//
			// See also
			// * WagonTransporterFactory#setWagonConfigurator(...)
			// * WagonTransporterFactory#setWagonProvider(...)
			//
			// WagonConfigurator instance PlexusWagonConfigurator (how to
			// configure?)
			// WagonProvider instance PlexusWagonProvider (how to configure?)
			//
			// See also: Sisu http://www.eclipse.org/sisu/
			// Noting,
			// "The Plexus container is no longer maintained and users of it should start to migrate to alternatives like JSR-330."
			// as cf. http://wiki.eclipse.org/Aether/Setting_Aether_Up
			//
			// So, alternately, consider adding Sisu as a dep
			// (sisu-maven-plugin?)
			//
			// However, note that the comment "Documentation on the way" about
			// Sisu does not qualify as documentation about Sisu.
			//
			// Furthermore, to whence does the dependency-spaghetti go, once it
			// depeds on Sisu and therefore also Giuce?

			// SEE ALSO
			// old aether guice example - uses wagon
			// http://git.eclipse.org/c/aether/aether-demo.git/tree/aether-demo-snippets/src/main/java/org/eclipse/aether/examples/guice/DemoAetherModule.java?id=0a9b2a53bffbbaef70992dcdb243631f1bc8e48b
			// new aether guice example - no wagon (?)
			// http://git.eclipse.org/c/aether/aether-demo.git/tree/aether-demo-snippets/src/main/java/org/eclipse/aether/examples/guice/DemoAetherModule.java

			// Alternately, just use Aeither's own simple (but inextensible) HTTP and File transports???
			
			ArtifactResult result = resolver
					.resolve("org.apache.maven:maven-model:3.1.0");
			System.out.println("Resolved: " + result.getArtifact().getFile());
		} catch (ArtifactResolutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static <C extends RepositoryConnectorFactory, T extends TransporterFactory> DefaultServiceLocator makeDefaultServiceLocator(
			Class<C> connectorFactoryClass, Class<T> transporterFactoryClass) {
		// FIXME: Should the service locator be stored in an instance field?
		DefaultServiceLocator loc = MavenRepositorySystemUtils
				.newServiceLocator();
		loc.addService(RepositoryConnectorFactory.class, connectorFactoryClass);
		loc.addService(TransporterFactory.class, transporterFactoryClass);
		return loc;
	}

	/**
	 * Create and initialize a {@link DefaultServiceLocator} with a
	 * {@link BasicRepositoryConnectorFactory} and a
	 * {@link WagonTransporterFactory}
	 * 
	 * @return the {@link RepositorySystem} assigned to the
	 *         {@link DefaultServiceLocator}
	 */
	public static RepositorySystem makeDefaultRepositorySystem() {
		// cf. http://wiki.eclipse.org/Aether/Setting_Aether_Up

		DefaultServiceLocator loc = makeDefaultServiceLocator(
				BasicRepositoryConnectorFactory.class,
				WagonTransporterFactory.class);
		// ^ FIXME: Is that enough to ensure availability of HTTP transport?
		// FIXME: see also
		// * WagonTransporterFactory#setWagonConfigurator(...)
		// * WagonTransporterFactory#setWagonProvider(...)
		return loc.getService(RepositorySystem.class);
	}

	public RepositorySystemSession newDefaultSession() {
		// Point of reference:
		// http://wiki.eclipse.org/Aether/Creating_a_Repository_System_Session
		// namely, "creating such a session that mimics Maven's setup"
		//
		// That pattern has been refactored here for a more modular design
		DefaultRepositorySystemSession session = MavenRepositorySystemUtils
				.newSession();
		configureSession(session);
		// session.setReadOnly(); // Note that that will not be called here
		return session;
	}

	/**
	 * Set the {@link LocalRepositoryManager} for the {@code session}, based on
	 * the result of {@link ResolverConfig#getLocalRepository()} for the
	 * {@link ResolverConfig} of this instance
	 * 
	 * @param session
	 *            the session instance to configure
	 */
	public void configureLocalRepository(DefaultRepositorySystemSession session) {
		// transfer local repository
		LocalRepository localRepo = new LocalRepository(getConfig()
				.getLocalRepository());
		RepositorySystem sys = getRepositorySystem();
		LocalRepositoryManager lmgr = sys.newLocalRepositoryManager(session,
				localRepo);
		session.setLocalRepositoryManager(lmgr);
	}

	/**
	 * Ensure that the {@code session} is configured for this {@link Resolver}
	 * and its {@link ResolverConfig}
	 * 
	 * @param session
	 *            the {@link DefaultRepositorySystemSession} instance to
	 *            configure
	 */
	public void configureSession(DefaultRepositorySystemSession session) {
		// Note that it may be unclear as to whether or not any more settings
		// should be transferred from the ResolverConfig's settings object into
		// the session. At least the local repository info must be transferred,
		// or else the resolver will be unable to resolve artifacts
		configureLocalRepository(session);
	}

}
