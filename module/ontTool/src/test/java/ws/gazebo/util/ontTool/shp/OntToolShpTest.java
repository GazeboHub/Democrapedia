package ws.gazebo.util.ontTool.shp;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.geotools.data.DataStore;
import org.geotools.data.FeatureSource;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opengis.feature.Feature;
import org.opengis.feature.Property;
import org.opengis.feature.type.FeatureType;
import org.opengis.filter.identity.FeatureId;

public class OntToolShpTest {

	public static final String SHP_TEST_PATH = "src/test/resources/statesp020_nt00032/statesp020.shp";

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
		try {
			String theType = ds.getTypeNames()[0];
			FeatureIterator<Feature> it = OntToolShp.getFeatures(theType, ds);
			try {
				while (it.hasNext()) {
					Feature f = it.next();
					// NB: getName() returns the same result, always
					System.out.println("Feature name: " + f.getName());
					// getIdentifier returns a unique result
					System.out.println("Feature ID: " + f.getIdentifier());
					FeatureId fid = f.getIdentifier();
					// NB: getType() returns a string containing something
					// resembling WKT
					System.out.println("Feature type: " + f.getType());
					Iterator<Property> pit = f.getProperties().iterator();
					while (pit.hasNext()) {
						Property p = pit.next();
						// FIXME: always skip feature named the_geom
						if (p.getName().toString() != "the_geom") {
							System.out.println("Feature " + fid
									+ " Property " + p.getName() + " Value: "
									+ p.getValue());
						}
					}

				}
			} finally {
				it.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("error");
		}

	}

}
