package ws.gazebo.util.ontTool.shp;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.FileDataStoreFactorySpi;
import org.geotools.data.FileDataStoreFinder;

public class OntToolShp {

	public static FileDataStoreFactorySpi SHP_FACTORY = FileDataStoreFinder.getDataStoreFactory("shp");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static DataStore openShpDataStore(File f) {
		Map<String, Serializable> m = null;
		try {
			m = Collections.singletonMap("url", (Serializable) f.toURI().toURL());
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
		}
		return ds;
	}

}
