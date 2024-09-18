# DmpHydroApiRequestsUpdateMeasurementsRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**station** | [**DmpHydroApiRequestsStationIdentifier**](DmpHydroApiRequestsStationIdentifier.md) |  | 
**measurementPointNumber** | **Integer** | Measurement point number in the station | 
**examinationTypeSc** | **Integer** | Examination type stancode, a subset of stancode 1101 | 
**transactionBased** | **Boolean** | True: The batch is treated as a transaction. If one or more fails, no data is persisted in the database. False: Measurements are treated individually. Some might fail while others are persisted in the database. |  [optional]
**results** | [**List&lt;DmpHydroApiRequestsResultRequest&gt;**](DmpHydroApiRequestsResultRequest.md) |  | 
