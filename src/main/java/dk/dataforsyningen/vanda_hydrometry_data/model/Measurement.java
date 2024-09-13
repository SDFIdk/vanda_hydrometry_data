package dk.dataforsyningen.vanda_hydrometry_data.model;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class Measurement {

	String measurementUid;
	
	String stationUid;
	
	String measurementTypeUid;
	
	Double result;
	
	OffsetDateTime measurementDateTime;
	
	boolean isCurrent;
	
	OffsetDateTime created;
	
	OffsetDateTime updated;
}
