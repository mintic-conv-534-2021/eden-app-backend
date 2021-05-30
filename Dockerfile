FROM mcr.microsoft.com/java/jre:11-zulu-alpine

WORKDIR /app

COPY target/*.jar ./app.jar

#ENV PATH=/app/app.jar

EXPOSE 8080

CMD ["sh", "-c", "echo ${PROFILE}"]
CMD ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "app.jar"]
