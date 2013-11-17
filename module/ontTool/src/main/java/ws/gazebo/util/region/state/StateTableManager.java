package ws.gazebo.util.region.state;

import java.io.IOException;

public class StateTableManager {
	
	public static final String STATE_TABLE_SOURCE = "http://www.census.gov/geo/reference/ansi_statetables.html";
	public static final String STATE_ADM_DATA_SOURCE = "http://nationalatlas.gov/atlasftp.html#statep0";

	private static StateTable tableInstance;
	
	public static void main(String[] args) {
		
		try {
			tableInstance = OntToolCensusStates.readStateInfo(args[0]);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(127);
		}
		
		// 1. tableInstance = OntToolCensusStates.readSateInfo(args[0])
		// 2. Collate the table instance with _ADM data, using OntToolShp
		// ...
		//  A. Define GNIS feature type ontology
		//  B. ...
		// 3. Load state class ontologies
		// 4. Generate state instance ontologies using Apache Jena


	}

}
