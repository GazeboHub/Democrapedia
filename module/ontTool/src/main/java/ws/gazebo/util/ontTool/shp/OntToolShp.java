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
import org.opengis.feature.Feature;

public class OntToolShp {

	public static FileDataStoreFactorySpi SHP_FACTORY = FileDataStoreFinder
			.getDataStoreFactory("shp");

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static DataStore openShpDataStore(File f) {
		Map<String, Serializable> m = null;
		try {
			m = Collections.singletonMap("url", (Serializable) f.toURI()
					.toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block (should not be reached)
			e.printStackTrace();
		}
		DataStore ds = null;
		try {
			ds = SHP_FACTORY.createDataStore(m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (ds != null) {
				ds.dispose();
			}
		}
		return ds;
	}

	public static FeatureIterator<Feature> getFeatures(String typeName,
			DataStore ds) throws IOException {
		FeatureSource<?, ?> fs = ds.getFeatureSource(typeName);
		FeatureCollection col = fs.getFeatures();
		FeatureIterator<Feature> it = col.features();
		return it;
	}
}
