# DmpHydroApiRequestsCreateStationRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**operatorStationId** | **String** | A unique operator station id within the operator organization |  [optional]
**locationTypeSc** | **Integer** | A subset of stancode list 1002 | 
**stationOwnerCvr** | **String** | Station owner CVR with the format of DK12345678 | 
**operatorCvr** | **String** | Operator CVR with the format of DK12345678 |  [optional]
**name** | **String** | Station name |  [optional]
**description** | **String** | Station description |  [optional]
**location** | [**DmpHydroApiRequestsLocation**](DmpHydroApiRequestsLocation.md) |  | 
**waterAreaNr** | **String** | Water area number |  [optional]
**dguNumber** | **String** | DGU number of Groundwater station |  [optional]
**measurementPointTypeSc** | **Integer** | The stancode of measurement point type (subset of stancode list 1002) that is created with the station automatically. If it is not presented, measurement point type is decided by the system |  [optional]
