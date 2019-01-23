# SpringBoot Assessment Application
To retrieve the Product List which has the valid price reductions in a category using multiple usecases

## Requirements
* Oracle Java Runtime 1.8 or higher
* Eclipse Photon

## To Build the application

You don’t need to build from source to use Spring Boot (binaries in repo.spring.io), but if you want to try out the latest and greatest, Spring Boot can be easily built with the maven wrapper. You also need JDK 1.8.

    ./mvnw clean install
    
## To Run the application

Run the below command to start the application once "Build Successful" message is displayed

    mvn spring-boot:run
    
## The application on Tomcat server (embedded) localhost, port: 8080

    [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
    [           main] com.imlewis.AssessmentApplication        : Started AssessmentApplication in 7.224 seconds (JVM running for 8.535)

## Execution URLs:

    Generic URL: http://localhost:8080/priceReductionList?labelType={labelType}
   
    Specific: 
      - http://localhost:8080/priceReductionList?labelType=ShowWasNow
      - http://localhost:8080/priceReductionList?labelType=ShowWasThenNow
      - http://localhost:8080/priceReductionList?labelType=ShowPercDscount
 
 
