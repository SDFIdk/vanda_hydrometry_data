package dk.dataforsyningen.vanda_hydrometry_data.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import dk.miljoeportal.vandah.model.DmpHydroApiResponsesResultResponse;
import lombok.Data;

@Data
public class MeasurementType {
	
	@NotNull
	String measurementTypeId; //Key
	
	@NotNull
	@Size(max=4)
	String unit;
	
	@NotNull
	Integer unitSc;
	
	@NotNull
	@Size(max=10)
	String parameter;
	
	@NotNull
	Integer parameterSc;
	
	@NotNull
	@Size(max=10)
	String examinationType;
	
	@NotNull
	Integer examinationTypeSc;
	
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

}
