# Use a base Java image
FROM openjdk:20

# Set the working directory
WORKDIR /tmp

# Copy the JAR file into the container
COPY target/YOLOv8-0.0.1-SNAPSHOT.jar /tmp

# Expose the port your application is running on
EXPOSE 8080

#Add resource file
COPY build ./build

# Start the application
CMD ["java", "-jar", "-Dai.djl.default_engine=PyTorch", "YOLOv8-0.0.1-SNAPSHOT.jar"]