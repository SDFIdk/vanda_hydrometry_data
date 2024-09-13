package dk.dataforsyningen.vanda_hydrometry_data.model;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class Station {
	
	String stationUid;
	
	String stationId;
	
	String Name;
	
	String stationOwnerName;
	
	Location location;
	
	String description;
	
	OffsetDateTime created;
	
	OffsetDateTime updated;

}
