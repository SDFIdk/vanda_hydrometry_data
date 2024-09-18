# DmpHydroApiResponsesStationResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**stationUid** | [**UUID**](UUID.md) | Vanda station GUID id |  [optional]
**stationId** | **String** | A 8-digits station id |  [optional]
**operatorStationId** | **String** | Operator station id |  [optional]
**oldStationNumber** | **String** | Old station number |  [optional]
**locationType** | **String** | Location type name |  [optional]
**locationTypeSc** | **Integer** | Location type stancode |  [optional]
**stationOwnerCvr** | **String** | Station owner cvr number |  [optional]
**stationOwnerName** | **String** | Station owner name |  [optional]
**operatorCvr** | **String** | Operator cvr number |  [optional]
**operatorName** | **String** | Operator name |  [optional]
**name** | **String** | Station name |  [optional]
**dguNumber** | **String** | DGU number of Groundwater station |  [optional]
**description** | **String** | Station description |  [optional]
**loggerId** | **String** | Logger id |  [optional]
**location** | [**DmpHydroApiResponsesLocationResponse**](DmpHydroApiResponsesLocationResponse.md) |  |  [optional]
**measurementPoints** | [**List&lt;DmpHydroApiResponsesStationResponseMeasurementPoint&gt;**](DmpHydroApiResponsesStationResponseMeasurementPoint.md) | A list of measurement points data in station |  [optional]
