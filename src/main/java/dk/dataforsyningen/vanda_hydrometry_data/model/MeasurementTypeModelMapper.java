package dk.dataforsyningen.vanda_hydrometry_data.model;

import dk.miljoeportal.vandah.model.DmpHydroApiResponsesResultResponse;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponseMeasurementPointExamination;

public class MeasurementTypeModelMapper {
	
	public static MeasurementType from(DmpHydroApiResponsesResultResponse result) {
		if (result == null) return null;
		
		MeasurementType measurementType = new MeasurementType();
		measurementType.setParameter(result.getParameter());
		measurementType.setParameterSc(result.getParameterSc());
		measurementType.setExaminationType(result.getExaminationType());
		measurementType.setExaminationTypeSc(result.getExaminationTypeSc());
		measurementType.setUnit(result.getUnit());
		measurementType.setUnitSc(result.getUnitSc());
				
		return measurementType;
	}
	
	public static MeasurementType from(DmpHydroApiResponsesStationResponseMeasurementPointExamination result) {
		if (result == null) return null;
		
		MeasurementType measurementType = new MeasurementType();
		measurementType.setParameter(result.getParameter());
		measurementType.setParameterSc(result.getParameterSc());
		measurementType.setExaminationType(result.getExaminationType());
		measurementType.setExaminationTypeSc(result.getExaminationTypeSc());
		measurementType.setUnit(result.getUnit());
		measurementType.setUnitSc(result.getUnitSc());
				
		return measurementType;
	}
}
