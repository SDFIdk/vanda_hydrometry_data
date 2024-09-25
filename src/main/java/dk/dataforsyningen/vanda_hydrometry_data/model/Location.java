package dk.dataforsyningen.vanda_hydrometry_data.model;

import javax.validation.constraints.NotNull;

import dk.miljoeportal.vandah.model.DmpHydroApiResponsesLocationResponse;
import lombok.Data;

@Data
public class Location {

	@NotNull
	Double x;
	
	@NotNull
	Double y;
	
	String srId;
	
	public static Location from(DmpHydroApiResponsesLocationResponse response) {
		if (response == null) return null;
		
		Location location = new Location();
		location.setX(response.getX());
		location.setY(response.getY());
		location.setSrId(response.getSrid());
		
		return location;
	}
}
