FROM openjdk:8
EXPOSE 8080
ADD target/crewsec.jar crewsec.jar
ENTRYPOINT ["java","-jar","/crewsec.jar"]