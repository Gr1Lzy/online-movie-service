FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/online-movie-service-0.0.1-SNAPSHOT.jar app.jar
COPY .env .env
RUN apt-get update && apt-get install -y dos2unix && dos2unix .env
RUN apt-get install -y curl && curl -sL https://deb.nodesource.com/setup_14.x | bash - && apt-get install -y nodejs && npm install -g dotenv-cli
EXPOSE 8081
CMD ["dotenv", "-e", ".env", "java", "-jar", "app.jar"]
