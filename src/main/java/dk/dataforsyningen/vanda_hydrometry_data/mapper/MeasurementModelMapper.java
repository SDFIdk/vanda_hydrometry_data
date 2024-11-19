package dk.dataforsyningen.vanda_hydrometry_data.mapper;

import dk.dataforsyningen.vanda_hydrometry_data.model.Measurement;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesResultResponse;

public class MeasurementModelMapper {

  public static Measurement from(DmpHydroApiResponsesResultResponse response) {
    return from(response, null);
  }

  public static Measurement from(DmpHydroApiResponsesResultResponse response, String stationId) {
    if (response == null) {
      return null;
    }

    Measurement measurement = new Measurement();
    measurement.setMeasurementPointNumber(response.getMeasurementPointNumber());
    measurement.setValue(response.getResult());
    measurement.setValueElevationCorrected(response.getResultElevationCorrected());
    measurement.setMeasurementDateTime(response.getMeasurementDateTime());
    measurement.setExaminationTypeSc(response.getExaminationTypeSc());
    measurement.setIsCurrent(true); //the measurements coming from API are always the current

    measurement.setStationId(stationId);

    return measurement;
  }
}
