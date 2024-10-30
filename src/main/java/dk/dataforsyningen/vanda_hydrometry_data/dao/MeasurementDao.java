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
				created,
				updated
			from hydrometry.measurement
			""")
	List<Measurement> getAllMeasurements();
	
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
				created,
				updated
			from hydrometry.measurement
			where
				station_id = :stationId
				and examination_type_sc = :examinationTypeSc
				and measurement_point_number = :measurementPointNumber
				and measurement_date_time = :measurementDateTime
				and is_current = true
			""")
	Measurement findCurrentMeasurement(@Bind String stationId,
			@Bind int measurementPointNumber,
			@Bind int examinationTypeSc,
			@Bind OffsetDateTime measurementDateTime
			);
	
	/**
	 * Add measurement if it does not exists
	 * 
	 * @param measurement
	 */
	@SqlQuery("""
			insert into hydrometry.measurement (station_id, measurement_date_time, vanda_event_timestamp, measurement_point_number, examination_type_sc, result, result_elevation_corrected, is_current, created, updated)
			(select :stationId, :measurementDateTime, :vandaEventTimestamp, :measurementPointNumber, :examinationTypeSc, :result, :resultElevationCorrected, :isCurrent, now(), now()
			where not exists (
				select 1 from hydrometry.measurement where
					station_id = :stationId
					and measurement_date_time = :measurementDateTime
					and measurement_point_number = :measurementPointNumber
					and examination_type_sc = :examinationTypeSc
					and is_current = :isCurrent
			))
			returning *
			""")
	Measurement addMeasurement(@BindBean Measurement measurement);
	
	@SqlBatch("""
			insert into hydrometry.measurement (station_id, measurement_date_time, vanda_event_timestamp, measurement_point_number, examination_type_sc, result, result_elevation_corrected, is_current, created, updated)
			(select :stationId, :measurementDateTime, :vandaEventTimestamp, :measurementPointNumber, :examinationTypeSc, :result, :resultElevationCorrected, :isCurrent, now(), now()
			where not exists (
				select 1 from hydrometry.measurement where
					station_id = :stationId
					and measurement_date_time = :measurementDateTime
					and measurement_point_number = :measurementPointNumber
					and examination_type_sc = :examinationTypeSc
					and is_current = :isCurrent
			))
			""")
	void addMeasurements(@BindBean List<Measurement> measurements);

	/**
	 * Update measurement result if it exists
	 * 
	 * @param measurement
	 */
	@SqlUpdate("""
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
	void updateMeasurements(@BindBean List<Measurement> measurements);
	
	@SqlUpdate("""
			delete
			from hydrometry.measurement
			where
				station_id = :stationId
				and measurement_date_time = :measurementDateTime
				and measurement_point_number = :measurementPointNumber
				and examination_type_sc = :examinationTypeSc
				and is_current = true
			""")
	void deleteMeasurement(@Bind String stationId, @Bind int measurementPointNumber,
			@Bind int examinationTypeSc,
			@Bind OffsetDateTime measurementDateTime
			);
	
	@SqlQuery("select count(*) from hydrometry.measurement")
	int count();
	
}
