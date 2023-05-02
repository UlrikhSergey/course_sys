FROM openjdk:20
ADD /target/demo10-0.0.1-SNAPSHOT.jar demo10-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","demo10-0.0.1-SNAPSHOT.jar"]
