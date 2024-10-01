package dk.dataforsyningen.vanda_hydrometry_data.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dk.dataforsyningen.vanda_hydrometry_data.dao.MeasurementDao;
import dk.dataforsyningen.vanda_hydrometry_data.dao.MeasurementTypeDao;
import dk.dataforsyningen.vanda_hydrometry_data.dao.StationDao;
import dk.dataforsyningen.vanda_hydrometry_data.model.Measurement;
import dk.dataforsyningen.vanda_hydrometry_data.model.MeasurementType;
import dk.dataforsyningen.vanda_hydrometry_data.model.Station;

@Service
public class DatabaseService {

	private StationDao stationDao;
	private MeasurementDao measurementDao;
	private MeasurementTypeDao measurementTypeDao;
	
	public DatabaseService(StationDao stationDao, MeasurementDao measurementDao, MeasurementTypeDao measurementTypeDao) {
		this.stationDao = stationDao;
		this.measurementDao = measurementDao;
		this.measurementTypeDao = measurementTypeDao;
	}
	
	/* STATION */
	
	public List<Station> getAllStations() {
		return stationDao.getAllStations();
	}
	
	@Transactional
	public void saveStations(List<Station> stations) {
		stationDao.addStations(stations);
	}
	
	@Transactional
	public void saveStation(Station station) {
		stationDao.addStation(station);
	}
	
	/* MEASUREMENT */
	
	public List<Measurement> getAllMeasurements() {
		return measurementDao.getAllMeasurements();
	}
	
	@Transactional
	public void saveMeasurements(List<Measurement> measurements) {
		//do an update in case they exist
		measurementDao.updateMeasurements(measurements);
		
		//add the measurements if they are missing
		measurementDao.addMeasurements(measurements);
	}
	
	@Transactional
	public void saveMeasurement(Measurement measurement) {
		//do an update in case it exists
		measurementDao.updateMeasurement(measurement);
				
		//add the measurement if is is missing
		measurementDao.addMeasurement(measurement);
	}
	
	/* MEASUREMENT TYPE */
	
	public List<MeasurementType> getAllMeasurementTypes() {
		return measurementTypeDao.getAllMeasurementTypes();
	}
	
	public MeasurementType getMeasurementType(String id) {
		return measurementTypeDao.findMeasurementTypeById(id);
	}
	
	@Transactional
	public void addMeasurementType(MeasurementType measurementType) {
		measurementTypeDao.addMeasurementType(measurementType);
	}
	
	@Transactional
	public void addMeasurementTypes(List<MeasurementType> measurementTypes) {
		measurementTypeDao.addMeasurementTypes(measurementTypes);
	}
}
