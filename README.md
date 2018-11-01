# Backend for the IDEXX assignment

Requires Maven to be installed on the machine.

Run mvn spring-boot:run to start up the server.

The search endpoint is exposed at http://localhost:8080/api/search?searchTerm={searchTerm}

## Operational
Health check can be found at http://localhost:8080//actuator/health

Generic Metrics can be found at http://localhost:8080/actuator/metrics

For upstream requests the metrics can be found here ( assuming at least one request was fired )

http://localhost:8080/actuator/metrics/external.search.googleApi

http://localhost:8080/actuator/metrics/external.search.itunes
