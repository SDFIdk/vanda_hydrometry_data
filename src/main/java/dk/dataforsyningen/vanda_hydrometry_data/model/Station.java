package dk.dataforsyningen.vanda_hydrometry_data.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponse;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponseMeasurementPoint;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponseMeasurementPointExamination;

public class Station {
	
	@NotNull
	String stationUid = null;
	
	@NotNull
	@Size(max=8)
	String stationId = null; //Key
	
	@Size(max=100)
	String operatorStationId = null;
	
	@Size(max=8)
	String oldStationNumber = null;
	
	@Size(max=150)
	String Name = null;
	
	@Size(max=150)
	String stationOwnerName = null;
	
	@NotNull
	Location location = null;
	
	@Size(max=200)
	String description = null;
	
	@NotNull
	OffsetDateTime created = null;
	
	@NotNull
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
		station.setLocation(Location.from(response.getLocation()));
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
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getStationOwnerName() {
		return stationOwnerName;
	}

	public void setStationOwnerName(String stationOwnerName) {
		this.stationOwnerName = stationOwnerName;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
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

	@Override
	public String toString() {
		return "Station [" + 
				"\n\tstationUid=" + stationUid + 
				",\n\tstationId=" + stationId + 
				",\n\toperatorStationId=" + operatorStationId + 
				",\n\toldStationNumber=" + oldStationNumber + 
				",\n\tName=" + Name + 
				",\n\tstationOwnerName=" + stationOwnerName + 
				",\n\tlocation=" + location + 
				",\n\tdescription=" + description + 
				",\n\tcreated=" + created + 
				",\n\tupdated=" + updated +
				",\n\tmeasurementTypes=" + measurementTypes +
				"]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(Name, created, description, location, oldStationNumber, operatorStationId, stationId,
				stationOwnerName, stationUid, updated);
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
		return Objects.equals(Name, other.Name) && Objects.equals(created, other.created)
				&& Objects.equals(description, other.description) && Objects.equals(location, other.location)
				&& Objects.equals(oldStationNumber, other.oldStationNumber)
				&& Objects.equals(operatorStationId, other.operatorStationId)
				&& Objects.equals(stationId, other.stationId)
				&& Objects.equals(stationOwnerName, other.stationOwnerName)
				&& Objects.equals(stationUid, other.stationUid) && Objects.equals(updated, other.updated);
	}
	
	
}
