package dk.dataforsyningen.vanda_hydrometry_data.mapper;

import dk.dataforsyningen.vanda_hydrometry_data.model.Station;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponse;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponseMeasurementPoint;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponseMeasurementPointExamination;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StationModelMapper {

  private static final Logger logger = LoggerFactory.getLogger(StationModelMapper.class);

  public static Station from(DmpHydroApiResponsesStationResponse response) {
    if (response == null) {
      return null;
    }

    Station station = new Station();

    station.setStationId(response.getStationId());
    station.setStationUid(MapperHelper.validateString(response.getStationUid().toString()));
    station.setOperatorStationId(MapperHelper.validateString(response.getOperatorStationId()));
    station.setStationIdSav(MapperHelper.validateString(response.getOldStationNumber()));
    station.setName(MapperHelper.validateString(response.getName()));
    station.setStationOwnerName(MapperHelper.validateString(response.getStationOwnerName()));

    if (response.getLocation() != null) {
      // Need to check SRID first, because if it is not 25832 then we can not and should not insert the X and Y coordinate and SRID in the database
      if (response.getLocation().getSrid() != null) {
        String validatedSrid = MapperHelper.validateString(response.getLocation().getSrid());
        Integer convertedSrid = MapperHelper.convertSrid(validatedSrid);

        if (convertedSrid == 25832) {
          station.setGeometrySrid(convertedSrid);

          if (response.getLocation().getX() != null) {
            station.setGeometryX(response.getLocation().getX());
          } else {
            logger.error("X coordinate is null for stationId: " + station.getStationId());
            throw new NullPointerException(
                "X coordinate is null for stationId: " + station.getStationId());
          }

          if (response.getLocation().getY() != null) {
            station.setGeometryY(response.getLocation().getY());
          } else {
            logger.error("Y coordinate is null for stationId: " + station.getStationId());
            throw new NullPointerException(
                "Y coordinate is null for stationId: " + station.getStationId());
          }
        } else {
          throw new InvalidParameterException(
              "SRID is not 25832. It is: " + convertedSrid + "stationId" + station.getStationId());
        }
      } else {
        throw new NullPointerException("SRID is null for stationId: " + response.getStationId());
      }
    }

    station.setLocationType(MapperHelper.validateString(response.getLocationType()));
    station.setDescription(MapperHelper.validateString(response.getDescription()));
    station.setMeasurementTypes(new ArrayList<>());
    if (response.getMeasurementPoints() != null) {
      for (DmpHydroApiResponsesStationResponseMeasurementPoint mp : response.getMeasurementPoints()) {
        if (mp.getExaminations() != null) {
          for (DmpHydroApiResponsesStationResponseMeasurementPointExamination mpe : mp.getExaminations()) {
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
