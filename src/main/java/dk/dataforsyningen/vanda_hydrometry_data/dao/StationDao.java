package dk.dataforsyningen.vanda_hydrometry_data.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import dk.dataforsyningen.vanda_hydrometry_data.model.Station;

@RegisterRowMapper(StationMapper.class)
public interface StationDao {
	
	@SqlQuery("select * from station")
	List<Station> readAllStations();
	
	@SqlQuery("select * from station where station_id = :stationId ")
	Station findStation(@Bind String stationId);

	/**
	 * Add station if not exists
	 * @param station
	 */
	@SqlUpdate("""
			insert into station 
			(station_id, old_station_number, name, station_owner_name, location, description, created, updated)
			values ( :stationId, :oldStationNumber, :name, :stationOwnerName, :location, :description, :created, :updated)
			on conflict (station_id) do nothing
			""")
	void addStation(@BindBean Station station);
	
	@SqlBatch("addStation")
	void addStations(@BindBean List<Station> stations);
}
