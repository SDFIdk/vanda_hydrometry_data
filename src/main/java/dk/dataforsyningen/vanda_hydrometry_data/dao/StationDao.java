package dk.dataforsyningen.vanda_hydrometry_data.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import dk.dataforsyningen.vanda_hydrometry_data.components.LogSqlFactory;
import dk.dataforsyningen.vanda_hydrometry_data.model.MeasurementType;
import dk.dataforsyningen.vanda_hydrometry_data.model.Station;

@RegisterRowMapper(StationMapper.class)
@LogSqlFactory
public interface StationDao {
	
	@SqlQuery("""
			select
				s.station_id,
				s.name,
				s.old_station_number,
				s.station_owner_name,
				s.location,
				ST_X(location) as location_x,
				ST_Y(location) as location_y,
				ST_SRID(location) as location_srid,
				s.description,
				s.created,
				s.updated,
				mt.parameter_sc,
				mt.parameter,
				mt.examination_type_sc,
				mt.examination_type,
				mt.unit_sc,
				mt.unit
			from hydrometry.station s left join hydrometry.station_measurement_type smt 
				on s.station_id = smt.station_id
				left join hydrometry.measurement_type mt
				on smt.examination_type_sc = mt.examination_type_sc
			""")
	List<Station> getAllStations();
	
	@SqlQuery("""
			select
				s.station_id,
				s.name,
				s.old_station_number,
				s.station_owner_name,
				s.location,
				ST_X(location) as location_x,
				ST_Y(location) as location_y,
				ST_SRID(location) as location_srid,
				s.description,
				s.created,
				s.updated,
				mt.parameter_sc,
				mt.parameter,
				mt.examination_type_sc,
				mt.examination_type,
				mt.unit_sc,
				mt.unit
			from hydrometry.station s left join hydrometry.station_measurement_type smt 
				on s.station_id = smt.station_id
				left join hydrometry.measurement_type mt
				on smt.examination_type_sc = mt.examination_type_sc 
			where s.station_id = :stationId
			""")
	List<Station> findStationByStationId(@Bind String stationId);
	
	@SqlQuery("""
			select
				s.station_id,
				s.name,
				s.old_station_number,
				s.station_owner_name,
				s.location,
				ST_X(location) as location_x,
				ST_Y(location) as location_y,
				ST_SRID(location) as location_srid,
				s.description,
				s.created,
				s.updated,
				mt.parameter_sc,
				mt.parameter,
				mt.examination_type_sc,
				mt.examination_type,
				mt.unit_sc,
				mt.unit
			from hydrometry.station s left join hydrometry.station_measurement_type smt 
				on s.station_id = smt.station_id
				left join hydrometry.measurement_type mt
				on smt.examination_type_sc = mt.examination_type_sc 
			where mt.examination_type_sc = :examinationTypeSc
			""")
	List<Station> findStationByExaminationTypeSc(@Bind int examinationTypeSc);
	
	@SqlQuery("""
			select count(*)
			from hydrometry.station_measurement_type smt 
			where smt.station_id = :stationId 
				and smt.examination_type_sc = :examinationTypeSc
			""")
	int isExaminationTypeScSupported(@Bind String stationId, @Bind int examinationTypeSc);

	/**
	 * Add (only) station if not exists
	 * @param station
	 */
	@SqlUpdate("""
			insert into hydrometry.station
			(station_id, old_station_number, name, station_owner_name, location, description, created, updated)
			values ( :stationId, :oldStationNumber, :name, :stationOwnerName, (ST_SetSRID(ST_MakePoint(:location.x, :location.y), :location.srid::int)), :description, now(), now())
			on conflict (station_id) do update
				set old_station_number = :oldStationNumber,
				name = :name,
				station_owner_name = :stationOwnerName,
				location = (ST_SetSRID(ST_MakePoint(:location.x, :location.y), :location.srid::int)),
				description = :description,
				updated = now()
			""")
	void addStation(@BindBean Station station);
	
	@SqlBatch("""
			insert into hydrometry.station
			(station_id, old_station_number, name, station_owner_name, location, description, created, updated)
			values ( :stationId, :oldStationNumber, :name, :stationOwnerName, (ST_SetSRID(ST_MakePoint(:location.x, :location.y), :location.srid::int)), :description, now(), now())
			on conflict (station_id) do update
				set old_station_number = :oldStationNumber,
				name = :name,
				station_owner_name = :stationOwnerName,
				location = (ST_SetSRID(ST_MakePoint(:location.x, :location.y), :location.srid::int)),
				description = :description,
				updated = now()
			""")
	void addStations(@BindBean List<Station> stations);
	
	@SqlUpdate("""
			insert into hydrometry.station_measurement_type (station_id, examination_type_sc)
			values (:stationId, :examinationTypeSc)
			on conflict do nothing
			""")
	void addStationMeasurementTypeRelation(@Bind String stationId, @Bind String examinationTypeSc);
	
	@SqlBatch("""
			insert into hydrometry.station_measurement_type (station_id, examination_type_sc)
			values (:stationId, :examinationTypeSc)
			on conflict do nothing
			""")
	void addStationMeasurementTypeRelations(@Bind List<String> stationId, @BindBean List<MeasurementType> examinationTypeSc);
	
	@SqlUpdate("delete from hydrometry.station where station_id = :id")
	void deleteStation(@Bind String id);
	
	@SqlUpdate("delete from hydrometry.station_measurement_type where station_id = :id")
	void deleteRelationToMeasurementTypeByStationId(@Bind String id);
	
	@SqlQuery("select count(*) from hydrometry.station")
	int count();
	
}
