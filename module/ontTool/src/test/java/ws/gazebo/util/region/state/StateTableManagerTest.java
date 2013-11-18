package ws.gazebo.util.region.state;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ws.gazebo.util.ontTool.jena.VFSFileManager;

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

		mgr.setFileManager(new VFSFileManager()); // set custom file manager
		// Note that the VFSFileManager does not provide support for mapping
		// pathnames onto the classpath, however.
		
		Model map = ModelFactory.createOntologyModel().read(
				"etc/location-mapping.n3", "N3");
		// map is null - the filename translation is not working across the
		// classpath - this is useless
		mgr.configure(map);

		OntModel m = ModelFactory.createOntologyModel();
		// OntDocumentManager dm = m.getDocumentManager();

		String GNIS_ONT_URI = "http://www.gazebo.ws/ont/featuresGNIS.rdf";
		System.out.println("Attempting to read "
				+ mgr.doAltURLMapping(GNIS_ONT_URI));
		m.read(GNIS_ONT_URI);
		System.out.println("Foo: " + m.getOntClass(GNIS_ONT_URI + "#Feature"));

	}

}
