package dk.dataforsyningen.vanda_hydrometry_data.service;

import dk.dataforsyningen.vanda_hydrometry_data.dao.MeasurementDao;
import dk.dataforsyningen.vanda_hydrometry_data.dao.MeasurementTypeDao;
import dk.dataforsyningen.vanda_hydrometry_data.dao.StationDao;
import dk.dataforsyningen.vanda_hydrometry_data.model.Measurement;
import dk.dataforsyningen.vanda_hydrometry_data.model.MeasurementType;
import dk.dataforsyningen.vanda_hydrometry_data.model.Station;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service class providing DAO access.
 *
 * @author Radu Dudici
 */
@Service
public class DatabaseService {

    private final StationDao stationDao;
    private final MeasurementDao measurementDao;
    private final MeasurementTypeDao measurementTypeDao;

    public DatabaseService(StationDao stationDao, MeasurementDao measurementDao, MeasurementTypeDao measurementTypeDao) {
        this.stationDao = stationDao;
        this.measurementDao = measurementDao;
        this.measurementTypeDao = measurementTypeDao;
    }

    /* STATION */

    public List<Station> getAllStations() {
        List<Station> stationsAndMeasurementTypes = stationDao.readAllStations();

        //merge (the measurement types) on same station
        HashMap<String, Station> stations = new HashMap<>();
        for (Station s : stationsAndMeasurementTypes) {
            if (!stations.containsKey(s.getStationId())) {
                stations.put(s.getStationId(), s);
            } else {
                Station station = stations.get(s.getStationId());
                if (s.getMeasurementTypes().size() > 0) {
                    //after mapping from DB the station objects will only contain one (or none) measurements
                    station.getMeasurementTypes().add(s.getMeasurementTypes().getFirst());
                }
            }
        }

        return new ArrayList<Station>(stations.values());
    }

    public List<Station> getAllStationsByExaminationType(int examinationTypeSc) {
        List<Station> stationsAndMeasurementTypes = stationDao.readStationByExaminationTypeSc(examinationTypeSc);

        //merge (measurement types) on same station
        HashMap<String, Station> stations = new HashMap<>();
        for (Station s : stationsAndMeasurementTypes) {
            if (!stations.containsKey(s.getStationId())) {
                stations.put(s.getStationId(), s);
            } else {
                Station station = stations.get(s.getStationId());
                if (s.getMeasurementTypes().size() > 0) {
                    //after mapping from DB the station objects will only contain one (or none) measurements
                    station.getMeasurementTypes().add(s.getMeasurementTypes().getFirst());
                }
            }
        }

        return new ArrayList<Station>(stations.values());
    }

    public Station getStation(String id) {
        List<Station> stationsAndMeasurementTypes = stationDao.readStationByStationId(id);

        //merge (measurement types) into the station
        Station station = null;
        for (Station s : stationsAndMeasurementTypes) {
            if (station == null) {
                station = s;
            } else {
                assert Objects.equals(station.getStationId(), s.getStationId());
                if (s.getMeasurementTypes().size() > 0) {
                    //after mapping from DB the station objects will only contain one (or none) measurements
                    station.getMeasurementTypes().add(s.getMeasurementTypes().getFirst());
                }
            }
        }

        return station;
    }

    public boolean isMeasurementSupported(String stationId, int examinationTypeSc) {
        return stationDao.isExaminationTypeScSupported(stationId, examinationTypeSc);
    }

    /**
     * Inserts stations (and related measurement type and the relations) from the given list
     * if they do not exist or update them otherwise.
     *
     * @param stations list
     */
    @Transactional
    public void saveStations(List<Station> stations) {
        //add/update station
        stationDao.insertStations(stations);

        //save measurement types
        addMeasurementTypes(stations.stream().flatMap(station -> station.getMeasurementTypes().stream()).collect(Collectors.toList()));

        //save station <-> measurement_type relation if it does not exist
        stations.stream().forEach(
                station -> {
                    ArrayList<MeasurementType> measurementTypes = station.getMeasurementTypes();
                    if (measurementTypes != null) {
                        stationDao.insertStationMeasurementTypeRelations(measurementTypes.stream().map(mt -> station.getStationId()).toList(), measurementTypes);
                    }
                }
        );
    }

