package dk.dataforsyningen.vanda_hydrometry_data.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import dk.dataforsyningen.vanda_hydrometry_data.components.LogSqlFactory;
import dk.dataforsyningen.vanda_hydrometry_data.model.Station;

@RegisterRowMapper(StationMapper.class)
@LogSqlFactory
public interface StationDao {
	
	@SqlQuery("select *, ST_AsText(location) as location_as_text from station")
	List<Station> getAllStations();
	
	@SqlQuery("select * from station where station_id = :stationId ")
	Station findStation(@Bind String stationId);

	/**
	 * Add station if not exists
	 * @param station
	 */
	@SqlUpdate("""
			insert into station 
			(station_id, old_station_number, name, station_owner_name, location, description, created, updated)
			values ( :stationId, :oldStationNumber, :name, :stationOwnerName, (ST_SetSRID(ST_MakePoint(:location.x, :location.y), 25832)), :description, now(), now())
			on conflict (station_id) do update
				set old_station_number = :oldStationNumber, 
				name = :name, 
				station_owner_name = :stationOwnerName, 
				location = (ST_SetSRID(ST_MakePoint(:location.x, :location.y), 25832)), 
				description = :description, 
				updated = now() 
			""")
	void addStation(@BindBean Station station);
	
	@SqlBatch("""
			insert into station 
			(station_id, old_station_number, name, station_owner_name, location, description, created, updated)
			values ( :stationId, :oldStationNumber, :name, :stationOwnerName, (ST_SetSRID(ST_MakePoint(:location.x, :location.y), 25832)), :description, now(), now())
			on conflict (station_id) do update
				set old_station_number = :oldStationNumber, 
				name = :name, 
				station_owner_name = :stationOwnerName, 
				location = (ST_SetSRID(ST_MakePoint(:location.x, :location.y), 25832)), 
				description = :description, 
				updated = now() 
			""")
	void addStations(@BindBean List<Station> stations);
}
