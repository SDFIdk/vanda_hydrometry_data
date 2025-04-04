CREATE EXTENSION IF NOT EXISTS postgis;
CREATE SCHEMA IF NOT EXISTS vanda;
SET search_path TO vanda, public;

CREATE TABLE station
(
 station_id         char(8) NOT NULL,
 station_id_sav varchar(100) NULL,
 name               varchar(150) NULL,
 station_owner_name varchar(150) NULL,
 geometry           geometry(POINT,25832) NOT NULL,
 location_type      varchar(100) NULL,
 description        varchar(1000) NULL,
 created            timestamp(3) with time zone NOT NULL,
 updated            timestamp(3) with time zone NOT NULL,
 CONSTRAINT PK_1 PRIMARY KEY ( station_id )
);
CREATE INDEX station_IDX_geometry ON station USING GIST (
  geometry
);

COMMENT ON TABLE station IS 'Contains information about the station';
COMMENT ON COLUMN station.station_id IS 'ID of the station, 8 characters';
COMMENT ON COLUMN station.station_id_sav IS 'ID of the station from SAV (old station number)';
COMMENT ON COLUMN station.name IS 'Name of the station';
COMMENT ON COLUMN station.station_owner_name IS 'Name of the station owner';
COMMENT ON COLUMN station.geometry IS 'The geometry of the station';
COMMENT ON COLUMN station.description IS 'Description of the station that can contain catchment information, for example "Opland = 426,7 km2"';

CREATE TABLE measurement_type
(
 examination_type_sc      int NOT NULL,
 examination_type         varchar(100) NOT NULL,
 "parameter"              varchar(100) NOT NULL,
 parameter_sc             int NOT NULL,
 unit                     varchar(20) NOT NULL,
 unit_sc                  int NOT NULL,
 CONSTRAINT PK_3 PRIMARY KEY ( examination_type_sc )
);

COMMENT ON TABLE measurement_type IS 'Contains information about the measurement types like Water Level, Stream Discharge, etc.';
COMMENT ON COLUMN measurement_type.examination_type_sc IS 'The stancode of the examination type and the key in the table';
COMMENT ON COLUMN measurement_type.examination_type IS 'The name of the examination type, for example "Vandføring" or "Vandstand"';
COMMENT ON COLUMN measurement_type.parameter IS 'The parameter of the measurement, for example "Vandføring" or "Vandstand"';
COMMENT ON COLUMN measurement_type.parameter_sc IS 'The stancode of the parameter';
COMMENT ON COLUMN measurement_type.unit IS 'The unit of the measurement, for example "cm, m³/s"';
COMMENT ON COLUMN measurement_type.unit_sc IS 'The stancode of the unit';


CREATE TABLE station_measurement_type
(
    station_id 			char(8) NOT NULL,
    examination_type_sc	int NOT NULL,
    CONSTRAINT PK_2 PRIMARY KEY ( station_id, examination_type_sc ),
    CONSTRAINT FK_5 FOREIGN KEY ( examination_type_sc ) REFERENCES measurement_type ( examination_type_sc ),
    CONSTRAINT FK_6 FOREIGN KEY ( station_id ) REFERENCES station ( station_id )
);

CREATE INDEX ON station_measurement_type
(
     examination_type_sc
);
CREATE INDEX ON station_measurement_type
(
     station_id
);


CREATE TABLE measurement
(
 "value"              double precision NULL,
 value_elevation_corrected double precision NULL,
 measurement_date_time timestamp(3) with time zone NOT NULL,
 is_current            bool NOT NULL,
 created               timestamp(3) with time zone NOT NULL,
 vanda_event_timestamp timestamp(3) with time zone NULL,
 station_id            char(8) NOT NULL,
 examination_type_sc      int NOT NULL,
 measurement_point_number int NOT NULL,
 CONSTRAINT FK_1 FOREIGN KEY ( examination_type_sc ) REFERENCES measurement_type ( examination_type_sc ),
 CONSTRAINT FK_2 FOREIGN KEY ( station_id ) REFERENCES station ( station_id )
);

CREATE INDEX FK_1_M ON measurement
(
 examination_type_sc
);
CREATE INDEX FK_2_M ON measurement
(
 station_id
);
CREATE INDEX measurement_station_IDX ON measurement
(
  station_id,
  examination_type_sc,
  is_current,
  measurement_date_time
);

CREATE INDEX ON measurement
(
 station_id,
 examination_type_sc,
 is_current,
 measurement_date_time,
 measurement_point_number
);

COMMENT ON TABLE measurement IS 'Contains the measurements of the stations';
COMMENT ON COLUMN measurement.value IS 'The value of the measurement, for example 4203';
COMMENT ON COLUMN measurement.measurement_date_time IS 'The measurement date time';
COMMENT ON COLUMN measurement.vanda_event_timestamp IS 'The timestamp when the record was created in the source system (vanda)';
COMMENT ON COLUMN measurement.is_current IS 'Is the current value, ie. the latest value';

CREATE TABLE calculated
(
 slice                char NOT NULL,
 date_start           date NOT NULL,
 mean                 real NOT NULL,
 created              timestamp(3) with time zone NOT NULL,
 updated              timestamp(3) with time zone NOT NULL,
 station_id           char(8) NOT NULL,
 examination_type_sc  int NOT NULL,
 CONSTRAINT FK_3 FOREIGN KEY ( station_id ) REFERENCES station ( station_id ),
 CONSTRAINT FK_4 FOREIGN KEY ( examination_type_sc ) REFERENCES measurement_type ( examination_type_sc )
);

