package ws.gazebo.util.region.state;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.geotools.data.DataStore;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;

import ws.gazebo.util.ontTool.shp.OntToolShp;

public class StateTableManager {

	public static final String STATE_TABLE_SOURCE = "http://www.census.gov/geo/reference/ansi_statetables.html";
	public static final String STATE_ADM_DATA_SOURCE = "http://nationalatlas.gov/atlasftp.html#statep0";

	private static StateTable tableInstance;

	public static void main(String[] args) {
		// # Procedural Overview
		//
		// 1. tableInstance = OntToolCensusStates.readSateInfo(args[0])
		// 2. Collate the table instance with _ADM data, using OntToolShp
		// ...
		// A. Define GNIS feature type ontology
		// B. ...
		// 3. Load state class ontologies
		// 4. Generate state instance ontologies using Apache Jena

		// read Census CSV file
		try {
			String censusStateInfoFileP = args[0];
			tableInstance = OntToolCensusStates
					.readStateInfo(censusStateInfoFileP);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		// open statesp020 Shapefile as Geotools DataStore
		String stateShpFileP = args[1];
		DataStore ds = OntToolShp.openShpDataStore(stateShpFileP);
		FeatureIterator<SimpleFeature> shpIter = null;
		try {
			shpIter = OntToolShp.getShpFeatureIterator(ds);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(2);
		}

		// iterate over features properties in datastore.
		// this program wil exit if an expected feature property is missing
		SimpleFeature f;
		String pStr, monthNameUnion;
		byte fipsID, monthUnion;
		int ordUnion;
		SimpleDateFormat monthFmt = new SimpleDateFormat("MMMM", Locale.ENGLISH);
		Date d = null;
		Calendar c = Calendar.getInstance();
		long dayUnion, yearUnion;
		StateInfo info;
		while (shpIter.hasNext()) {
			f = shpIter.next();

			// retrieve entity FIPS ID; retrieve StateInfo on that value as key
			pStr = (String) requiredStringPropertyValue("STATE_FIPS", f);
			fipsID = new Byte(pStr);
			// CHECK
			// System.out.println("Found FIPS ID " + fipsID);

			info = tableInstance.getStateInfo(fipsID);

			ordUnion = requiredIntPropertyValue("ORDER_ADM", f);
			if (ordUnion != 0) { // states only have non-zero ordUnion.
				// byte ordinal value encoded in data source as of type 'int'
				info.setOrderAdmittedUnion((byte) ordUnion);

				dayUnion = requiredLongPropertyValue("DAY_ADM", f);
				// day of month encoded in data source as of type 'long'
				info.setDayAdmittedUnion((byte) dayUnion);

				// month encoded in data source as of type 'string' e.g
				// "January"
				monthNameUnion = requiredStringPropertyValue("MONTH_ADM", f);
				// DEBUG System.out.println("Read month \"" + monthNameUnion +
				// "\"");
				try {
					d = monthFmt.parse(monthNameUnion);
				} catch (ParseException e) {
					e.printStackTrace();
					System.exit(42);
				}
				c.setTime(d);
				// Java uses '0' for January, in the Calendar.MONTH encoding
				monthUnion = (byte) (c.get(Calendar.MONTH) + 1);
				// DEBUG System.out.println("...as " + monthUnion);
				info.setMonthAdmittedUnion(monthUnion);

				// year encoded in data source as of type 'long'
				yearUnion = requiredLongPropertyValue("YEAR_ADM", f);
				info.setYearAdmittedUnion((short) yearUnion);

			}
		}
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
