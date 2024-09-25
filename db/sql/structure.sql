CREATE EXTENSION postgis;
CREATE SCHEMA hydrometry;
SET search_path TO hydrometry, public;

CREATE TABLE station
(
 station_id         char(8) NOT NULL,
 old_station_number char(8) NULL,
 name               varchar(150) NULL,
 station_owner_name varchar(150) NULL,
 location           geometry(POINT,25832) NOT NULL,
 description        varchar(200) NULL,
 created            timestamp with time zone NOT NULL,
 updated            timestamp with time zone NOT NULL,
 CONSTRAINT PK_1 PRIMARY KEY ( station_id )
);
CREATE INDEX station_IDX_location ON station USING GIST (
  location
);

COMMENT ON TABLE station IS 'Contains information about the station';
COMMENT ON COLUMN station.station_id IS 'ID of the station, 8 characters';
COMMENT ON COLUMN station.old_station_number IS 'Old station number, 8 characters';
COMMENT ON COLUMN station.name IS 'Name of the station';
COMMENT ON COLUMN station.station_owner_name IS 'Name of the station owner';
COMMENT ON COLUMN station.location IS 'The geo location of the station';
COMMENT ON COLUMN station.description IS 'Description of the station that can contain catchment information, for example "Opland = 426,7 km2"';

CREATE TABLE measurement_type
(
 measurement_type_id      int NOT NULL,
 unit                     varchar(4) NOT NULL,
 unit_sc                  int NOT NULL,
 "parameter"              varchar(10) NOT NULL,
 parameter_sc             int NOT NULL,
 examination_type_sc      int NOT NULL,
 examination_type         varchar(10) NOT NULL,
 CONSTRAINT PK_3 PRIMARY KEY ( measurement_type_id )
);

COMMENT ON TABLE measurement_type IS 'Contains information about the measurement types like Water Level, Water Flow, etc.';
COMMENT ON COLUMN measurement_type.measurement_type_id IS 'The unique identifier of the measurement type';
COMMENT ON COLUMN measurement_type.unit IS 'The unit of the measurement, for example "cm, m³/s"';
COMMENT ON COLUMN measurement_type.unit_sc IS 'The stancode of the unit';
COMMENT ON COLUMN measurement_type.parameter IS 'The parameter of the measurement, for example "Vandføring" or "Vandstand"';
COMMENT ON COLUMN measurement_type.parameter_sc IS 'The stancode of the parameter';
COMMENT ON COLUMN measurement_type.examination_type_sc IS 'The stancode of the examination type';
COMMENT ON COLUMN measurement_type.examination_type IS 'The name of the examination type, for example "Vandføring" or "Vandstand"';
COMMENT ON COLUMN measurement_type.examination_type IS 'The name of the examination type, for example "Vandføring" or "Vandstand"';

CREATE TABLE measurement
(
 "result"              double precision NOT NULL,
 measurement_date_time timestamp with time zone NOT NULL,
 is_current            bool NOT NULL,
 created               timestamp with time zone NOT NULL,
 station_id            char(8) NOT NULL,
 measurement_type_id   int NOT NULL,
 measurement_point_number int NOT NULL,
 CONSTRAINT FK_1 FOREIGN KEY ( measurement_type_id ) REFERENCES measurement_type ( measurement_type_id ),
 CONSTRAINT FK_2 FOREIGN KEY ( station_id ) REFERENCES station ( station_id )
);

CREATE INDEX FK_1_M ON measurement
(
 measurement_type_id
);
CREATE INDEX FK_2_M ON measurement
(
 station_id
);
CREATE UNIQUE INDEX measurement_station_IDX ON measurement
(
  station_id,
  measurement_type_id,
  is_current,
  measurement_date_time
);

COMMENT ON TABLE measurement IS 'Contains the measurements of the stations';
COMMENT ON COLUMN measurement.result IS 'The result of the measurement, for example 4203';
COMMENT ON COLUMN measurement.measurement_date_time IS 'The measurement date time';
COMMENT ON COLUMN measurement.is_current IS 'Is the result current, ie. the latest result';

CREATE TABLE calculated
(
 slice                char NOT NULL,
 date_start           date NOT NULL,
 mean                 real NOT NULL,
 created              timestamp with time zone NOT NULL,
 updated              timestamp with time zone NOT NULL,
 station_id           char(8) NOT NULL,
 measurement_type_id  int NOT NULL,
 CONSTRAINT FK_3 FOREIGN KEY ( station_id ) REFERENCES station ( station_id ),
 CONSTRAINT FK_4 FOREIGN KEY ( measurement_type_id ) REFERENCES measurement_type ( measurement_type_id )
);

CREATE INDEX FK_1_C ON calculated
(
 station_id
);
CREATE INDEX FK_2_C ON calculated
(
 measurement_type_id
);
-- unique index
CREATE UNIQUE INDEX calculated_IDX ON calculated
(
 slice,
 station_id,
 date_start,
 measurement_type_id
);

COMMENT ON TABLE calculated IS 'Contains the calculated measurements of the stations';
COMMENT ON COLUMN calculated.slice IS 'The slice type (size): Y(ear), S(eason), M(onth) or D(ay)';
COMMENT ON COLUMN calculated.date_start IS 'The start date of the slice';
COMMENT ON COLUMN calculated.mean IS 'The mean of the measurements in the slice';
