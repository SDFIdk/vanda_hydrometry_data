package dk.dataforsyningen.vanda_hydrometry_data.model;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponse;
import lombok.Data;

@Data
public class Station {
	
	@NotNull
	String stationUid;
	
	@Size(max=8)
	String stationId; //Key
	
	@Size(max=100)
	String operatorStationId;
	
	@Size(max=8)
	String oldStationNumber;
	
	@Size(max=150)
	String Name;
	
	@Size(max=150)
	String stationOwnerName;
	
	@NotNull
	Location location;
	
	@Size(max=200)
	String description;
	
	@NotNull
	OffsetDateTime created;
	
	@NotNull
	OffsetDateTime updated;

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
		
		return station;
	}
}
