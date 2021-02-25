# Dockerfile for NotifyMe-PUSHSERVER
# http://www.ubique.ch/

FROM openjdk:11-jdk

LABEL maintainer="Martin Alig <alig@ubique.ch>"
LABEL maintainer="Tobias Bachmann <bachmann@ubique.ch>"
LABEL maintainer="Severin Kistler <kistler@ubique.ch>"

# Fix bug in debian: missing ca cert: https://bugs.debian.org/cgi-bin/bugreport.cgi?bug=962596
RUN wget --no-check-certificate -c https://www.geotrust.com/resources/root_certificates/certificates/GeoTrust_Global_CA.pem   \
    && mkdir /usr/local/share/ca-certificates/extra                                                                       \
    && mv GeoTrust_Global_CA.pem /usr/local/share/ca-certificates/extra/GeoTrust_Global_CA.crt                            \
    && update-ca-certificates

# Install pushserver
RUN useradd pushserver

WORKDIR /home/pushserver/

# Copy configs
COPY . /home/pushserver

# Create skeleton
RUN mkdir -p /home/pushserver/bin && \
    mkdir -p /home/pushserver/archive && \
    mkdir -p /home/pushserver/log && \
    mkdir -p /home/pushserver/tmp

# Copy binary
ADD https://crayx.ubique.ch/install/ef4ad58f0a15c407eb1fcb52622a4ab5/backend/pushserver/pushserver-standalone-2.4.0-SNAPSHOT.jar /home/pushserver/bin/pushserver.jar

RUN chown -R pushserver:pushserver /home/pushserver

# Access to webinterface
EXPOSE 8080

CMD java -jar $JAVA_OPTS -Dlog4j.configuration=file:/home/pushserver/conf/pushserver-log4j.properties -Dspring.profiles.active=jdbc -Dpushserver.conf.path=/home/pushserver/conf/ -Dfile.encoding=UTF-8 -Dsentry.dsn=$SENTRYDSN -Djavamelody.disabled=true /home/pushserver/bin/pushserver.jar
