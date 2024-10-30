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

	@SqlQuery("""
			select
				examination_type_sc,
				examination_type,
				parameter_sc,
				parameter,
				unit_sc,
				unit
			from hydrometry.measurement_type
			""")
	List<MeasurementType> getAllMeasurementTypes();
	
	@SqlQuery("""
			select
				examination_type_sc,
				examination_type,
				parameter_sc,
				parameter,
				unit_sc,
				unit
			from hydrometry.measurement_type
			where examination_type_sc = :examinationTypeSc
			""")
	MeasurementType findMeasurementTypeById(@Bind int examinationTypeSc);
	
	@SqlUpdate("""
			insert into hydrometry.measurement_type
			(parameter_sc, parameter, examination_type_sc, examination_type, unit_sc, unit)
			values (:parameterSc, :parameter, :examinationTypeSc, :examinationType, :unitSc, :unit)
			on conflict (examination_type_sc) do update
				set parameter = EXCLUDED.parameter,
					examination_type = EXCLUDED.examination_type,
					unit = EXCLUDED.unit
			""")
	@GetGeneratedKeys
	String addMeasurementType(@BindBean MeasurementType measurementType);
	
	@SqlBatch("""
			insert into hydrometry.measurement_type
			(parameter_sc, parameter, examination_type_sc, examination_type, unit_sc, unit)
			values (:parameterSc, :parameter, :examinationTypeSc, :examinationType, :unitSc, :unit)
			on conflict (examination_type_sc) do update
				set parameter = EXCLUDED.parameter,
					examination_type = EXCLUDED.examination_type,
					unit = EXCLUDED.unit
			""")
	@GetGeneratedKeys
	List<String> addMeasurementTypes(@BindBean List<MeasurementType> measurementTypes);
	
	@SqlUpdate("delete from hydrometry.measurement_type where examination_type_sc = :examinationTypeSc")
	void deleteMeasurementType(@Bind int examinationTypeSc);
	
	@SqlQuery("select count(*) from hydrometry.measurement_type")
	int count();
}
