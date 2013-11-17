package ws.gazebo.util.region.state;

public final class StateInfo {

	// cf. http://www.census.gov/geo/reference/ansi_statetables.html
	// 
	// data file available at 
	// http://www.census.gov/geo/reference/docs/state.txt
	// locally cached at 
	// workspace:/democrapedia/module/ontTool/src/test/resources/census/state.txt

	byte stateNumericANSICode; 
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
	
	// cf. http://nationalatlas.gov/mld/statesp.html
	byte orderAdmittedUnion;
	byte dayAdmittedUnion;
	byte monthAdmittedUnion;
	short yearAdmittedUnion;
	

	public StateInfo(String[] fields) {
		stateNumericANSICode = new Byte(fields[0]);
		statePostalCode = fields[1];
		name = fields[2];
		gnisFeatureCode = new Integer(fields[3]);
	}


	public byte getStateNumericANSICode() {
		return stateNumericANSICode;
	}


	public void setStateNumericANSICode(byte stateNumericANSICode) {
		this.stateNumericANSICode = stateNumericANSICode;
	}


	public String getStatePostalCode() {
		return statePostalCode;
	}


	public void setStatePostalCode(String statePostalCode) {
		this.statePostalCode = statePostalCode;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getGnisFeatureCode() {
		return gnisFeatureCode;
	}


	public void setGnisFeatureCode(int gnisFeatureCode) {
		this.gnisFeatureCode = gnisFeatureCode;
	}


	public byte getDayAdmittedUnion() {
		return dayAdmittedUnion;
	}


	public void setDayAdmittedUnion(byte dayAdmittedUnion) {
		this.dayAdmittedUnion = dayAdmittedUnion;
	}


	public byte getMonthAdmittedUnion() {
		return monthAdmittedUnion;
	}


	public void setMonthAdmittedUnion(byte monthAdmittedUnion) {
		this.monthAdmittedUnion = monthAdmittedUnion;
	}


	public short getYearAdmittedUnion() {
		return yearAdmittedUnion;
	}


	public void setYearAdmittedUnion(short yearAdmittedUnion) {
		this.yearAdmittedUnion = yearAdmittedUnion;
	}


	public byte getOrderAdmittedUnion() {
		return orderAdmittedUnion;
	}


	public void setOrderAdmittedUnion(byte orderAdmittedUnion) {
		this.orderAdmittedUnion = orderAdmittedUnion;
	}

	
}