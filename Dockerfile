FROM openjdk:17

ENV TZ=Asia/Seoul

COPY /build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILES_ACTIVE}", "-Dsentry.dsn=${SENTRY_DSN}", "/app.jar"]
