FROM openjdk:11
ADD phonebook-api/build/libs/*.jar phonebook.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "phonebook.jar"]
