FROM openjdk:11
RUN groupadd -g 2021 hiit_grp && useradd -u 2022 -g hiit_grp -s /bin/sh hiit_user
USER hiit_user:hiit_grp
ARG JAR_FILE=target/my_hiit-*-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prd","-jar","/app.jar"]