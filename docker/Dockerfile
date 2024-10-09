FROM vanda_hydrometry_data_temp AS builder

FROM eclipse-temurin:21.0.4_7-jdk-alpine

RUN apk upgrade --available --no-cache

COPY --from=builder /vanda_hydrometry_data/target/vanda_hydrometry_data.jar ./vanda_hydrometry_data.jar

ENTRYPOINT java -jar vanda_hydrometry_data.jar