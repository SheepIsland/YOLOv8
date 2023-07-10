# Use a base Java image
FROM openjdk:20

# Set the working directory
WORKDIR /tmp

# Copy the JAR file into the container
COPY target/YOLOv8-0.0.1-SNAPSHOT.jar /tmp

# Copy the Maven wrappers and pom file from the host machine to the container image.
# The pom.xml file contains information of project and configuration information for the maven to build the
# project such as dependencies, build directory, source directory, test source directory, plugin, goals etc.
# COPY .mvn/ ./.mvn
# COPY mvnw pom.xml ./

# Trigger a goal that resolves all project dependencies including plugins and reports and their dependencies.
# RUN ./mvnw dependency:go-offline

# Expose the port your application is running on
EXPOSE 8080

# Add resource file
COPY build ./build

# Copy the most important directory of the maven project – /src. It includes java source code and pre-environment
# configuration files of the artifact.
# COPY src ./src

# Start the application
CMD ["java", "-jar", "-Dai.djl.default_engine=PyTorch", "YOLOv8-0.0.1-SNAPSHOT.jar"]
# CMD ["./mvnw", "spring-boot:run"]