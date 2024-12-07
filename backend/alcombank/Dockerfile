# Start with an official JDK image
FROM openjdk:23-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml files to the working directory
COPY .mvn/ .mvn
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .

# Copy the source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Specify the jar file name based on the generated file
ARG JAR_FILE=target/alcombank-1.jar
COPY ${JAR_FILE} app.jar

# Expose the port the app runs on
EXPOSE 8080

# Set the entry point to run the jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]