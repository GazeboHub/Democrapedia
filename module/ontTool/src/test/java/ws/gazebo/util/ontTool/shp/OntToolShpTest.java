package ws.gazebo.util.ontTool.shp;

import static org.junit.Assert.*;

import java.io.File;

import org.geotools.data.DataStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OntToolShpTest {

	public static final String SHP_TEST_PATH = "src/main/test/resources/statesp020_nt00032/statesp020.shp";

	private File shpTestFile;
	
	@Before
	public void setUp() {
		shpTestFile = new File(SHP_TEST_PATH);
	}
	
	@After
    public void tearDown() {
	
	}

	@Test
	public final void testOpenShpDataStore() {
		DataStore ds = OntToolShp.openShpDataStore(shpTestFile);
	}
	
	
}
