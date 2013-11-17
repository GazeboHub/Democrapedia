package ws.gazebo.util.region.state;

import java.util.ArrayList;
import java.util.HashMap;

public class StateTable {
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -1L;
	
	ArrayList<StateInfo> table;
	HashMap<Byte, StateInfo> fips52map;

	/**
	 * Initialize table for 57 expected entries.
	 * 
	 * @see <a
	 *      href="http://www.census.gov/geo/reference/ansi_statetables.html">ANSI
	 *      state table data</a>, US Department of Census.
	 */
	public StateTable() {
		this(57);
	}

	public StateTable(int expectedEntriesCount) {
		table = new ArrayList<StateInfo>(expectedEntriesCount);
		fips52map = new HashMap<Byte, StateInfo>(expectedEntriesCount);
	}

	/**
	 * Method for initializing a StateInfo instance based on <a
	 * href="http://www.census.gov/geo/reference/ansi_statetables.html">ANSI
	 * state table data</a> from the US Department of Census. This method
	 * ensures that the instance will be added to the table and all internal
	 * indices in the table.
	 * 
	 * @return new StateInfo instance
	 */
	public StateInfo addStateInfo(String[] fields) {
		StateInfo info = new StateInfo(fields);
		table.add(info);
		byte fips52code = info.getStateNumericANSICode();
		fips52map.put(fips52code, info);
		return info;
	}

	/**
	 * Return the {@link StateInfo} instance denoted uniquely with the
	 * {@code fips52code} value as per INCITS 38:200x (previously FIPS 5-2)
	 * 
	 * @param fips52code
	 *            the INCITS 38:200x code for the instance
	 * @return {@link StateInfo} instance
	 */
	public StateInfo getStateInfo(byte fips52code) {
		return fips52map.get(fips52code);
	}

}
