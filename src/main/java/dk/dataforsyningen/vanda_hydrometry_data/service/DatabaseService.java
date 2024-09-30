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
	
	@Transactional
	public void saveStations(List<Station> stations) {
		stationDao.addStations(stations);
	}
	
	@Transactional
	public void saveMeasurements(List<Measurement> measurements) {
		measurementDao.addMeasurements(measurements);
	}
	
	public List<MeasurementType> getAllMeasurementTypes() {
		return measurementTypeDao.readAllMeasurementTypes();
	}
	
	@Transactional
	public void addMeasurementType(MeasurementType measurementType) {
		measurementTypeDao.addMeasurementType(measurementType);
	}
}
