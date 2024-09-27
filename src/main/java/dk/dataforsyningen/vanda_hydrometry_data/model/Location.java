package dk.dataforsyningen.vanda_hydrometry_data.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import dk.miljoeportal.vandah.model.DmpHydroApiResponsesLocationResponse;

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

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public String getSrId() {
		return srId;
	}

	public void setSrId(String srId) {
		this.srId = srId;
	}

	@Override
	public String toString() {
		return "Location [x=" + x + ", y=" + y + ", srId=" + srId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(srId, x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return Objects.equals(srId, other.srId) && Objects.equals(x, other.x) && Objects.equals(y, other.y);
	}
	
	
}
