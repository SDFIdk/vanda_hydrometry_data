# DmpHydroApiResponsesCreateMeasurementsResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**created** | [**Date**](Date.md) | The time data is stored in the database for the batch |  [optional]
**transactionId** | [**UUID**](UUID.md) | Batch transaction id |  [optional]
**successful** | [**List&lt;DmpHydroApiResponsesSuccessfulResponse&gt;**](DmpHydroApiResponsesSuccessfulResponse.md) | A list of measurements that were stored |  [optional]
**failed** | [**List&lt;DmpHydroApiResponsesFailedResponse&gt;**](DmpHydroApiResponsesFailedResponse.md) | A list of measurements that were failed to store |  [optional]
