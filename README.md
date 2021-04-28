# ContractorData

## Requirments
To run this project you will need a postgres database set up on your local machine with port 5454 exposed
The database will either need to be named "demodb" or you may name it something other but you will need to alter the "application.yml" file

> you will need to replace {demodb} with your database name
>> url: jdbc:postgresql://localhost:5454/{demodb}

> application.yml can be found here
>> src/main/resources/application.yml
> 

- You will need to have Java installed on your [machine](https://www.oracle.com/java/technologies/javase-downloads.html)
- You will also need maven installed on your [machine](https://maven.apache.org/download.cgi) 

---
## Run the project
After requirements are met you will be able to run the project.

First you will need to cd into the root directory of the project.

Following that you can use the following command to run the project 

```
mvn spring-boot:run
```
After a successful compilation you can see or add data using postman or other tools with the following web address.
> http://localhost:8080/api/v1/employee
>
> or
> 
> http://localhost:8080/api/v1/house
>
>or
>
>http://localhost:8080/api/v1/pay