FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD target/quartzDemo-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-Duser.timezone=Asia/Kolkata","-jar","/myapp.jar"]
