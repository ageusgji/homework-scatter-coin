FROM openjdk:11

LABEL maintainer="ageusgji@gmail.com"

VOLUME /tmp

EXPOSE 8888

ADD build/libs/*.jar app.jar


ENTRYPOINT ["java","-jar","/app.jar"]
