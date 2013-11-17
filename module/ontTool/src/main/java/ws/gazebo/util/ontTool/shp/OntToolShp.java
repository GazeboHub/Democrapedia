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
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class OntToolShp {

	public static FileDataStoreFactorySpi SHP_FACTORY = FileDataStoreFinder
			.getDataStoreFactory("shp");

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

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

	public static FeatureIterator<SimpleFeature> getFeatureIterator(String typeName,
			DataStore ds) throws IOException {
		FeatureSource<SimpleFeatureType, SimpleFeature> fs = ds.getFeatureSource(typeName);
		FeatureCollection<SimpleFeatureType, SimpleFeature> col = fs.getFeatures();
		FeatureIterator<SimpleFeature> it = col.features();
		return it;
	}
}
