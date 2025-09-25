####
# This Dockerfile is used in order to build a container that runs the Quarkus application in JVM mode
#
# Before building the container image run:
#
# ./mvnw package
#
# Then, build the image with:
#
# docker build -f Dockerfile -t quarkus/transactions-service-jvm .
#
# Then run the container using:
#
# docker run -i --rm -p 8080:8080 quarkus/transactions-service-jvm
#
# If you want to include the debug port into your docker image
# you will have to expose the debug port (default 5005 being the default) like this :  EXPOSE 8080 5005.
# Additionally you will have to set -e JAVA_DEBUG=true and -e JAVA_DEBUG_PORT=*:5005
# when running the container
#
# Then run the container using :
#
# docker run -i --rm -p 8080:8080 -p 5005:5005 -e JAVA_DEBUG="true" -e JAVA_DEBUG_PORT="*:5005" quarkus/transactions-service-jvm
#
# This image uses the `run-java.sh` script to run the application.
# This scripts computes the command line to execute your Java application, and
# includes memory/GC tuning.
# You can configure the behavior using the following environment properties:
# - JAVA_OPTS: JVM options passed to the `java` command (example: "-verbose:class")
# - JAVA_OPTS_APPEND: User specified Java options to be appended to generated options
#   in JAVA_OPTS (example: "-Dsome.property=foo")
# - QUARKUS_OPTS: Quarkus options (example: "--help")
#
###
FROM registry.access.redhat.com/ubi8/openjdk-21:1.19

ENV LANGUAGE='en_US:en'

# Configure working directory
USER root
RUN mkdir -p /deployments && chown -R 185:185 /deployments
USER 185

# Copy dependencies and application files
COPY target/quarkus-app/lib/ /deployments/lib/
COPY target/quarkus-app/*.jar /deployments/
COPY target/quarkus-app/app/ /deployments/app/
COPY target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_ENABLE_DEBUG="true"

ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]
