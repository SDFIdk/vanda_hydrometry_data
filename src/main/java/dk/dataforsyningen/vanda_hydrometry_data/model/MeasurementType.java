package dk.dataforsyningen.vanda_hydrometry_data.model;

import lombok.Data;

@Data
public class MeasurementType {
	
	String measurementTypeUid;
	
	String unit;
	
	String parameter;

}
