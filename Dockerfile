FROM ibmcom/websphere-liberty:kernel-ubi-min
VOLUME ["/tmp/"]
USER root
COPY ./liberty/config/server.xml /config/server.xml
COPY ./target/service-engineer-1.0.0.war /config/dropins/spring/service-engineer-1.0.0.war
RUN chown -R 1001 /config /opt/ibm/wlp/usr/servers/defaultServer /opt/ibm/wlp/usr/shared/resources /opt/ibm/wlp/ && chmod -R g+rw /config /opt/ibm/wlp/usr/servers/defaultServer /opt/ibm/wlp/usr/shared/resources /opt/ibm/wlp/
USER 1001
ARG VERBOSE=true
RUN configure.sh