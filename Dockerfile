FROM amazoncorretto:17.0.7-alpine

ARG APPLICATION_USER=compiledideas
RUN adduser --no-create-home -u 1000 -D $APPLICATION_USER

# Configure working directory
RUN mkdir /app && \
    chown -R $APPLICATION_USER /app

USER 1000

COPY --chown=1000:1000 target/crewsec-backend-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app

EXPOSE 8080
# ADD target/crewsec-backend-0.0.1-SNAPSHOT.jar crewsec.jar
ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]