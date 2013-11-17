package ws.gazebo.util.ontTool.shp;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import ws.gazebo.util.region.state.StateInfo;
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

}
