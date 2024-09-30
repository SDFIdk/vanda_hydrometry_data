package dk.dataforsyningen.vanda_hydrometry_data.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import dk.dataforsyningen.vanda_hydrometry_data.components.LogSqlFactory;
import dk.dataforsyningen.vanda_hydrometry_data.model.MeasurementType;

@RegisterRowMapper(MeasurementTypeMapper.class)
@LogSqlFactory
public interface MeasurementTypeDao {

	@SqlQuery("select * from measurement_type")
	List<MeasurementType> readAllMeasurementTypes();
	
	@SqlQuery("select * from measurement_type where measurement_type_id = :measurementTypeId")
	MeasurementType findMeasurementType(@Bind int measurementTypeId);
	
	@SqlUpdate("""
			insert into measurement_type
			(measurement_type_id, parameter_sc, parameter, examination_type_sc, examination_type, unit_sc, unit)
			values (:measurementTypeId, :parameterSc, :parameter, :examinationTypeSc, :examinationType, :unitSc, :unit)
			on conflict (measurement_type_id) do nothing
			""")
	@GetGeneratedKeys
	Integer addMeasurementType(@BindBean MeasurementType measurementType);
	
	@SqlBatch("addMeasurementType")
	@GetGeneratedKeys
	List<Integer> addMeasurementTypes(@BindBean List<MeasurementType> measurementTypes);
}
