package dk.dataforsyningen.vanda_hydrometry_data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import dk.dataforsyningen.vanda_hydrometry_data.components.VandaHUtility;
import dk.dataforsyningen.vanda_hydrometry_data.model.Station;

public class StationMapper implements RowMapper<Station> {

	@Override
	public Station map(ResultSet rs, StatementContext ctx) throws SQLException {
		Station station = new Station();
		
		station.setStationId(rs.getString("station_id"));
		station.setOldStationNumber(rs.getString("old_station_number"));
		station.setName(rs.getString("name"));
		station.setStationOwnerName(rs.getString("station_owner_name"));
		station.setLocation(VandaHUtility.toLocation(rs.getString("location_as_text")));
		station.setDescription(rs.getString("description"));
		station.setCreated(VandaHUtility.toOffsetDate(rs.getTimestamp("created"), false));
		station.setUpdated(VandaHUtility.toOffsetDate(rs.getTimestamp("updated"), false));
				
		return station;
	}

}
