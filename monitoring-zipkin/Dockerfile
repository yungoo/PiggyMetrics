FROM java:8-jre
MAINTAINER Alexander Lukyanchikov <sqshq@sqshq.com>

ADD ./target/monitoring-zipkin.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/monitoring-zipkin.jar"]

EXPOSE 8090