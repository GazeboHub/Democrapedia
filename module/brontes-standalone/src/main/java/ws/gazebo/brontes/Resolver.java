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
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;

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
		this.repositorySystem = rs;
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
		// req.setTrace(trace) // FIXME: trace ?
		// ^ seen in org.apache.maven.repository.internal.DefaultModelResolver
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
		// http://wiki.eclipse.org/Aether

		ResolverConfig cfg = new ResolverConfig();
		cfg.ensureSettingsDefault();

		RepositorySystem rs = makeDefaultRepositorySystem();
		System.err.println("DEBUG : default rs: " + rs); // FIXME: why sometimes
															// null?
		Resolver resolver = new Resolver(cfg, rs);
		resolver.ensureSession();

		try {

			ArtifactResult result = resolver
					.resolve("org.apache.maven:maven-model:3.0.1");
			System.out.println("Resolved: " + result.getArtifact().getFile());
			System.out.println("Deleting file");
			result.getArtifact().getFile().delete();
			// ^ And then it crashes on *some* subsequent runs (???)
			// as when resolving maven-model 3.1.1 and then deleting it
			// (Maven bug?)
			//
			// FIXME: It's still not downloading artifacts over the network
			//
			// So, new plan:
			// 1) Rename this item's Maven module -> brontes-standalone; table
			// the design
			// 2) Define new 'brontes' module as a POM
			// 3) Move the brontes-standalone into the new brontes:*:pom
			// 4) Define a new plugin, maven-ontology-plugin, in which

			// A. The plugin may provide a Jena FileManager implementation,
			// MvnFileManager

			// B. The plugin shall rely on the configuration of the
			// plugin, in order to compute each target ontology such that must
			// be loaded within the JVM, therefore available to other ontology
			// models
			// cf.
			// <config>
			// --- <ont artfact="org.example:pizza-ont:1.0"
			// --------- pathname="META-INF/pizza.rdf" type="OWL_MEM"/>
			// </config>
			// ^ such that then adds a dependency to the containing project,
			// namely in the file org.example:pizza-ont:1.0
		} catch (ArtifactResolutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Create and initialize a {@link DefaultServiceLocator} with a
	 * {@link BasicRepositoryConnectorFactory}. a {@link FileTransporterFactory}
	 * , and a {@link HttpTransporterFactory}
	 * 
	 * @return the {@link RepositorySystem} assigned to the
	 *         {@link DefaultServiceLocator}
	 */
	public static RepositorySystem makeDefaultRepositorySystem() {
		// cf. http://wiki.eclipse.org/Aether/Setting_Aether_Up

		// FIXME: Should the service locator be stored in an instance field?
		DefaultServiceLocator loc = MavenRepositorySystemUtils
				.newServiceLocator();

		// Note: This does not rely on popular dependency injection models, such
		// that would serve to obfuscate Java code with ghostly object
		// insertions
		loc.addService(RepositoryConnectorFactory.class,
				BasicRepositoryConnectorFactory.class);

		// FIXME : Can this add both as extensions of TransporterFactory ?
		// Or does the second override the first?
		loc.addService(TransporterFactory.class, FileTransporterFactory.class);
		loc.addService(TransporterFactory.class, HttpTransporterFactory.class);
		// ^ Not working.

		// FIXME: Why is this ever returning null?
		// Noticing the DefaultServiceLocator() constructor, it should be that
		// an instance of the appropriate class and its implemenation is already
		// being "injected" (rather, set, as in a hash table) into the locator
		RepositorySystem rs = loc.getService(RepositorySystem.class);
		if (rs == null) {
			// FIXME: UGLY HACK. PLEASE INSERT CORRECT DEPENDECIES TO CONTINUE.

			// return new DefaultRepositorySystem(); // UNINITIALIZED. INVALID.

			// MAN IT SURE WOULD BE NICE TO HAVE A CONVENIENT 'NEW' METHOD
			// AROUND, SOMEWHERE, IN THIS TRACEABLE THOUGH DISTINCTLY
			// NON-FADDISH CODE

			// FIXME "Terminal breakpoint", alternately
			System.err.println("Null repository system. Aborting");
			System.exit(127);
		}
		return rs;
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
