package dk.dataforsyningen.vanda_hydrometry_data.dao;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import dk.dataforsyningen.vanda_hydrometry_data.components.LogSqlFactory;
import dk.dataforsyningen.vanda_hydrometry_data.model.Measurement;

@RegisterRowMapper(MeasurementMapper.class)
@LogSqlFactory
public interface MeasurementDao {

	@SqlQuery("""
			select 
				station_id,
				measurement_point_number, 
				measurement_date_time,
				measurement_type_id,
				result,
				is_current,
				created
			from measurement
			""")
	List<Measurement> getAllMeasurements();
	
	@SqlQuery("""
			select
				station_id,
				measurement_point_number, 
				measurement_date_time,
				measurement_type_id,
				result,
				is_current,
				created 
			from measurement 
			where 
				station_id = :stationId
				and measurement_type_id = :measurementTypeId
				and measurement_point_number = :measurementPointNumber
				and measurement_date_time = :measurementDateTime
				and is_current = true
			""")
	Measurement findCurrentMeasurement(@Bind String stationId,
			@Bind int measurementPointNumber,
			@Bind String measurementTypeId,
			@Bind OffsetDateTime measurementDateTime
			);
	
	/**
	 * Add measurement if it does not exists
	 * 
	 * @param measurement
	 */
	@SqlQuery("""
			insert into measurement (station_id, measurement_date_time, measurement_point_number, measurement_type_id, result, is_current, created)
			(select :stationId, :measurementDateTime, :measurementPointNumber, :measurementTypeId, :result, :isCurrent, now()
			where not exists (
				select 1 from measurement where
					station_id = :stationId and
					measurement_date_time = :measurementDateTime and 
					measurement_point_number = :measurementPointNumber and
					measurement_type_id = :measurementTypeId and
					is_current = :isCurrent
			))
			returning *  
			""")
	Measurement addMeasurement(@BindBean Measurement measurement);
	
	/**
	 * Update measurement result if it exists
	 * 
	 * @param measurement
	 */
	@SqlUpdate("""
			update measurement set result = :result
			where
				station_id = :stationId and
				measurement_date_time = :measurementDateTime and 
				measurement_point_number = :measurementPointNumber and
				measurement_type_id = :measurementTypeId and
				result != :result and
				is_current = :isCurrent
			""")
	void updateMeasurement(@BindBean Measurement measurement);
	
	@SqlBatch("""
			insert into measurement (station_id, measurement_date_time, measurement_point_number, measurement_type_id, result, is_current, created)
			select :stationId, :measurementDateTime, :measurementPointNumber, :measurementTypeId, :result, :isCurrent, now()
			where not exists (
				select 1 from measurement where
					station_id = :stationId and
					measurement_date_time = :measurementDateTime and 
					measurement_point_number = :measurementPointNumber and
					measurement_type_id = :measurementTypeId and
					is_current = :isCurrent
			)
			""")
	void addMeasurements(@BindBean List<Measurement> measurements);
	
	@SqlBatch("""
			update measurement set result = :result
			where
				station_id = :stationId and
				measurement_date_time = :measurementDateTime and 
				measurement_point_number = :measurementPointNumber and
				measurement_type_id = :measurementTypeId and
				result != :result and
				is_current = :isCurrent
			""")
	void updateMeasurements(@BindBean List<Measurement> measurements);
	
	@SqlUpdate("""
			delete from measurement where
				station_id = :stationId and
				measurement_date_time = :measurementDateTime and 
				measurement_point_number = :measurementPointNumber and
				measurement_type_id = :measurementTypeId and
				is_current = true
			""")
	void deleteMeasurement(@Bind String stationId, @Bind int measurementPointNumber,
			@Bind String measurementTypeId,
			@Bind OffsetDateTime measurementDateTime
			);
	
	@SqlQuery("select count(*) from measurement")
	int count();
	
}
