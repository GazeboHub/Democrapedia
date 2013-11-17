package ws.gazebo.util.region.state;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class StateTableManagerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void test() {
		OntDocumentManager mgr = OntDocumentManager.getInstance();
		// trying to load the etc/location-mapping.n3, such that should be
		// available in the classpath of the 'ontologies' module
		
		URL mappingURL = this.getClass().getClassLoader().getResource("etc/location-mapping.n3");
		System.out.println("Configuring model factory with " + mappingURL);
		
		mgr.setFileManager(new ClasspathFileManager()); // DO SECOND, after configure(..)
		Model map = ModelFactory.createOntologyModel().read(mappingURL.toString(), "N3");
		// map is null - that's troublesome.
		mgr.configure(map);
		
		OntModel m = ModelFactory.createOntologyModel();
		// OntDocumentManager dm = m.getDocumentManager();
		
		String GNIS_ONT_URI = "http://www.gazebo.ws/ont/featuresGNIS.rdf";
		System.out.println("Attempting to read " + mgr.doAltURLMapping(GNIS_ONT_URI));
		m.read(GNIS_ONT_URI);
		System.out.println("Foo: " + m.getOntClass(GNIS_ONT_URI + "#Feature"));

	}

}
