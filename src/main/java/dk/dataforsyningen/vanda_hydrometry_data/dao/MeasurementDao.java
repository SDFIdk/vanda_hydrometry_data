package dk.dataforsyningen.vanda_hydrometry_data.dao;

import java.time.OffsetDateTime;
import java.util.List;

import dk.dataforsyningen.vanda_hydrometry_data.mapper.MeasurementMapper;
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
				vanda_event_timestamp,
				examination_type_sc,
				result,
				result_elevation_corrected,
				is_current,
				created
			from hydrometry.measurement
			where
				station_id = :stationId
				and examination_type_sc = :examinationTypeSc
				and measurement_point_number = :measurementPointNumber
				and measurement_date_time = :measurementDateTime
			order by created
			""")
	List<Measurement> readMeasurementHistory(@Bind String stationId,
			@Bind int measurementPointNumber,
			@Bind int examinationTypeSc,
			@Bind OffsetDateTime measurementDateTime
			);
	
	@SqlQuery("""
			select
				station_id,
				measurement_point_number,
				measurement_date_time,
				vanda_event_timestamp,
				examination_type_sc,
				result,
				result_elevation_corrected,
				is_current,
				created
			from hydrometry.measurement
			where
				station_id = :stationId
				and examination_type_sc = :examinationTypeSc
				and measurement_point_number = :measurementPointNumber
				and measurement_date_time = :measurementDateTime
				and is_current = true
			""")
	Measurement readCurrentMeasurement(@Bind String stationId,
			@Bind int measurementPointNumber,
			@Bind int examinationTypeSc,
			@Bind OffsetDateTime measurementDateTime
			);
	
	/**
	 * Add a new record for the given measurement
	 * 
	 * @param measurement
	 */
	@SqlQuery("""
			insert into hydrometry.measurement (station_id, measurement_date_time, vanda_event_timestamp, measurement_point_number, examination_type_sc, result, result_elevation_corrected, is_current, created)
			values (:stationId, :measurementDateTime, :vandaEventTimestamp, :measurementPointNumber, :examinationTypeSc, :result, :resultElevationCorrected, :isCurrent, now())
			returning *
			""")
	Measurement insertMeasurement(@BindBean Measurement measurement);
	
	@SqlBatch("""
			insert into hydrometry.measurement (station_id, measurement_date_time, vanda_event_timestamp, measurement_point_number, examination_type_sc, result, result_elevation_corrected, is_current, created)
			values (:stationId, :measurementDateTime, :vandaEventTimestamp, :measurementPointNumber, :examinationTypeSc, :result, :resultElevationCorrected, :isCurrent, now())
			""")
	void insertMeasurements(@BindBean List<Measurement> measurements);

	/**
	 * Update measurement result if it exists
	 * 
	 * @param measurement
	 */
/*	@SqlUpdate("""
			update hydrometry.measurement set result = :result, result_elevation_corrected = :resultElevationCorrected, updated = now()
			where
				station_id = :stationId
				and measurement_date_time = :measurementDateTime
				and measurement_point_number = :measurementPointNumber
				and examination_type_sc = :examinationTypeSc
				and is_current = :isCurrent
			""")
	void updateMeasurement(@BindBean Measurement measurement);
		
	@SqlBatch("""
			update hydrometry.measurement set result = :result, result_elevation_corrected = :resultElevationCorrected, updated = now()
			where
				station_id = :stationId
				and measurement_date_time = :measurementDateTime
				and measurement_point_number = :measurementPointNumber
				and examination_type_sc = :examinationTypeSc
				and is_current = :isCurrent
			""")
	void updateMeasurements(@BindBean List<Measurement> measurements);*/
	
	/**
	 * Set is_current to false on all records from the given measurement 
	 * (all records in the given measurement's history)
	 * 
	 * @param measurement
	 */
	@SqlUpdate("""
			update hydrometry.measurement set is_current = false
			where
				station_id = :stationId
				and measurement_date_time = :measurementDateTime
				and measurement_point_number = :measurementPointNumber
				and examination_type_sc = :examinationTypeSc
			""")
	int inactivateMeasurementHistory(@BindBean Measurement measurement);
	
	/**
	 * Set is_current to false on all records from the given measurements list 
	 * (all records in the measurements' history)
	 * 
	 * @param list of measurements
	 */
	@SqlBatch("""
			update hydrometry.measurement set is_current = false
			where
				station_id = :stationId
				and measurement_date_time = :measurementDateTime
				and measurement_point_number = :measurementPointNumber
				and examination_type_sc = :examinationTypeSc
			""")
	void inactivateMeasurementsHistory(@BindBean List<Measurement> measurements);
	
	/**
	 * Deletes the matching measurement and all its history
	 * 
	 * @param stationId
	 * @param measurementPointNumber
	 * @param examinationTypeSc
	 * @param measurementDateTime
	 */
	@SqlUpdate("""
			delete from hydrometry.measurement
			where
				station_id = :stationId
				and measurement_date_time = :measurementDateTime
				and measurement_point_number = :measurementPointNumber
				and examination_type_sc = :examinationTypeSc
			""")
	void deleteMeasurementWithHistory(@Bind String stationId, @Bind int measurementPointNumber,
			@Bind int examinationTypeSc,
			@Bind OffsetDateTime measurementDateTime
			);
	
	/**
	 * Deletes all measurements related to the given station
	 * 
	 * @param stationId
	 */
	@SqlUpdate("""
			delete
			from hydrometry.measurement
			where
				station_id = :stationId
			""")
	void deleteMeasurementsForStation(@Bind String stationId);
	
	/**
	 * Counts the number of records in the given measurement's history
	 * 
	 * @param stationId
	 * @param measurementPointNumber
	 * @param examinationTypeSc
	 * @param measurementDateTime
	 * @return number of records
	 */
	@SqlQuery("""
			select count(*) from hydrometry.measurement
			where
				station_id = :stationId
				and measurement_date_time = :measurementDateTime
				and measurement_point_number = :measurementPointNumber
				and examination_type_sc = :examinationTypeSc
			""")
	int countHistory(@Bind String stationId, @Bind int measurementPointNumber,
			@Bind int examinationTypeSc,
			@Bind OffsetDateTime measurementDateTime);
	
	/**
	 * Counts all measurements from the DB
	 * 
	 * @return number of records
	 */
	@SqlQuery("select count(*) from hydrometry.measurement")
	int countAll();
	
}