CREATE INDEX FK_1_C ON calculated
(
 station_id
);
CREATE INDEX FK_2_C ON calculated
(
 examination_type_sc
);
-- unique index
CREATE UNIQUE INDEX calculated_IDX ON calculated
(
 slice,
 station_id,
 date_start,
 examination_type_sc
);

COMMENT ON TABLE calculated IS 'Contains the calculated measurements of the stations';
COMMENT ON COLUMN calculated.slice IS 'The slice type (size): Y(ear), S(eason), M(onth) or D(ay)';
COMMENT ON COLUMN calculated.date_start IS 'The start date of the slice';
COMMENT ON COLUMN calculated.mean IS 'The mean of the measurements in the slice';

CREATE TABLE vanda.calculated_daily (
  date date,
  mean double precision
);

CREATE OR REPLACE FUNCTION vanda.get_daily_means(
    p_station_id char(8),
    p_examination_type_sc int,
    p_start_date date,
    p_end_date date
)
RETURNS SETOF vanda.calculated_daily
STABLE
LANGUAGE plpgsql
AS $$
BEGIN
  RETURN QUERY
  SELECT
      DATE(measurement_date_time) as date,
      AVG(value) as daily_mean
  FROM vanda.measurement
  WHERE station_id = p_station_id
    AND is_current = true
    AND examination_type_sc = p_examination_type_sc
    AND measurement_date_time BETWEEN p_start_date AND p_end_date
  GROUP BY DATE(measurement_date_time)
  ORDER BY DATE(measurement_date_time);
END;
$$;

CREATE TABLE vanda.calculated_monthly (
  year int,
  month int,
  mean double precision
);

CREATE OR REPLACE FUNCTION vanda.get_monthly_means(
  p_station_id char(8),
  p_examination_type_sc int,
  p_start_date date,
  p_end_date date
)
RETURNS SETOF vanda.calculated_monthly
STABLE
LANGUAGE plpgsql
AS $$
BEGIN
  RETURN QUERY
  SELECT
      EXTRACT(YEAR FROM measurement_date_time)::int as year,
      EXTRACT(MONTH FROM measurement_date_time)::int as month,
      AVG(value) as monthly_mean
  FROM vanda.measurement
  WHERE station_id = p_station_id
    AND is_current = true
    AND examination_type_sc = p_examination_type_sc
    AND measurement_date_time BETWEEN p_start_date AND p_end_date
  GROUP BY EXTRACT(YEAR FROM measurement_date_time), EXTRACT(MONTH FROM measurement_date_time)
  ORDER BY year, month;
END;
$$;

CREATE TABLE vanda.calculated_yearly (
  year int,
  mean double precision
);

CREATE OR REPLACE FUNCTION vanda.get_yearly_means(
  p_station_id char(8),
  p_examination_type_sc int,
  p_start_date date,
  p_end_date date
)
RETURNS SETOF vanda.calculated_yearly
STABLE
LANGUAGE plpgsql
AS $$
BEGIN
  RETURN QUERY
  SELECT
      EXTRACT(YEAR FROM measurement_date_time)::int as year,
      AVG(value) as mean
  FROM vanda.measurement
  WHERE station_id = p_station_id
    AND is_current = true
    AND examination_type_sc = p_examination_type_sc
    AND measurement_date_time BETWEEN p_start_date AND p_end_date
  GROUP BY EXTRACT(YEAR FROM measurement_date_time)
  ORDER BY year;
END;
$$;

CREATE TABLE vanda.calculated_seasonal (
  season_year int,
  season text,
  mean double precision
);

CREATE OR REPLACE FUNCTION vanda.get_seasonal_means(
  p_station_id char(8),
  p_examination_type_sc int,
  p_start_date date,
  p_end_date date,
  p_season text DEFAULT NULL
)
RETURNS SETOF vanda.calculated_seasonal
STABLE
LANGUAGE plpgsql
AS $$
BEGIN
  RETURN QUERY
  WITH seasons AS (
    SELECT *,
          CASE
              WHEN EXTRACT(MONTH FROM measurement_date_time) IN (12, 1, 2) THEN 'winter'
              WHEN EXTRACT(MONTH FROM measurement_date_time) IN (3, 4, 5) THEN 'spring'
              WHEN EXTRACT(MONTH FROM measurement_date_time) IN (6, 7, 8) THEN 'summer'
              WHEN EXTRACT(MONTH FROM measurement_date_time) IN (9, 10, 11) THEN 'autumn'
          END AS season,
          CASE
              WHEN EXTRACT(MONTH FROM measurement_date_time) = 12 THEN EXTRACT(YEAR FROM measurement_date_time) + 1
              ELSE EXTRACT(YEAR FROM measurement_date_time)
          END AS season_year
    FROM vanda.measurement
    WHERE station_id = p_station_id
      AND is_current = true
      AND examination_type_sc = p_examination_type_sc
      AND measurement_date_time BETWEEN p_start_date AND p_end_date
  )
  SELECT
    season_year::int as year,
    season,
    AVG(value) as mean
  FROM seasons
  WHERE p_season IS NULL OR season = p_season
  GROUP BY season_year, season
  ORDER BY season_year,
          CASE season
            WHEN 'winter' THEN 1
            WHEN 'spring' THEN 2
            WHEN 'summer' THEN 3
            WHEN 'autumn' THEN 4
          END;
END;
$$;

SET search_path TO public, vanda;