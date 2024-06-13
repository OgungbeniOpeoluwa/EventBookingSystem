FROM maven:3.8.7 as build
COPY . .
RUN mvn -B clean package -DskipTests

FROM openjdk:21
COPY --from=build ./target/*.jar eventBooking.jar
ENTRYPOINT ["java", "-jar","eventBooking.jar"]