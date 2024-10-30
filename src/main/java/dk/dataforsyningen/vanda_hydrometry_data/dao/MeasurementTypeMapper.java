package dk.dataforsyningen.vanda_hydrometry_data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import dk.dataforsyningen.vanda_hydrometry_data.model.MeasurementType;

public class MeasurementTypeMapper implements RowMapper<MeasurementType> {

	@Override
	public MeasurementType map(ResultSet rs, StatementContext ctx) throws SQLException {
		
		MeasurementType mt = new MeasurementType();
		
		mt.setParameterSc(rs.getInt("parameter_sc"));
		mt.setParameter(nullifyEmpty(rs.getString("parameter")));
		mt.setExaminationTypeSc(rs.getInt("examination_type_sc"));
		mt.setExaminationType(nullifyEmpty(rs.getString("examination_type")));
		mt.setUnitSc(rs.getInt("unit_sc"));
		mt.setUnit(nullifyEmpty(rs.getString("unit")));
		
		return mt;
	}

	private String nullifyEmpty(String s) {
		return (s == null || s.isEmpty() ? null : s);
	}
}
