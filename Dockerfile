FROM eclipse-temurin:21-jdk-alpine
 
RUN mkdir -p /opt /sandbox
 
RUN wget -q https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.2/junit-platform-console-standalone-1.10.2.jar \
    -O /opt/junit.jar
 
WORKDIR /sandbox