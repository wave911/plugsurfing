FROM hcr.data.here.com/public_mirror/alpine:3.11.2 as java
RUN apk --no-cache add openjdk11-jre

ADD target/hometask-0.0.1-SNAPSHOT.jar /opt/musify/hometask-0.0.1-SNAPSHOT.jar
ENV JAVA_OPTS="-Xms64m \
               -Xmx384m"
ENTRYPOINT exec java $JAVA_OPTS -jar /opt/musify/hometask-0.0.1-SNAPSHOT.jar
