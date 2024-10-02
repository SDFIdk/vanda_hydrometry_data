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
	
	@SqlQuery("""
			select
				station_id,
				name,
				old_station_number,
				station_owner_name,
				location,
				ST_AsText(location) as location_as_text,
				description,
				created,
				updated
			from station
			""")
	List<Station> getAllStations();
	
	@SqlQuery("""
			select
				station_id,
				name,
				old_station_number,
				station_owner_name,
				location,
				ST_AsText(location) as location_as_text,
				description,
				created,
				updated
			from station 
			where station_id = :stationId 
			""")
	Station findStationByStationId(@Bind String stationId);

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
	
	@SqlUpdate("delete from station where station_id = :id")
	void deleteStation(@Bind String id);
	
	@SqlQuery("select count(*) from station")
	int count();
	
}
