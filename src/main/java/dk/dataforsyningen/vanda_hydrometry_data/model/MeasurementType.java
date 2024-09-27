package dk.dataforsyningen.vanda_hydrometry_data.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import dk.miljoeportal.vandah.model.DmpHydroApiResponsesResultResponse;

public class MeasurementType {
	
	@NotNull
	String measurementTypeId = null; //Key
	
	@NotNull
	@Size(max=4)
	String unit = null;
	
	@NotNull
	Integer unitSc = null;
	
	@NotNull
	@Size(max=10)
	String parameter = null;
	
	@NotNull
	Integer parameterSc = null;
	
	@NotNull
	@Size(max=10)
	String examinationType = null;
	
	@NotNull
	Integer examinationTypeSc = null;
	
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

	public String getMeasurementTypeId() {
		return measurementTypeId;
	}

	public void setMeasurementTypeId(String measurementTypeId) {
		this.measurementTypeId = measurementTypeId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getUnitSc() {
		return unitSc;
	}

	public void setUnitSc(Integer unitSc) {
		this.unitSc = unitSc;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Integer getParameterSc() {
		return parameterSc;
	}

	public void setParameterSc(Integer parameterSc) {
		this.parameterSc = parameterSc;
	}

	public String getExaminationType() {
		return examinationType;
	}

	public void setExaminationType(String examinationType) {
		this.examinationType = examinationType;
	}

	public Integer getExaminationTypeSc() {
		return examinationTypeSc;
	}

	public void setExaminationTypeSc(Integer examinationTypeSc) {
		this.examinationTypeSc = examinationTypeSc;
	}

	@Override
	public String toString() {
		return "MeasurementType [" +
				"\n\tmeasurementTypeId=" + measurementTypeId + 
				",\n\tunit=" + unit + 
				",\n\tunitSc=" + unitSc +
				",\n\tparameter=" + parameter + 
				",\n\tparameterSc=" + parameterSc + 
				",\n\texaminationType=" + examinationType +
				",\n\texaminationTypeSc=" + examinationTypeSc + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(examinationType, examinationTypeSc, measurementTypeId, parameter, parameterSc, unit,
				unitSc);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeasurementType other = (MeasurementType) obj;
		return Objects.equals(examinationType, other.examinationType)
				&& Objects.equals(examinationTypeSc, other.examinationTypeSc)
				&& Objects.equals(measurementTypeId, other.measurementTypeId)
				&& Objects.equals(parameter, other.parameter) && Objects.equals(parameterSc, other.parameterSc)
				&& Objects.equals(unit, other.unit) && Objects.equals(unitSc, other.unitSc);
	}
	
	public boolean equalsWithoutId(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeasurementType other = (MeasurementType) obj;
		return Objects.equals(examinationType, other.examinationType)
				&& Objects.equals(examinationTypeSc, other.examinationTypeSc)
				&& Objects.equals(parameter, other.parameter) && Objects.equals(parameterSc, other.parameterSc)
				&& Objects.equals(unit, other.unit) && Objects.equals(unitSc, other.unitSc);
	}
	
	
}
