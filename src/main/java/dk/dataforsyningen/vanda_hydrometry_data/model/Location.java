package dk.dataforsyningen.vanda_hydrometry_data.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import dk.miljoeportal.vandah.model.DmpHydroApiResponsesLocationResponse;

public class Location {

	@NotNull
	Double x;
	
	@NotNull
	Double y;
	
	String srid;
	
	public static Location from(DmpHydroApiResponsesLocationResponse response) {
		if (response == null) return null;
		
		Location location = new Location();
		location.setX(response.getX());
		location.setY(response.getY());
		location.setSrid(response.getSrid());
		
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

	public String getSrid() {
		return srid;
	}

	public void setSrid(String srid) {
		this.srid = srid;
	}

	@Override
	public String toString() {
		return "Location [x=" + x + ", y=" + y + ", srid=" + srid + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(srid, x, y);
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
		return Objects.equals(srid, other.srid) && Objects.equals(x, other.x) && Objects.equals(y, other.y);
	}
	
	
}
