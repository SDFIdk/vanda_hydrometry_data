package dk.dataforsyningen.vanda_hydrometry_data.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Station {
	
	String stationUid = null;
	
	String stationId = null; //Key
	
	String operatorStationId = null;
	
	String oldStationNumber = null;
	
	String name = null;
	
	String stationOwnerName = null;
	
	Double x;
	
	Double y;
	
	String srid;
	
	String locationType = null;
	
	String description = null;
	
	OffsetDateTime created = null;
	
	OffsetDateTime updated = null;
	
	ArrayList<MeasurementType> measurementTypes = new ArrayList<>();


	public String getStationUid() {
		return stationUid;
	}

	public void setStationUid(String stationUid) {
		this.stationUid = (stationUid == null || stationUid.isEmpty() ? null : stationUid);
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getOperatorStationId() {
		return operatorStationId;
	}

	public void setOperatorStationId(String operatorStationId) {
		this.operatorStationId = (operatorStationId == null || operatorStationId.isEmpty() ? null : operatorStationId);
	}

	public String getOldStationNumber() {
		return oldStationNumber;
	}

	public void setOldStationNumber(String oldStationNumber) {
		this.oldStationNumber = (oldStationNumber == null || oldStationNumber.isEmpty() ? null : oldStationNumber);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStationOwnerName() {
		return stationOwnerName;
	}

	public void setStationOwnerName(String stationOwnerName) {
		this.stationOwnerName = (stationOwnerName == null || stationOwnerName.isEmpty() ? null : stationOwnerName);
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public String getSrid() {
		return srid;
	}

	public void setSrid(String srid) {
		this.srid = srid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = (description == null || description.isEmpty() ? null : description);
	}

	public OffsetDateTime getCreated() {
		return created;
	}

	public void setCreated(OffsetDateTime created) {
		this.created = created;
	}

	public OffsetDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(OffsetDateTime updated) {
		this.updated = updated;
	}

	public ArrayList<MeasurementType> getMeasurementTypes() {
		return measurementTypes;
	}

	public void setMeasurementTypes(ArrayList<MeasurementType> measurementTypes) {
		this.measurementTypes = measurementTypes;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = (locationType == null || locationType.isEmpty() ? null : locationType);
	}

	@Override
	public String toString() {
		return "Station [" + 
				"\n\tstationUid=" + stationUid + 
				",\n\tstationId=" + stationId + 
				",\n\toperatorStationId=" + operatorStationId + 
				",\n\toldStationNumber=" + oldStationNumber + 
				",\n\tname=" + name + 
				",\n\tstationOwnerName=" + stationOwnerName + 
				",\n\tlocation= [x=" + x + ", y=" + y + ", srid=" + srid + "]" +
				",\n\tlocationType=" + locationType +
				",\n\tdescription=" + description + 
				",\n\tcreated=" + created + 
				",\n\tupdated=" + updated +
				",\n\tmeasurementTypes=" + measurementTypes +
				"]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, created, description, x, y, srid, locationType, oldStationNumber, 
				operatorStationId, stationId, stationOwnerName, stationUid, updated);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Station other = (Station) obj;
		return Objects.equals(stationUid, other.stationUid)
				&& Objects.equals(stationId, other.stationId)
				&& Objects.equals(stationOwnerName, other.stationOwnerName)
				&& Objects.equals(name, other.name) 
				&& Objects.equals(locationType, other.locationType) 
				&& Objects.equals(description, other.description) 
				&& Objects.equals(srid, other.srid) && Objects.equals(x, other.x) && Objects.equals(y, other.y)
				&& Objects.equals(oldStationNumber, other.oldStationNumber)
				&& Objects.equals(operatorStationId, other.operatorStationId)
				&& Objects.equals(created, other.created)
				&& Objects.equals(updated, other.updated);
	}
	
	
}
