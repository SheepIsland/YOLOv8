# Use a base Java image
FROM openjdk:20
FROM maven:3.9

# Set the working directory
WORKDIR /tmp

COPY pom.xml ./
COPY src ./src

RUN mvn clean package

# Expose the port your application is running on
EXPOSE 8080

# Add resource file
COPY build ./build

# Start the application
CMD ["java", "-jar", "-Dai.djl.default_engine=PyTorch", "target/YOLOv8-0.0.1-SNAPSHOT.jar"]