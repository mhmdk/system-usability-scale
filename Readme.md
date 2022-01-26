![unit tests](https://github.com/mhmdk/system-usability-scale/actions/workflows/main.yml/badge.svg)

## System Usability Scale

a [system usability scale](https://www.usability.gov/how-to-and-tools/methods/system-usability-scale.html) calculator
spring boot implementation  

### How To Run
- Build and run locally:  
  `mvn spring-boot:run`  This requires java11 and maven
- Or alternatively run the docker image:  
  `docker run -d -p8080:8080 mkamar/system-usability-scale`

By default, the server will listen to HTTP requests on port 8080
