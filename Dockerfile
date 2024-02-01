FROM openjdk:17-alpine
EXPOSE 8080
ADD target/*.jar socialapp.jar
ENTRYPOINT ["java", "-jar", "/socialapp.jar"]
