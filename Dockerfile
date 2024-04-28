FROM openjdk:17-jdk-alpine3.14

COPY "./target/booksystem.jar" "/application/booksystem.jar"

CMD ["java", "-jar", "/application/booksystem.jar"]