package dk.dataforsyningen.vanda_hydrometry_data.model;

import java.time.OffsetDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import dk.miljoeportal.vandah.model.DmpHydroApiResponsesResultResponse;

public class Measurement {

	@NotNull
	Integer measurementPointNumber = null;

	@NotNull
	OffsetDateTime measurementDateTime = null;
	
	Double resultElevationCorrected = null;

	@NotNull
	Double result = null;
	
	@NotNull
	OffsetDateTime created = null;
	
	@NotNull
	OffsetDateTime updated = null;
	
	OffsetDateTime vandaEventTimestamp = null;
	
	@NotNull
	Boolean isCurrent = null;
	
	@NotNull
	String stationId = null; //FK

	@NotNull
	Integer examinationTypeSc = null; //FK		
	
	public static Measurement from(DmpHydroApiResponsesResultResponse response) {
		return Measurement.from(response, null);
	}
	
	public static Measurement from(DmpHydroApiResponsesResultResponse response, String stationId) {
		if (response == null) return null;
		
		Measurement measurement = new Measurement();
		measurement.setMeasurementPointNumber(response.getMeasurementPointNumber());
		measurement.setResult(response.getResult());
		measurement.setMeasurementDateTime(response.getMeasurementDateTime());
		measurement.setExaminationTypeSc(MeasurementType.from(response).getExaminationTypeSc());
		measurement.setIsCurrent(true); //the measurements coming from API are always the current
		
		measurement.setStationId(stationId);
		
		return measurement;
	}

	public Integer getMeasurementPointNumber() {
		return measurementPointNumber;
	}

	public void setMeasurementPointNumber(Integer measurementPointNumber) {
		this.measurementPointNumber = measurementPointNumber;
	}

	public OffsetDateTime getMeasurementDateTime() {
		return measurementDateTime;
	}

	public void setMeasurementDateTime(OffsetDateTime measurementDateTime) {
		this.measurementDateTime = measurementDateTime;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public OffsetDateTime getCreated() {
		return created;
	}

	public void setCreated(OffsetDateTime created) {
		this.created = created;
	}

	public Boolean getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(Boolean isCurrent) {
		this.isCurrent = isCurrent;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	
	

	public Double getResultElevationCorrected() {
		return resultElevationCorrected;
	}

	public void setResultElevationCorrected(Double resultElevationCorrected) {
		this.resultElevationCorrected = resultElevationCorrected;
	}

	public OffsetDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(OffsetDateTime updated) {
		this.updated = updated;
	}

	public OffsetDateTime getVandaEventTimestamp() {
		return vandaEventTimestamp;
	}

	public void setVandaEventTimestamp(OffsetDateTime vandaEventTimestamp) {
		this.vandaEventTimestamp = vandaEventTimestamp;
	}

	public Integer getExaminationTypeSc() {
		return examinationTypeSc;
	}

	public void setExaminationTypeSc(Integer examinationTypeSc) {
		this.examinationTypeSc = examinationTypeSc;
	}

	@Override
	public String toString() {
		return "Measurement [measurementPointNumber=" + measurementPointNumber 
				+ ", measurementDateTime=" + measurementDateTime 
				+ ", resultElevationCorrected=" + resultElevationCorrected 
				+ ", result=" + result
				+ ", created=" + created 
				+ ", updated=" + updated 
				+ ", vandaEventTimestamp=" + vandaEventTimestamp
				+ ", isCurrent=" + isCurrent 
				+ ", stationId=" + stationId 
				+ ", examinationTypeSc=" + examinationTypeSc
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(created, examinationTypeSc, isCurrent, measurementDateTime, measurementPointNumber, result,
				resultElevationCorrected, stationId, updated, vandaEventTimestamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Measurement other = (Measurement) obj;
		return Objects.equals(created, other.created) && Objects.equals(examinationTypeSc, other.examinationTypeSc)
				&& Objects.equals(isCurrent, other.isCurrent)
				&& Objects.equals(measurementDateTime, other.measurementDateTime)
				&& Objects.equals(measurementPointNumber, other.measurementPointNumber)
				&& Objects.equals(result, other.result)
				&& Objects.equals(resultElevationCorrected, other.resultElevationCorrected)
				&& Objects.equals(stationId, other.stationId) && Objects.equals(updated, other.updated)
				&& Objects.equals(vandaEventTimestamp, other.vandaEventTimestamp);
	}


	
	
}
