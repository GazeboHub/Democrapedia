package ws.gazebo.util.ontTool.shp;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import au.com.bytecode.opencsv.CSVReader;

public class OntToolCensusStates {


	public static void main(String[] args) {

		// to do: use the iterator approach for OpenCSV
		// cf. http://opencsv.sourceforge.net/#how-to-read
		// essentially producing Ontology instance data and properties from the
		// input value
		
		
	}

	public static final Collection<StateInfo> readStateInfo(String pathname)
			throws IOException {
		String[] buffer;
		StateInfo info;
		Collection<StateInfo> table = new ArrayList<StateInfo>(57);
		
		CSVReader reader = new CSVReader(new FileReader(pathname));
		while((buffer = reader.readNext()) != null) {
			info = new StateInfo(buffer);
			table.add(info);
		}
		return table;
	}
	
	public final class StateInfo {

		// cf. http://www.census.gov/geo/reference/ansi_statetables.html
		// data file available at src/test/resources/census/state.txt

		int stateNumericANSICode; 
		// ^ xsd:byte length=2, pattern=[0-9]{2}, maxInclusive=78
		// ^ INCITS 38:200x (previously FIPS 5-2)
		// ^ PRIMARY KEY
		String statePostalCode; 
		// ^ xsd:string length=2, pattern=[A-Z]{2}
		// ^ UNIQUE
		String name;
		// ^ xsd:string
		// ^ UNIQUE
		int gnisFeatureCode; 
		// ^ xsd:byte pattern=[1-9][0-9]{0,9}
		// ^ UNIQUE, "Alternate key"

		public StateInfo(String[] fields) {
			stateNumericANSICode = new Integer(fields[0]);
			statePostalCode = fields[1];
			name = fields[2];
			gnisFeatureCode = new Integer(fields[3]);
		}

		// cf. http://geonames.usgs.gov/domestic/states_fileformat.htm
		// int gnisFeatureID; // pattern=[1-9][0-9]{0,9} | read string as int
		// (unsigned)
		// String gisFeatureName;
		// - skip feature class = "Civil"
		// String uspsCode; // length=2, pattern=[A-Z]{2}
		// byte numericCode; // length=2, pattern=[0-9]{2}, maxInclusive=78 |
		// INCITS 38:200x (previously FIPS 5-2)
		// - skip county name (to do: don't sip)
		// - skip county numeric (to do: don't ski)
		// - skip remaining fields
	}

}
