FROM vanda_hydrometry_data_temp AS builder

FROM eclipse-temurin:21.0.6_7-jdk-alpine

RUN apk upgrade --available --no-cache

COPY --from=builder /vanda_hydrometry_data/target/vanda-hydrometry-data.jar ./vanda-hydrometry-data.jar

ENTRYPOINT ["java", "-jar", "vanda-hydrometry-data.jar"]