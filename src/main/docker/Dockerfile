FROM openjdk:8-jdk-alpine
VOLUME /tmp
RUN echo "Asia/Shanghai" > /etc/timezone
ADD demo-0.0.1-SNAPSHOT.jar demo.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/demo.jar"]