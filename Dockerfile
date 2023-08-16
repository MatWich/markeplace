FROM openjdk:17 as BUILDER

ENV APP_HOME=/usr/src/marketplace
RUN microdnf install findutils
WORKDIR $APP_HOME

COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
RUN ./gradlew build 2>/dev/null || true
COPY . .
RUN ls
CMD ./gradlew build && ls build/libs && pwd


#app
FROM openjdk:17
ENV APP_HOME=/usr/src/marketplace
ENV APP_NAME=marketplace-0.0.1-SNAPSHOT.jar
WORKDIR $APP_HOME
COPY --from=BUILDER $APP_HOME/build/libs/$APP_NAME .
CMD chmod +x $APP_NAME
EXPOSE 8080
CMD java -jar ./${APP_NAME}



