# vanda_hydrometry_data

Store static data from DMP VanDa Hydrometry API.

Find the API description and details (including prod, test and demo endpoints) at Danmarks Miljø Portalen VanDa Wiki [https://github.com/danmarksmiljoeportal/VanDa/wiki/Hydro-API](https://github.com/danmarksmiljoeportal/VanDa/wiki/Hydro-API).

The PROD Swagger can be accessed here [https://vandah.miljoeportal.dk/api/swagger/index.html](https://vandah.miljoeportal.dk/api/swagger/index.html)

## Description

The application is a command line application that upon execution connects to DMP Vanda API, retrieves data as configured in the command line parameters and saves the data in the database or displays it if necessary.

DMP and DB connections are configured in the  _application.properties_ file. 

## Usage
This section shows the operations and parameters that can be used with the application. In order to run the application from the command line (console) use this command:

	java -jar vanda-hydrometry-data.jar dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataApplication
	
In order to tell the application what to do the following parameters should be used further (added to the command line). Parameters may be commands,or options that starts with "--" and may have or not a value after the "=" sign.

### Read stations

Retrieve all stations from DMP:

	stations
	
Retrieve a single or a subset of stations from DMP depending on extra criteria that is provided as parameters:

	stations [--stationId=string] [--examinationTypeSc=number,number] [--operatorStationId=string] [--withResultsAfter=date] [--withResultsCreatedAfter=date]
	
* **stationId** is a 8 digits number to identify a single station.
* **operatorStationId** the id of the stations' operator
* **examinationTypeSc** retrieve the stations that provides the requested examination types. Can be a comma separated values (no spaces).
* **withResultsAfter** only return stations with examinations that got results measured after a point in time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.
* **withResultsCreatedAfter** only return stations with examination that contains results created after a point in time. This is the point in time there where created/updated in the system and not the actual measurement time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.

### Read water levels

Returns current results of water level (ExaminationType 25) measurements.

	waterLevels --stationId=string [--operatorStationId=string] [--measurementPointNumber=number] [--from=date] [--to=date] [--createdAfter=date]
	
* **stationId** is a 8 digits number to identify a single station. Either stationId or operatorStationId must be provided.
* **operatorStationId** the id of the stations' operator. Either stationId or operatorStationId must be provided.
* **measurementPointNumber** the measurement point number on the station. If not specified, returns all measurement points.
* **from** from measurement date time to include in the response. Return results on the specified date time and later. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.
* **to** to measurement date time to include in the response. Return results on the specified date time and ealier. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.
* **createdAfter** return results that are created or updated after the specified date time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.

### Read water flows

Returns current results of water flow (ExaminationType 27) measurements.

	waterFlow --stationId=string [--operatorStationId=string] [--measurementPointNumber=number] [--from=date] [--to=date] [--createdAfter=date]
	
* **stationId** (_required_) is a 8 digits number to identify a single station. Either stationId or operatorStationId must be provided.
* **operatorStationId** the id of the stations' operator. Either stationId or operatorStationId must be provided.
* **measurementPointNumber** the measurement point number on the station. If not specified, returns all measurement points.
* **from** from measurement date time to include in the response. Return results on the specified date time and later. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.
* **to** to measurement date time to include in the response. Return results on the specified date time and ealier. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.
* **createdAfter** return results that are created or updated after the specified date time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.


### Read examination types

Returns the examination types with mapping and constraints.

	examinationTypes

	
### Save to DB

In order to save the retrieved data into the DB (using the config from properties file) use the parameter "saveDb". Example that saves all stations:

	stations --savedb
	
The parameter is ignored for Examination Types.

### Inspect data

In order to display the data in the console (or redirect the output into a file) so the user can inspect it, use the parameter "verbose". Example that will display the examination types.

	stations --verbose

### Help

Running the application without parameters will display the help info. For more details on a command use the option --help. Example:

	stations --help


## Development

In order to re-generate sources (the data model) from DMP API, enable the plugin in pom.xml

```
	<plugin>
		<groupId>io.swagger.codegen.v3</groupId>
		<artifactId>swagger-codegen-maven-plugin</artifactId>
		<version>3.0.61</version>
		<configuration>
			<skip>false</skip>
		</configuration>
	...
```

