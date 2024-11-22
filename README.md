# vanda_hydrometry_data

Store static data from DMP VanDa Hydrometry API.

Find the API description and details (including prod, test and demo endpoints) at Danmarks Miljø Portalen VanDa Wiki [https://github.com/danmarksmiljoeportal/VanDa/wiki/Hydro-API](https://github.com/danmarksmiljoeportal/VanDa/wiki/Hydro-API).

The PROD Swagger can be accessed here [https://vandah.miljoeportal.dk/api/swagger/index.html](https://vandah.miljoeportal.dk/api/swagger/index.html)

## Description

The application is a command line application that upon execution connects to DMP Vanda API, retrieves data as configured in the command line parameters and saves the data in the database or displays it if necessary.

DMP and DB connections are configured in the  _application.properties_ file. The database DAO queries are based on Postgresql (with Postgis extension) database.

## Usage
This section shows the operations and parameters that can be used with the application. In order to run the application from the command line (console) use this command:

	java -jar vanda-hydrometry-data.jar
	
In order to tell the application what to do the following parameters should be used further (added to the command line). Parameters may be commands,or options that starts with "--" and may have or not a value after the "=" sign.

### Read stations

Retrieve all stations from DMP:

	stations
	
Retrieve a single or a subset of stations from DMP depending on extra criteria that is provided as parameters:

	stations [--stationId=string] [--examinationTypeSc=number] [--operatorStationId=string] [--withResultsAfter=date] [--withResultsCreatedAfter=date]
	
- **stationId** is a 8 digits number to identify a single station.
- **operatorStationId** the id of the stations' operator
- **examinationTypeSc** retrieve the stations that provides the requested examination type. 
- **withResultsAfter** only return stations with examinations that got results measured after a point in time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component "Z" (Zulu) is not provided, the system's time zone is considered.
- **withResultsCreatedAfter** only return stations with examination that contains results created after a point in time. This is the point in time there where created/updated in the system and not the actual measurement time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component "Z" (Zulu) is not provided, the system's time zone is considered.

### Read water levels

Returns current results of water level (ExaminationType 25) measurements.

	waterlevels --stationId=string [--operatorStationId=string] [--measurementPointNumber=number] [--from=date] [--to=date] [--createdAfter=date]
	
- **stationId** (_required_) is a 8 digits number to identify a single station. Either stationId or operatorStationId must be provided. Use "all" (for ex. --stationId=all) to read data for all stations saved in the database. Use comma separated values (f.ex. --stationId=10000002,10000003) to read data for selected stations.
- **operatorStationId** the id of the stations' operator. Either stationId or operatorStationId must be provided.
- **measurementPointNumber** the measurement point number on the station. If not specified, returns all measurement points.
- **from** from measurement date time to include in the response. Return results on the specified date time and later. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component "Z" (Zulu) is not provided, the system's time zone is considered.
- **to** to measurement date time to include in the response. Return results on the specified date time and ealier. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component "Z" (Zulu) is not provided, the system's time zone is considered. 
- **createdAfter** return results that are created or updated after the specified date time. Should be used together with 'from' and 'to' parameters. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component "Z" (Zulu) is not provided, the system's time zone is considered.

### Read stream discharge

Returns current results of stream discharge (ExaminationType 27) measurements. On DMP API the endpoint is called water-flows.

	streamdischarge --stationId=string [--operatorStationId=string] [--measurementPointNumber=number] [--from=date] [--to=date] [--createdAfter=date]
	
- **stationId** (_required_) is a 8 digits number to identify a single station. Either stationId or operatorStationId must be provided. Use "all" (for ex. --stationId=all) to read data for all stations saved in the database. Use comma separated values (f.ex. --stationId=10000002,10000003) to read data for selected stations.
- **operatorStationId** the id of the stations' operator. Either stationId or operatorStationId must be provided.
- **measurementPointNumber** the measurement point number on the station. If not specified, returns all measurement points.
- **from** from measurement date time to include in the response. Return results on the specified date time and later. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component "Z" (Zulu) is not provided, the system's time zone is considered.
- **to** to measurement date time to include in the response. Return results on the specified date time and ealier. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component "Z" (Zulu) is not provided, the system's time zone is considered.
- **createdAfter** return results that are created or updated after the specified date time. Should be used together with 'from' and 'to' parameters. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component "Z" (Zulu) is not provided, the system's time zone is considered.


### Read examination types

Returns the examination types with mapping and constraints.

	examinationtypes

	
### Save to DB

In order to save the retrieved data into the DB (using the config from properties file) use the parameter "saveDb". Example that saves all stations:

	stations --saveDb
	
Usually if the data item already exists in the database its relevant values will be updated otherwise the item will be inserted. In this way there will not be doublets.

The save command is ignored for Examination Types.

### Inspect API retrieved data

In order to display the data retrieved from the API in the console (or redirect the output into a file) so the user can inspect it, use the parameter "displayRawData". Example that will display all the stations.

	stations --displayRawData
	
### Inspect internal data

In order to display the mapped data in the console (or redirect the output into a file) so the user can inspect it, use the parameter "displayData". Example that will display all the stations.

	stations --displayData	
	
### Help

Running the application without parameters will display the help info. For more details on a command use the option --help. Example:

	stations --help


## Examples

Get all station, display them on the screen:

```
java -jar vanda-hydrometry-data.jar stations --displayData
```

Get all station, save them in the database (insert or update) and display the summary on the screen:

```
java -jar vanda-hydrometry-data.jar stations --saveDb
```

Get all water levels measurements between 2 dates for all stations saved into the db, then save the measurements into the DB:

```
java -jar vanda-hydrometry-data.jar waterlevels --stationId=all --saveDb --from=2024-10-01Z --to=2024-10-31Z
```

Get all stream discharge measurements between 2 dates for 2 stations and siplay the results on the screen:

```
java -jar vanda-hydrometry-data.jar streamdischarge --stationId=10000001,10000002 --displayData --from=2024-10-01Z --to=2024-10-2Z
```


## Configuration

The following configurations can be adjusted in  _application.properties_ :

- **Postgres database connection info**

  Provide database, username and password to connect to the Postgres database.

```
	spring.datasource.url=jdbc:postgresql:{database}
	spring.datasource.username={username}
	spring.datasource.password={password}
	spring.datasource.driver-class-name=org.postgresql.Driver
```

- **Enable database test**

  In order to enable the database junit testing set this property to true. Note that a functional database connection is required.

```
	vanda-hydrometry-data.database.test=true
```

- **Danmarks Miljø Portalen API URL**

  Set the API URL on this property. It can point to demo (https://vandah.demo.miljoeportal.dk/api/), test (https://vandah.test.miljoeportal.dk/api/) or production (https://vandah.miljoeportal.dk/api/).

```
	dmp.vandah.api.url=https://vandah.test.miljoeportal.dk/api/
```

