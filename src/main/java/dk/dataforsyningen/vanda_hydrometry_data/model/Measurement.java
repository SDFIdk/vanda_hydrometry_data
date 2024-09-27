package dk.dataforsyningen.vanda_hydrometry_data.model;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;

import dk.dataforsyningen.vanda_hydrometry_data.components.VandaHUtility;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesResultResponse;
import lombok.Data;

@Data
public class Measurement {

	@NotNull
	Integer measurementPointNumber;

	@NotNull
	OffsetDateTime measurementDateTime;

	@NotNull
	Double result;
	
	@NotNull
	OffsetDateTime created;
	
	@NotNull
	Boolean isCurrent;
	
	@NotNull
	String stationId; //FK

	@NotNull
	String measurementTypeId; //FK
		
	
	public static Measurement from(DmpHydroApiResponsesResultResponse response) {
		return Measurement.from(response, null);
	}
	
	public static Measurement from(DmpHydroApiResponsesResultResponse response, String stationId) {
		if (response == null) return null;
		
		Measurement measurement = new Measurement();
		measurement.setMeasurementPointNumber(response.getMeasurementPointNumber());
		measurement.setResult(response.getResult());
		measurement.setMeasurementDateTime(response.getMeasurementDateTime());
		
		measurement.setStationId(stationId);
		
		return measurement;
	}

}
