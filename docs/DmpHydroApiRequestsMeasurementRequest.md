# DmpHydroApiRequestsMeasurementRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**formulaId** | **String** | Formula id |  [optional]
**measurementDateTime** | [**OffsetDateTime**](Date.md) | Measurement time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example &#x27;2023-09-21T14:34Z&#x27;. | 
**result** | **Double** | Result | 
**reasonCodeSc** | **Integer** | Reason code stancode, a subset of stancode list 1188 |  [optional]
**refPointText** | **String** | Reference point text, required for examination type sc 30, unavailable for other types |  [optional]
**distToFixPointM** | **Double** | Distance to fix point (meter), required for examination type sc 30, unavailable for other types |  [optional]
