FROM java
VOLUME /tmp
ADD spring-mvc.jar app.jar
#RUN sh -c 'touch /app.jar'
#ENV JAVA_OPTS=""
# ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]