FROM adoptopenjdk/openjdk11:alpine
ARG JAR_FILE=/build/libs/PassnetOrganizer.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "/app.jar", "--spring.profiles.active=${PROFILE}"]