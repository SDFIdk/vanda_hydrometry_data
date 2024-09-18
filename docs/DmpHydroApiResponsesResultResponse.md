# DmpHydroApiResponsesResultResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**measurementPointNumber** | **Integer** | Measurement point number |  [optional]
**measurementPointTypeSc** | **Integer** | Measurement point type stancode |  [optional]
**measurementPointType** | **String** | Measurement point type name |  [optional]
**parameterSc** | **Integer** | Parameter stancode |  [optional]
**parameter** | **String** | Parameter name |  [optional]
**examinationTypeSc** | **Integer** | Examination type stancode |  [optional]
**examinationType** | **String** | Examination type name |  [optional]
**measurementDateTime** | [**Date**](Date.md) | Measurement date time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example &#x27;2023-09-21T14:34Z&#x27;. |  [optional]
**result** | **Double** | Measurement result |  [optional]
**resultElevationCorrected** | **Double** | Elevation corrected result. Only available for Water Level examination |  [optional]
**unitSc** | **Integer** | Unit stancode |  [optional]
**unit** | **String** | Unit name |  [optional]
**loggerId** | **String** | Id of the logger that provided the measurement result |  [optional]
**formulaId** | **String** | Formula id |  [optional]
**createdTimestamp** | [**Date**](Date.md) | The timestamp when the result was delivered/created. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example &#x27;2023-09-21T14:34Z&#x27;. |  [optional]
**reasonSc** | **Integer** | Reason stancode |  [optional]
**reason** | **String** | Reason name |  [optional]
**vegetationIndex** | **Integer** | Vegetation index |  [optional]
**refPointText** | **String** | Reference point text |  [optional]
**distToFixPointM** | **Double** | Distance to fix point in meter (m) |  [optional]
