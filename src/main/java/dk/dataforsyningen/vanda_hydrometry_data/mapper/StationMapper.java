package dk.dataforsyningen.vanda_hydrometry_data.mapper;

import dk.dataforsyningen.vanda_hydrometry_data.model.MeasurementType;
import dk.dataforsyningen.vanda_hydrometry_data.model.Station;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class StationMapper implements RowMapper<Station> {

  @Override
  public Station map(ResultSet rs, StatementContext ctx) throws SQLException {
    Station station = new Station();

    station.setStationId(rs.getString("station_id"));
    station.setStationIdSav(rs.getString("station_id_sav"));
    station.setName(rs.getString("name"));
    station.setStationOwnerName(rs.getString("station_owner_name"));
    station.setLocationX((Double) rs.getObject("location_x"));
    station.setLocationY((Double) rs.getObject("location_y"));
    station.setLocationSrid((Integer) rs.getObject("location_srid"));
    station.setLocationType(rs.getString("location_type"));
    station.setDescription(rs.getString("description"));
    station.setCreated(rs.getObject("created", OffsetDateTime.class));
    station.setUpdated(rs.getObject("updated", OffsetDateTime.class));

    if (rs.getObject("examination_type_sc") != null) {
      MeasurementType mt = new MeasurementType();

      mt.setParameterSc(rs.getInt("parameter_sc"));
      mt.setParameter(rs.getString("parameter"));
      mt.setExaminationTypeSc(rs.getInt("examination_type_sc"));
      mt.setExaminationType(rs.getString("examination_type"));
      mt.setUnitSc(rs.getInt("unit_sc"));
      mt.setUnit(rs.getString("unit"));

      station.getMeasurementTypes().add(mt);
    }

    return station;
  }
}
