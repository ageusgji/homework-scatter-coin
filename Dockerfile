FROM openjdk:11

LABEL maintainer="ageusgji@gmail.com"

VOLUME /tmp

EXPOSE 8888

ADD build/libs/*.jar coinScatterApp.jar

RUN bash -c 'touch ./coinScatterApp.jar'
ARG SPRING_PROFILES_ACTIVE
RUN echo $SPRING_PROFILES_ACTIVE
ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE

ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/coinScatterApp.jar"]
