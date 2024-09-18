# DmpHydroApiRequestsUpdateExaminationTypeRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**examinationTypeSc** | **Integer** | Examination type stancode, a subset of stancode list 1101 | 
**measurementPointTypeSc** | **Integer** | Measurement point type stancode, a subset of stancode list 1002 |  [optional]
**reasonCodesSc** | **List&lt;Integer&gt;** | List of reason codes stancode, a subset of stancode list 1188 | 
**examinationTypeName** | **String** | Examination type name | 
**parameterSc** | **Integer** | Parameter stancode, a subset of stancode list 1008 | 
**unitSc** | **Integer** | Unit stancode, a subset of stancode list 1009 | 
**min** | **Double** | Min value of measurements under the examination type |  [optional]
**max** | **Double** | Max value of measurements under the examination type |  [optional]
**decimals** | **Integer** | Number of decimals rounded for the measurements values |  [optional]
**description** | **String** | Description for the examination type |  [optional]
**measurementIntervalMin** | **Integer** | Minimum interval between measurement in minutes |  [optional]
