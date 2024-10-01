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

	@NotNull
	Double result = null;
	
	@NotNull
	OffsetDateTime created = null;
	
	@NotNull
	Boolean isCurrent = null;
	
	@NotNull
	String stationId = null; //FK

	@NotNull
	String measurementTypeId = null; //FK		
	
	public static Measurement from(DmpHydroApiResponsesResultResponse response) {
		return Measurement.from(response, null);
	}
	
	public static Measurement from(DmpHydroApiResponsesResultResponse response, String stationId) {
		if (response == null) return null;
		
		Measurement measurement = new Measurement();
		measurement.setMeasurementPointNumber(response.getMeasurementPointNumber());
		measurement.setResult(response.getResult());
		measurement.setMeasurementDateTime(response.getMeasurementDateTime());
		measurement.setMeasurementTypeId(MeasurementType.from(response).getMeasurementTypeId());
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

	public String getMeasurementTypeId() {
		return measurementTypeId;
	}

	public void setMeasurementTypeId(String measurementTypeId) {
		this.measurementTypeId = measurementTypeId;
	}

	@Override
	public String toString() {
		return "Measurement [" +
				"\n\tstationId=" + stationId +
				",\n\tmeasurementPointNumber=" + measurementPointNumber + 
				",\n\tmeasurementDateTime=" + measurementDateTime + 
				",\n\tresult=" + result + 
				",\n\tcreated=" + created + 
				",\n\tisCurrent=" + isCurrent +
				",\n\tmeasurementTypeId=" + measurementTypeId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(created, isCurrent, measurementDateTime, measurementPointNumber, measurementTypeId, result,
				stationId);
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
		return Objects.equals(created, other.created) && Objects.equals(isCurrent, other.isCurrent)
				&& Objects.equals(measurementDateTime, other.measurementDateTime)
				&& Objects.equals(measurementPointNumber, other.measurementPointNumber)
				&& Objects.equals(measurementTypeId, other.measurementTypeId) && Objects.equals(result, other.result)
				&& Objects.equals(stationId, other.stationId);
	}
	
	
}
