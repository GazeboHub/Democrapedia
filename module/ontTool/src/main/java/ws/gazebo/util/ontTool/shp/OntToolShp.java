package ws.gazebo.util.ontTool.shp;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStoreFactorySpi;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class OntToolShp {

	public static FileDataStoreFactorySpi SHP_FACTORY = FileDataStoreFinder
			.getDataStoreFactory("shp");

	public static DataStore openShpDataStore(String pathname) {
		File f = new File(pathname);
		return openShpDataStore(f);
	}

	/**
	 * Initialize a Geotools {@link DataStore} onto a Shapefile in the local
	 * filesystem
	 * 
	 * @param f
	 *            file representing the local Shapefile
	 * @return a {@link DataStore} instance for the Shapefile
	 * @see {@link DataStore#dispose()}
	 */
	public static DataStore openShpDataStore(File f) {
		Map<String, Serializable> m = null;
		try {
			m = Collections.singletonMap("url", (Serializable) f.toURI()
					.toURL());
		} catch (MalformedURLException e) {
			// (should not be reached)
			e.printStackTrace();
			return null;
		}
		DataStore ds = null;
		try {
			ds = SHP_FACTORY.createDataStore(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ds;
	}

	public static FeatureIterator<SimpleFeature> getShpFeatureIterator(DataStore ds) throws IOException {
		String typeName = ds.getTypeNames()[0];
		FeatureSource<SimpleFeatureType, SimpleFeature> fs = ds.getFeatureSource(typeName);
		FeatureCollection<SimpleFeatureType, SimpleFeature> col = fs.getFeatures();
		FeatureIterator<SimpleFeature> it = col.features();
		return it;
	}

	public static Property requireProperty(String name, SimpleFeature feature) {
		Property p = feature.getProperty(name);
		if (p == null) {
			System.err.println("Fatal error. Property " + name
					+ " is null in feature " + feature);
			System.exit(127);
		}
		// Eclipse wasn't observing the return from 'else'
		// So, leaving this return without the additional control flow
		// bracketing
		return p;
	
	}

	public static String requiredStringPropertyValue(String propertyName,
			SimpleFeature feature) {
		Property p = requireProperty(propertyName, feature);
		return (String) p.getValue();
	}

	public static Integer requiredIntPropertyValue(String propertyName,
			SimpleFeature feature) {
		Property p = requireProperty(propertyName, feature);
		return (Integer) p.getValue();
	}

	public static Long requiredLongPropertyValue(String propertyName,
			SimpleFeature feature) {
		Property p = requireProperty(propertyName, feature);
		return (Long) p.getValue();
	}
}
