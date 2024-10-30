package dk.dataforsyningen.vanda_hydrometry_data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import dk.dataforsyningen.vanda_hydrometry_data.components.VandaHUtility;
import dk.dataforsyningen.vanda_hydrometry_data.model.MeasurementType;
import dk.dataforsyningen.vanda_hydrometry_data.model.Station;

public class StationMapper implements RowMapper<Station> {

	@Override
	public Station map(ResultSet rs, StatementContext ctx) throws SQLException {
		Station station = new Station();
		
		station.setStationId(nullifyEmpty(rs.getString("station_id")));
		station.setOldStationNumber(nullifyEmpty(rs.getString("old_station_number")));
		station.setName(nullifyEmpty(rs.getString("name")));
		station.setStationOwnerName(nullifyEmpty(rs.getString("station_owner_name")));
		station.setLocationX((Double) rs.getObject("location_x"));
		station.setLocationY((Double) rs.getObject("location_y"));
		station.setLocationSrid(rs.getString("location_srid"));
		station.setLocationType(nullifyEmpty(rs.getString("location_type")));
		station.setDescription(nullifyEmpty(rs.getString("description")));
		station.setCreated(VandaHUtility.toOffsetDate(rs.getTimestamp("created"), false));
		station.setUpdated(VandaHUtility.toOffsetDate(rs.getTimestamp("updated"), false));
		
		if ((Integer)rs.getObject("examination_type_sc") != null) {
			MeasurementType mt = new MeasurementType();
		
			mt.setParameterSc(rs.getInt("parameter_sc"));
			mt.setParameter(rs.getString("parameter"));
			mt.setExaminationTypeSc(rs.getInt("examination_type_sc"));
			mt.setExaminationType(rs.getString("examination_type"));
			mt.setUnitSc(rs.getInt("unit_sc"));
			mt.setUnit(rs.getString("unit"));
			
			station.getMeasurementTypes().add(mt);
		}
				
		return station;
	}

	private String nullifyEmpty(String s) {
		return (s == null || s.isEmpty() ? null : s);
	}
}
