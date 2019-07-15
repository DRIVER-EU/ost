A docker image is available on dockerhub: https://hub.docker.com/r/drivereu/ost-app

Image with database to store backend for OST:
    image: drivereu/ost-app:ost_database
    ports:
    - "5437:5432" <-- database needs to work on port 5437
    volumes:
    - ost-database:/var/lib/postgresql/data <-- volume to save database
    restart: always

Image with frontend and nginx configuration:
    image: drivereu/ost-app:ost_frontend
    links:
    - api <-- needs to be connected to OST backend (Apache Tomcat service)
    ports:
    - "84:80" <-- we are starting our application on port 84
    restart: always

Image with backend (Apache Tomcat service):
    image: drivereu/ost-app:ost_backend
    environment:
      KAFKA_BROKER_URL: broker:9092 <-- backend needs to have mapping to testbed broker on internal port 9092
    depends_on:
    - db <-- backend is using Postgres to store data and services
    ports:
    - "8084:8080" <-- backend should work on port 8084
    restart: always

Additional info:
OST is searching for kafka broker on port 3501 and schema registry on port 3502 (this is configured inside ost_backend container: /opt/config/)
Please note that the current version requires a manual restart of the backend server, since when the backend service starts, the DB isn't ready yet.

Build project with:
docker-compose up -d

Wait about 2min and restart ost_backend container:
docker ps | grep ost_backend
docker restart [CONTAINER ID]
This will return the container id of ost_backend service
docker restart [CONTAINER ID]
This will restart ost_backend container

Using the OST
Open a browser and visit the OST home page with 127.0.0.:84.
DO NOT use localhost:84, as this will not work - the GUI then tries to connect to an internal service running at ITTI, which is not accessible.
To login, use the following credentials: admin19/adminpass
