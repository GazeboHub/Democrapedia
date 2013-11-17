package ws.gazebo.util.region.state;

import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

public class OntToolCensusStates {

	public static final StateInfoTable readStateInfo(String pathname)
			throws IOException {
		String[] buffer;
		StateInfoTable table = new StateInfoTable();

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(pathname));
			while ((buffer = reader.readNext()) != null) {
				table.addStateInfo(buffer);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		return table;
	}

}
