FROM ringcentral/jdk:latest
ADD spring-security.jar app.jar
ENV JAVA_OPTS="-Xmx128m -Xms128m"
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Djava.awt.headless=true","-jar", "/app.jar"]