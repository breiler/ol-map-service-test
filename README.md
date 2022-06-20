# OpenLayers Map Service
A small demo of using OpenLayers together with Spring boot.

## To build and run
```
# Build it
mvn clean install

# Run it
./target/ol-map-service-1.0-SNAPSHOT-exec.jar 
```

Then head over to: http://localhost:8080/


## To develop the frontend

Start the backend according to `To build and run`, then run the following:
```
cd src/main/webapp
npm start
```
Then head over to: http://localhost:1234/