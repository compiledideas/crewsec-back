FROM amazoncorretto:17
EXPOSE 8080
ADD target/crewsec-backend-0.0.1-SNAPSHOT.jar crewsec.jar
ENTRYPOINT ["java","-jar","/crewsec.jar"]