#FROM mediasol/openjdk21-slim-jprofiler
##CMD ./gradlew clean
#CMD ./gradlew build
#VOLUME /tmp
#ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]