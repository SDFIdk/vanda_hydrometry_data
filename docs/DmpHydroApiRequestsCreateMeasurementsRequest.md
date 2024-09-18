# DmpHydroApiRequestsCreateMeasurementsRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**station** | [**DmpHydroApiRequestsStationIdentifier**](DmpHydroApiRequestsStationIdentifier.md) |  | 
**measurementPointNumber** | **Integer** | Measurement point number in the station | 
**examinationTypeSc** | **Integer** | Examination type stancode, a subset of stancode 1101 | 
**loggerId** | **String** | Logger id |  [optional]
**parameterSc** | **Integer** | Parameter stancode, a subset of stancode list 1008 | 
**unitSc** | **Integer** | Unit stancode, a subset of stancode list 1009 | 
**transactionBased** | **Boolean** | True: The batch is treated as a transaction. If one or more fails, no data is persisted in the database. False: Measurements are treated individually. Some might fail while others are persisted in the database. |  [optional]
**measurements** | [**List&lt;DmpHydroApiRequestsMeasurementRequest&gt;**](DmpHydroApiRequestsMeasurementRequest.md) | A list of measurements on the measurement point | 
