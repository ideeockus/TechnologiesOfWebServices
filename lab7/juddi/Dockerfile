FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y wget unzip

# https://downloads.apache.org/tomcat/tomcat-10/v10.1.18/bin/apache-tomcat-10.1.18.tar.gz
ARG TOMCAT_VERSION=10.1.18
RUN wget https://downloads.apache.org/tomcat/tomcat-10/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz -O /tmp/tomcat.tar.gz \
    && tar xvfz /tmp/tomcat.tar.gz -C /opt \
    && mv /opt/apache-tomcat-${TOMCAT_VERSION} /opt/tomcat \
    && rm /tmp/tomcat.tar.gz

ARG JUDDI_VERSION=3.1.0
RUN mkdir -p /opt/juddi && wget https://archive.apache.org/dist/juddi/3_1_0/juddi-dist-3.1.0-dist.zip -O /tmp/juddi.zip
RUN unzip /tmp/juddi.zip -d /opt/juddi
RUN rm /tmp/juddi.zip

RUN ls -l /opt/juddi/juddi-dist-${JUDDI_VERSION}

RUN wget https://archive.apache.org/dist/juddi/3_1_0/juddi-tomcat-3.1.0.zip -O /opt/tomcat/webapps/juddi_{JUDDI_VERSION}.zip
# RUN cp /opt/juddi/juddi-dist-${JUDDI_VERSION}/juddiv3-tomcat-${JUDDI_VERSION}.zip /opt/tomcat/webapps

EXPOSE 8080

# Запустите Apache Tomcat
CMD ["/opt/tomcat/bin/catalina.sh", "run"]
