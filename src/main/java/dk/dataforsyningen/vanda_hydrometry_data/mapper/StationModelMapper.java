package dk.dataforsyningen.vanda_hydrometry_data.mapper;

import java.util.ArrayList;

import dk.dataforsyningen.vanda_hydrometry_data.components.VandaHUtility;
import dk.dataforsyningen.vanda_hydrometry_data.model.Station;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponse;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponseMeasurementPoint;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponseMeasurementPointExamination;

public class StationModelMapper {
	
	public static Station from(DmpHydroApiResponsesStationResponse response) {
		if (response == null) return null;
		
		Station station = new Station();
		
		station.setStationId(response.getStationId());
		station.setStationUid(response.getStationUid() != null ? MapperHelper.validate(response.getStationUid().toString()) : null);
		station.setOperatorStationId(MapperHelper.validate(response.getOperatorStationId()));
		station.setOldStationNumber(MapperHelper.validate(response.getOldStationNumber()));
		station.setName(MapperHelper.validate(response.getName()));
		station.setStationOwnerName(MapperHelper.validate(response.getStationOwnerName()));
		station.setLocationX(response.getLocation() != null ? response.getLocation().getX() : null);
		station.setLocationY(response.getLocation() != null ? response.getLocation().getY() : null);
		station.setLocationSrid(response.getLocation() != null && response.getLocation().getSrid() != null ? VandaHUtility.toInt(response.getLocation().getSrid()) : null);
		station.setLocationType(MapperHelper.validate(response.getLocationType()));
		station.setDescription(MapperHelper.validate(response.getDescription()));
		station.setMeasurementTypes(new ArrayList<>());
		if (response.getMeasurementPoints() != null) {
			for(DmpHydroApiResponsesStationResponseMeasurementPoint mp : response.getMeasurementPoints()) {
				if (mp.getExaminations() != null) {
					for(DmpHydroApiResponsesStationResponseMeasurementPointExamination mpe : mp.getExaminations()) {
						if (mpe != null) {
							station.getMeasurementTypes().add(MeasurementTypeModelMapper.from(mpe));
						}
					}
				}
			}
		}
		
		return station;
	}
}
