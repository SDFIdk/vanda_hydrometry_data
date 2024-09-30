package dk.dataforsyningen.vanda_hydrometry_data.dao;

import java.sql.Date;
import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import dk.dataforsyningen.vanda_hydrometry_data.model.Measurement;

@RegisterRowMapper(MeasurementMapper.class)
public interface MeasurementDao {

	@SqlQuery("select * from measurement")
	List<Measurement> readAllMeasurement();
	
	@SqlQuery("""
			select * from measurement where 
			station_id = :stationId
			and measurement_date_time = :measurementDateTime
			and measurement_type_id = :measurementTypeId
			and measurement_point_number = :measurementPointNumber
			and result = :result
			""")
	Measurement findMeasurement(@Bind String stationId,
			@Bind Date measurementDatetime,
			@Bind int measurement_type_id,
			@Bind int measurementPointNumber,
			@Bind double result);
	
	/**
	 * Add measurement if it does not exists
	 * 
	 * @param measurement
	 */
	@SqlUpdate("""
			insert into measurements (station_id, measurement_date_time, measurement_point_number, measurement_type_id, result, is_current, created)
			values (:stationId, :measurementDateTime, :measurementPointNumber, :measurementTypeId, :result, :isCurrent, :created)
			on conflict on constraint (measurement_station_IDX) do nothing
			""")
	void addMeasurement(@BindBean Measurement measurement);
	
	@SqlBatch("addMeasurement")
	void addMeasurements(@BindBean List<Measurement> measurements);
}
