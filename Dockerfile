FROM registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift:latest
LABEL maintainer="KEN20667@kcbgroup.com"
VOLUME /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.322.b06-2.el8_5.x86_64/jre/lib/security/
ENV PORT 8181
COPY target/*.jar /opt/app.jar
WORKDIR /opt
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar