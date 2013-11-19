package ws.gazebo.brontes;

import java.io.File;

import org.apache.maven.settings.Settings;
import org.apache.maven.settings.building.DefaultSettingsBuilderFactory;
import org.apache.maven.settings.building.DefaultSettingsBuildingRequest;
import org.apache.maven.settings.building.SettingsBuilder;
import org.apache.maven.settings.building.SettingsBuildingException;
import org.apache.maven.settings.crypto.DefaultSettingsDecrypter;
import org.apache.maven.settings.crypto.DefaultSettingsDecryptionRequest;
import org.apache.maven.settings.crypto.SettingsDecrypter;
import org.apache.maven.settings.crypto.SettingsDecryptionRequest;
import org.apache.maven.settings.crypto.SettingsDecryptionResult;

public class ResolverConfig {

	public static File USER_M2_DIRECTORY = new File(
			System.getProperty("user.home"), ".m2");
	public static File USER_M2_DEFAULT_LOCAL_REPOSITORY = new File(USER_M2_DIRECTORY, "repository");
	public static File USER_SETTINGS_FILE = new File(USER_M2_DIRECTORY,
			"settings.xml");

	SettingsBuilder settingsBuilder;
	SettingsDecrypter settingsDecrypter;
	Settings settings = null;

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
	 * Initialize an instance with default {@link SettingsBuilder} and
	 * {@link SettingsDecrypter}.
	 * 
	 * @see <ul>
	 *      <li>{@link DefaultSettingsBuilderFactory#newInstance()} which
	 *      provides the value for the {@link SettingsBuilder}</li>
	 *      <li>{@link DefaultSettingsDecrypter}, a fresh instance of which will
	 *      provide the value of the {@link SettingsDecrypter}</li>
	 *      <li>{@link #ensureSettingsDefault()} which <em>may</em> be called at
	 *      least once on the initialized instance. That method <em>should</em>
	 *      be called at least once, if no alternative {@link Settings} source
	 *      would be provided by the application</li>
	 *      </ul>
	 */
	public ResolverConfig() {
		super();
		DefaultSettingsBuilderFactory sbf = new DefaultSettingsBuilderFactory();
		// ^ apparently, only exists to create a default settings builder
		settingsBuilder = sbf.newInstance();
		settingsDecrypter = new DefaultSettingsDecrypter();
		// NB: dependency on MavenRepositorySystemUtils
	}

	/**
	 * Initialize an instance with the specified {@link SettingsBuilder} and
	 * {@link SettingsDecrypter} instances.
	 * 
	 * @param builder
	 * @param decrypter
	 */
	public ResolverConfig(SettingsBuilder builder, SettingsDecrypter decrypter) {
		super();
		settingsBuilder = builder;
		settingsDecrypter = decrypter;
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

	/**
	 * Ensure that settings are loaded and returned. If {@link #getSettings()}
	 * returns null, calls {@link #loadSettingsDefault()} then sets the
	 * resulting value via {@link #setSettings(Settings)} before returning the
	 * value
	 * 
	 * @return the {@link Settings} object
	 * @throws SettingsBuildingException
	 */
	public Settings ensureSettingsDefault() throws SettingsBuildingException {
		Settings s = getSettings();
		if (s == null) {
			s = loadSettingsDefault();
			setSettings(s);
		}
		return s;
	}

	/**
	 * Property accessor method
	 * 
	 * @return the {@link Settings} object
	 * @see {@link #ensureSettingsDefault()}
	 */
	public Settings getSettings() {
		return settings;
	}

	/**
	 * Property accessor method
	 * 
	 * @param settings
	 *            the {@link Settings} object
	 * @see {@link #ensureSettingsDefault()} which presents an alternative
	 *      method for ensuring that the instance's settings are initialized
	 */
	public void setSettings(Settings settings) {
		this.settings = settings;
	}


	/**
	 * <p>
	 * Load global and user settings, including proxy information, as per Maven
	 * settings.
	 * </p>
	 * <p>
	 * This method returns the {@link Settings} object, not modifying any fields
	 * of the containing {@link ResolverConfig} instance. This method needs a
	 * non-null value from {@link #getSettingsBuilder()}
	 * </p>
	 * 
	 * @return the {@link Settings} object
	 * @throws SettingsBuildingException
	 *             if the settings cannot be built
	 * @see <ul>
	 *      <li>{@link #ensureSettingsDefault()} which calls this method</li>
	 *      <li><a href="http://maven.apache.org/settings.html">Maven - Settings
	 *      Reference</a></li>
	 *      </ul>
	 * 
	 */
	public Settings loadSettingsDefault() throws SettingsBuildingException {
		DefaultSettingsBuildingRequest r = new DefaultSettingsBuildingRequest();
		r.setUserSettingsFile(USER_SETTINGS_FILE);
		File gs = getGlobalSettingsFile();
		if (gs != null) {
			r.setGlobalSettingsFile(gs);
		}
		Settings s = getSettingsBuilder().build(r).getEffectiveSettings(); // throws

		// Note: Whatever may seem to be suggested in the Javadoc of
		// the method Settings#getLocalRepository(), nothing is actually
		// initializing that property within the Settings class. So, this method
		// initializes a suitable value to the property, initializing it to such
		// a value as suggested in the javadoc of that other method in that
		// other class
		//
		// with regards to: maven-settings, artifact version 3.1.1
		s.setLocalRepository(USER_M2_DEFAULT_LOCAL_REPOSITORY.toString());
		
		SettingsDecryptionRequest sdr = new DefaultSettingsDecryptionRequest(s);
		SettingsDecryptionResult sdrResult = getSettingsDecrypter()
				.decrypt(sdr);
		s.setServers(sdrResult.getServers());
		s.setProxies(sdrResult.getProxies());
		return s;
	}

	// **** Delegate methods follow ****
	// See also: 
	// * Resolver.newDefaultSession()
	// * Resolver.transferSettings(...)
	
	/**
	 * Delegate method onto {@link Settings#getLocalRepository()}
	 * 
	 * @return a {@link File} object representing the local artifact repository
	 * @see {@link #ensureSettingsDefault()} such that may be called as to
	 *      ensure that a {@link Settings} property will be available in the
	 *      instance, prior to this method's execution
	 */
	public File getLocalRepository() {
		Settings s = getSettings();
		String repo = s.getLocalRepository();
		return new File(repo);
	}
}
