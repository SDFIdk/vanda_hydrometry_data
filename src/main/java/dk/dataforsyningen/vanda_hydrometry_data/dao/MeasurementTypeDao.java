package dk.dataforsyningen.vanda_hydrometry_data.dao;

import dk.dataforsyningen.vanda_hydrometry_data.components.LogSqlFactory;
import dk.dataforsyningen.vanda_hydrometry_data.mapper.MeasurementTypeMapper;
import dk.dataforsyningen.vanda_hydrometry_data.model.MeasurementType;
import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@LogSqlFactory
public interface MeasurementTypeDao {

  /**
   * Reads all measurement types
   *
   * @return list of measurement types
   */
  @SqlQuery("""
      select
      	examination_type_sc,
      	examination_type,
      	parameter_sc,
      	parameter,
      	unit_sc,
      	unit
      from vanda.measurement_type
      """)
  @RegisterRowMapper(MeasurementTypeMapper.class)
  List<MeasurementType> readAllMeasurementTypes();

  /**
   * Read measurement type with the given examination type Sc
   *
   * @param examinationTypeSc
   * @return measurement type
   */
  @SqlQuery("""
      select
      	examination_type_sc,
      	examination_type,
      	parameter_sc,
      	parameter,
      	unit_sc,
      	unit
      from vanda.measurement_type
      where examination_type_sc = :examinationTypeSc
      """)
  @RegisterRowMapper(MeasurementTypeMapper.class)
  MeasurementType readMeasurementTypeByExaminationType(@Bind int examinationTypeSc);

  /**
   * Insert (or update if exists) the given measurement type
   *
   * @param measurementType
   */
  @SqlUpdate("""
      insert into vanda.measurement_type
      (parameter_sc, parameter, examination_type_sc, examination_type, unit_sc, unit)
      values (:parameterSc, :parameter, :examinationTypeSc, :examinationType, :unitSc, :unit)
      on conflict (examination_type_sc) do update
      	set parameter = EXCLUDED.parameter,
      		examination_type = EXCLUDED.examination_type,
      		unit = EXCLUDED.unit
      """)
  void insertMeasurementType(@BindBean MeasurementType measurementType);

  /**
   * Inserts (or updates if exists) the measurement types from the given list
   *
   * @param list of measurementTypes
   */
  @SqlBatch("""
      insert into vanda.measurement_type
      (parameter_sc, parameter, examination_type_sc, examination_type, unit_sc, unit)
      values (:parameterSc, :parameter, :examinationTypeSc, :examinationType, :unitSc, :unit)
      on conflict (examination_type_sc) do update
      	set parameter = EXCLUDED.parameter,
      		examination_type = EXCLUDED.examination_type,
      		unit = EXCLUDED.unit
      """)
  void insertMeasurementTypes(@BindBean List<MeasurementType> measurementTypes);

  /**
   * Deletes the measurement type with the given examination type sc
   *
   * @param examinationTypeSc
   */
  @SqlUpdate("delete from vanda.measurement_type where examination_type_sc = :examinationTypeSc")
  void deleteMeasurementType(@Bind int examinationTypeSc);

  /**
   * Counts all measurement types from the db
   *
   * @return the number of records
   */
  @SqlQuery("select count(*) from vanda.measurement_type")
  int count();
}
