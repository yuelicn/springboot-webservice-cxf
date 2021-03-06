FROM harbor.tusdao.com/library/java:8
ADD build/libs/*.jar app.jar
COPY /src/main/profile/prod/application.yml /
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar","--spring.config.location=/application.yml"]