    /**
     * Insert station (and related measurement type and the relations)
     * if it does not exist or updates it otherwise.
     *
     * @param station
     */
    @Transactional
    public void saveStation(Station station) {
        //add/update station
        stationDao.insertStation(station);

        //save measurement types
        addMeasurementTypes(station.getMeasurementTypes());

        //save station <-> measurement_type relation if it does not exist
        ArrayList<MeasurementType> measurementTypes = station.getMeasurementTypes();
        if (measurementTypes != null) {
            stationDao.insertStationMeasurementTypeRelations(measurementTypes.stream().map(mt -> station.getStationId()).toList(), measurementTypes);
        }
    }

    public void deleteStation(String id) {
        stationDao.deleteRelationToMeasurementTypeByStation(id);
        stationDao.deleteStation(id);
    }

    public void deleteStationMeasurementTypeRelation(String id) {
        stationDao.deleteRelationToMeasurementTypeByStation(id);
    }

    public int countStations() {
        return stationDao.count();
    }

    /* MEASUREMENT */

    /**
     * Get measurement history, i.e. all records related to the requested measurement
     *
     * @param stationId
     * @param measurementPointNumber
     * @param measurementDatetime
     * @return list of measurement history for the given measurement
     */
    public List<Measurement> getMeasurementHistory(String stationId,
                                                   int measurementPointNumber,
                                                   int examinationTypeSc,
                                                   OffsetDateTime measurementDatetime) {
        return measurementDao.readMeasurementHistory(stationId, measurementPointNumber, examinationTypeSc, measurementDatetime);
    }

    /**
     * Returns the active (there should be only one) measurement matching the given parameters.
     *
     * @param stationId
     * @param measurementPointNumber
     * @param measurementDatetime
     * @return Measurement
     */
    public Measurement getMeasurement(String stationId,
                                      int measurementPointNumber,
                                      int examinationTypeSc,
                                      OffsetDateTime measurementDatetime) {
        return measurementDao.readCurrentMeasurement(stationId, measurementPointNumber, examinationTypeSc, measurementDatetime);
    }

    /**
     * Inserts a new active (is_current = true) record in the given measurements' histories
     * and deactivate (is_current=false) their older records.
     *
     * @param measurements list
     */
    @Transactional
    public void saveMeasurements(List<Measurement> measurements) {
        //deactivate their history
        measurementDao.inactivateMeasurementsHistory(measurements);

        //add the active record for the list of measurements
        measurementDao.insertMeasurements(measurements);
    }

    /**
     * Inserts a new active (is_current = true) record in the given measurement's history
     * and deactivate (is_current=false) its older records.
     *
     * @param measurement
     * @return inserted measurement or null
     */
    @Transactional
    public Measurement saveMeasurement(Measurement measurement) {
        //deactivate its history
        measurementDao.inactivateMeasurementHistory(measurement);

        //add the active record for the measurement
        return measurementDao.insertMeasurement(measurement);
    }

    public void deleteMeasurement(String stationId, int measurementPointNumber,
                                  int examinationTypeSc,
                                  OffsetDateTime measurementDatetime
    ) {
        measurementDao.deleteMeasurementWithHistory(stationId, measurementPointNumber, examinationTypeSc, measurementDatetime);
    }

    public void deleteMeasurementForStation(String stationId) {
        measurementDao.deleteMeasurementsForStation(stationId);
    }

    public int countAllMeasurements() {
        return measurementDao.countAll();
    }

    /* MEASUREMENT TYPE */

    public List<MeasurementType> getAllMeasurementTypes() {
        return measurementTypeDao.readAllMeasurementTypes();
    }

    public MeasurementType getMeasurementType(int examinationTypeSc) {
        return measurementTypeDao.readMeasurementTypeByExaminationType(examinationTypeSc);
    }

    /**
     * Inserts (if it does not exist) or updates the measurement type.
     *
     * @param measurementType
     */
    public void addMeasurementType(MeasurementType measurementType) {
        measurementTypeDao.insertMeasurementType(measurementType);
    }

    /**
     * Inserts (if it does not exist) or update the measurement from the given list.
     *
     * @param measurementTypes list
     */
    public void addMeasurementTypes(List<MeasurementType> measurementTypes) {
        measurementTypeDao.insertMeasurementTypes(measurementTypes);
    }

    public void deleteMeasurementType(int examinationTypeSc) {
        measurementTypeDao.deleteMeasurementType(examinationTypeSc);
    }

    public int countMeasurementTypes() {
        return measurementTypeDao.count();
    }

}
