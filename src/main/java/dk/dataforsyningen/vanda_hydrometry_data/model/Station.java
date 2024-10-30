package dk.dataforsyningen.vanda_hydrometry_data.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Objects;

import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponse;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponseMeasurementPoint;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponseMeasurementPointExamination;

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

	public static Station from(DmpHydroApiResponsesStationResponse response) {
		if (response == null) return null;
		
		Station station = new Station();
		
		station.setStationId(response.getStationId());
		station.setStationUid(response.getStationUid() != null ? response.getStationUid().toString() : null);
		station.setOperatorStationId(response.getOperatorStationId());
		station.setOldStationNumber(response.getOldStationNumber());
		station.setName(response.getName());
		station.setStationOwnerName(response.getStationOwnerName());
		station.setX(response.getLocation() != null ? response.getLocation().getX() : null);
		station.setY(response.getLocation() != null ? response.getLocation().getY() : null);
		station.setSrid(response.getLocation() != null ? response.getLocation().getSrid() : null);
		station.setLocationType(response.getLocationType());
		station.setDescription(response.getDescription());
		station.measurementTypes = new ArrayList<>();
		if (response.getMeasurementPoints() != null) {
			for(DmpHydroApiResponsesStationResponseMeasurementPoint mp : response.getMeasurementPoints()) {
				if (mp.getExaminations() != null) {
					for(DmpHydroApiResponsesStationResponseMeasurementPointExamination mpe : mp.getExaminations()) {
						if (mpe != null) {
							station.measurementTypes.add(MeasurementType.from(mpe));
						}
					}
				}
			}
		}
		
		return station;
	}

	public String getStationUid() {
		return stationUid;
	}

	public void setStationUid(String stationUid) {
		this.stationUid = stationUid;
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
		this.operatorStationId = operatorStationId;
	}

	public String getOldStationNumber() {
		return oldStationNumber;
	}

	public void setOldStationNumber(String oldStationNumber) {
		this.oldStationNumber = oldStationNumber;
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
		this.stationOwnerName = stationOwnerName;
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
		this.description = description;
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
		this.locationType = locationType;
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
