FROM openjdk:11-jre-slim
WORKDIR /app

COPY ./build/libs/dynamodb-issue-version.jar /app/dynamodb-issue.jar

EXPOSE 8080
CMD ["java", "-jar", "dynamodb-issue.jar"]
