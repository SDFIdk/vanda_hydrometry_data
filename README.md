# vanda_hydrometry_data

Store static data from DMP VanDa Hydrometry API.

Find the API description and details (including prod, test and demo endpoints) at Danmarks Miljø Portalen VanDa Wiki [https://github.com/danmarksmiljoeportal/VanDa/wiki/Hydro-API](https://github.com/danmarksmiljoeportal/VanDa/wiki/Hydro-API).

The PROD Swagger can be accessed here [https://vandah.miljoeportal.dk/api/swagger/index.html](https://vandah.miljoeportal.dk/api/swagger/index.html)

## Description

The application is a command line application that upon execution connects to DMP Vanda API, retrieves data as configured in the command line parameters and saves the data in the database or displays it if necessary.

DMP and DB connections are configured in the  _application.properties_ file. The database DAO queries are based on Postgresql (with Postgis extension) database.

## Usage
This section shows the operations and parameters that can be used with the application. In order to run the application from the command line (console) use this command:

	java -jar vanda-hydrometry-data.jar dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataApplication
	
In order to tell the application what to do the following parameters should be used further (added to the command line). Parameters may be commands,or options that starts with "--" and may have or not a value after the "=" sign.

### Read stations

Retrieve all stations from DMP:

	stations
	
Retrieve a single or a subset of stations from DMP depending on extra criteria that is provided as parameters:

	stations [--stationId=string] [--examinationTypeSc=number,number] [--operatorStationId=string] [--withResultsAfter=date] [--withResultsCreatedAfter=date]
	
- **stationId** is a 8 digits number to identify a single station.
- **operatorStationId** the id of the stations' operator
- **examinationTypeSc** retrieve the stations that provides the requested examination types. Can be a comma separated values (no spaces).
- **withResultsAfter** only return stations with examinations that got results measured after a point in time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component "Z" (Zulu) is not provided, the system's time zone is considered.
- **withResultsCreatedAfter** only return stations with examination that contains results created after a point in time. This is the point in time there where created/updated in the system and not the actual measurement time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component "Z" (Zulu) is not provided, the system's time zone is considered.

### Read water levels

Returns current results of water level (ExaminationType 25) measurements.

	waterLevels --stationId=string [--operatorStationId=string] [--measurementPointNumber=number] [--from=date] [--to=date] [--createdAfter=date]
	
- **stationId** (_required_) is a 8 digits number to identify a single station. Either stationId or operatorStationId must be provided. Use "all" (for ex. --stationId=all) to read data for all stations saved in the database. Use comma separated values (f.ex. --stationId=10000002,10000003) to read data for selected stations.
- **operatorStationId** the id of the stations' operator. Either stationId or operatorStationId must be provided.
- **measurementPointNumber** the measurement point number on the station. If not specified, returns all measurement points.
- **from** from measurement date time to include in the response. Return results on the specified date time and later. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component "Z" (Zulu) is not provided, the system's time zone is considered.
- **to** to measurement date time to include in the response. Return results on the specified date time and ealier. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component "Z" (Zulu) is not provided, the system's time zone is considered.
- **createdAfter** return results that are created or updated after the specified date time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component "Z" (Zulu) is not provided, the system's time zone is considered.

### Read water flows

Returns current results of water flow (ExaminationType 27) measurements.

	waterFlow --stationId=string [--operatorStationId=string] [--measurementPointNumber=number] [--from=date] [--to=date] [--createdAfter=date]
	
- **stationId** (_required_) is a 8 digits number to identify a single station. Either stationId or operatorStationId must be provided. Use "all" (for ex. --stationId=all) to read data for all stations saved in the database. Use comma separated values (f.ex. --stationId=10000002,10000003) to read data for selected stations.
- **operatorStationId** the id of the stations' operator. Either stationId or operatorStationId must be provided.
- **measurementPointNumber** the measurement point number on the station. If not specified, returns all measurement points.
- **from** from measurement date time to include in the response. Return results on the specified date time and later. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component "Z" (Zulu) is not provided, the system's time zone is considered.
- **to** to measurement date time to include in the response. Return results on the specified date time and ealier. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component "Z" (Zulu) is not provided, the system's time zone is considered.
- **createdAfter** return results that are created or updated after the specified date time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component "Z" (Zulu) is not provided, the system's time zone is considered.


### Read examination types

Returns the examination types with mapping and constraints.

	examinationTypes

	
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
	
### More output

In order to display more details about the execution of the program or warnings in the console use the parameter "verbose". 

	stations --verbose

### Help

Running the application without parameters will display the help info. For more details on a command use the option --help. Example:

	stations --help


## Development

In order to re-generate sources (the data model) from DMP API, enable the plugin in pom.xml by setting **skip** to **false**.

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

## Configuration

The following configurations can be adjusted in different configuration files:  _application.properties_ ,  _logback.xml_ :

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
	vanda-hidrometry-data.database.test=true
```

- **Danmarks Miljø Portalen API URL**

  Set the API URL on this property. It can point to demo (https://vandah.demo.miljoeportal.dk/api/), test (https://vandah.test.miljoeportal.dk/api/) or production (https://vandah.miljoeportal.dk/api/).

```
	dmp.vandah.api.url=https://vandah.test.miljoeportal.dk/api/
```

- **Execution for multiple stations**

  If there is the need to execute a command for several stations one could use the option "--stationId". For exmple: "--stationId=all" or "--stationId=00000001,00000002". This property sets the mode the command will be executed.
  
  If this is set to "true" then the requested command (not API calls) is executed several times for each required station. Saving happens after each station. This is slower but less memory usage. 
  
  If this is set to "false" then the command is executed once for all required stations. Saving happens once for the entire data at the end. This is faster but more memory consuming. 
  
  Individual API calls will be made for each station in any case.

```
	vanda-hidrometry-data.one-command-per-station=true
```

- **Logging**

  The file logback.xml defines the configuration for logging. It enables the logging into rolling files, their name and format.