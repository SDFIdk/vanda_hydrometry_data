# DmpHydroApiRequestsDeleteRangedMeasurementsRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**station** | [**DmpHydroApiRequestsStationIdentifier**](DmpHydroApiRequestsStationIdentifier.md) |  | 
**measurementPointNumber** | **Integer** | Measurement point number | 
**examinationTypeSc** | **Integer** | Examination type stancode | 
**fromDateTime** | [**Date**](Date.md) | From date time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example &#x27;2023-09-21T14:34Z&#x27;. |  [optional]
**toDateTime** | [**Date**](Date.md) | To date time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example &#x27;2023-09-21T14:34Z&#x27;. |  [optional]
