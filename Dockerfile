FROM tcox42/eclipse-temurin-11
WORKDIR /social-media-app
EXPOSE 8081
ADD /target/SocialApp-0.0.1-SNAPSHOT.jar SocialApp.jar
CMD ["java", "-jar", "SocialApp.jar"]