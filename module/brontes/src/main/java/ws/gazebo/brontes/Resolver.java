package ws.gazebo.brontes;

import java.io.File;
import java.util.ServiceLoader;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.apache.maven.settings.Settings;
import org.apache.maven.settings.building.DefaultSettingsBuilder;
import org.apache.maven.settings.building.DefaultSettingsBuilderFactory;
import org.apache.maven.settings.building.DefaultSettingsBuildingRequest;
import org.apache.maven.settings.building.SettingsBuilder;
import org.apache.maven.settings.building.SettingsBuildingException;
import org.apache.maven.settings.building.SettingsBuildingRequest;
import org.apache.maven.settings.crypto.DefaultSettingsDecrypter;
import org.apache.maven.settings.crypto.DefaultSettingsDecryptionRequest;
import org.apache.maven.settings.crypto.SettingsDecrypter;
import org.apache.maven.settings.crypto.SettingsDecryptionRequest;
import org.apache.maven.settings.crypto.SettingsDecryptionResult;
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
import org.eclipse.aether.spi.locator.ServiceLocator;
import org.eclipse.aether.transport.wagon.WagonTransporterFactory;

public class Resolver {

	public static File USER_M2_DIRECTORY = new File(
			System.getProperty("user.home"), ".m2");

	SettingsBuilder settingsBuilder;
	SettingsDecrypter settingsDecrypter;
	ServiceLocator serviceLocator;
	Settings settings = null;

	public Resolver() {
		super();
		DefaultSettingsBuilderFactory sbf = new DefaultSettingsBuilderFactory();
		// ^ apparently, only exists to create a default settings builder
		settingsBuilder = sbf.newInstance();
		settingsDecrypter = new DefaultSettingsDecrypter();
		// FIXME: Decouple this dependency on MavenRepositorySystemUtils ? 
		serviceLocator = MavenRepositorySystemUtils.newServiceLocator();
	}

	public Resolver(SettingsBuilder builder, SettingsDecrypter decrypter,
			ServiceLocator locator) {
		super();
		settingsBuilder = builder;
		settingsDecrypter = decrypter;
		serviceLocator = locator;
	}

	public SettingsBuilder getSettingsBuilder() {
		return settingsBuilder;
	}

	public void setSettingsBuilder(SettingsBuilder settingsBuilder) {
		this.settingsBuilder = settingsBuilder;
	}

	public SettingsDecrypter getSettingsDecrypter() {
		return settingsDecrypter;
	}

	public void setSettingsDecrypter(SettingsDecrypter settingsDecrypter) {
		this.settingsDecrypter = settingsDecrypter;
	}
	

	public ServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

	public static void main(String[] args) {
		// cf.
		// http://blog.sonatype.com/people/2011/01/how-to-use-aether-in-maven-plugins/
		// NOTE: This does not download archives, only resolves those that exist
		// locally in the appropriate configuration locations...

		// FIXME: Static/instance method discrepancy
		Resolver f = new Resolver();
		RepositorySystem rs = configureDefaultRepositorySystem((DefaultServiceLocator) f.getServiceLocator());
		RepositorySystemSession ses = f.newSession(rs);

		try {
			ArtifactRequest r = new ArtifactRequest();
			r.setArtifact(new DefaultArtifact("org.apache.maven:maven-model:3.1.0"));
			ArtifactResult result = rs.resolveArtifact(ses, r); // query part
			// NB: The 'result' does not have a pathname (?!)			
			System.out.println("Resolved: " + result.getArtifact() + " in " + result.getRepository());
		} catch (ArtifactResolutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static File getUserSettingsFile() {
		// cf. http://maven.apache.org/settings.html
		return new File(USER_M2_DIRECTORY, "settings.xml");
	}

	/**
	 * @return file representing "$M2_HOME/conf/settings.xml" or null if M2_HOME
	 *         is not defined in the environment
	 */
	public static File getGlobalSettingsFile() {
		// cf. http://maven.apache.org/settings.html
		String m2home = System.getenv("M2_HOME");
		if (m2home != null) {
			return new File(new File(m2home, "conf"), "settings.xml");
		} else {
			return null;
		}
	}

	/**
	 * Load global and user settings, including proxy information
	 * 
	 * @return the settings object
	 * @throws SettingsBuildingException
	 *             if the settings could not be built
	 */
	public Settings ensureSettings() throws SettingsBuildingException {
		if (settings == null) {
			DefaultSettingsBuildingRequest r = new DefaultSettingsBuildingRequest();
			r.setUserSettingsFile(getUserSettingsFile());
			File gs = getGlobalSettingsFile();
			if (gs != null) {
				r.setGlobalSettingsFile(gs);
			}
			Settings s = getSettingsBuilder().build(r).getEffectiveSettings(); // throws
																				// _
			SettingsDecryptionRequest sdr = new DefaultSettingsDecryptionRequest(
					s);
			SettingsDecryptionResult sdrResult = getSettingsDecrypter()
					.decrypt(sdr);
			s.setServers(sdrResult.getServers());
			s.setProxies(sdrResult.getProxies());
			return s;
		} else {
			return settings;
		}
	}

	public File getLocalRepository() {
		// cf.
		// https://eclipse.googlesource.com/aether/aether-ant/+/ac01d8950c2202425f73c6e6c926bb2736e05b67/src/test/java/org/eclipse/aether/ant/AntBuildsTest.java
		String localRepo;
		Settings s = null;
		try {
			s = ensureSettings();
		} catch (SettingsBuildingException e) {
			e.printStackTrace();
		}
		localRepo = System.getProperty("maven.repo.local");
		if (localRepo != null) {
			return new File(localRepo);
		} else {
			localRepo = s.getLocalRepository();
			;
			if (localRepo != null) {
				return new File(localRepo);
			} else {
				return new File(USER_M2_DIRECTORY, "repository");
			}
		}

	}

	public static RepositorySystem configureDefaultRepositorySystem(DefaultServiceLocator loc) {
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

		LocalRepository localRepo = new LocalRepository(getLocalRepository());

		session.setLocalRepositoryManager(system.newLocalRepositoryManager(
				session, localRepo));
		return session;
	}
}
