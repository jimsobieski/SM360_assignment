# SM360 Tech Assignment
SM360 Tech Assignment is a Java 17 project that aims to provide a web application for managing car listings and dealers.

## Features
Create, read, update and delete listings and dealers
Publish and unpublish listings
A tier system that limits the number of published listings per dealer
## Technologies
- Spring Boot
- Hibernate
- Swagger
- Docker
## Requirements
- Java 17
- PostgreSQL
## Installation

### Database
Create a docker container
```sh
docker run --name sm360-db -e POSTGRES_PASSWORD=postgres -d postgres
```
Connect in your database server with postgres user and run this script
```sh
CREATE USER sm360 WITH ENCRYPTED PASSWORD 'techassignment';
CREATE DATABASE sm360 WITH ENCODING = 'UTF8' OWNER = 'sm360';
GRANT CONNECT ON DATABASE sm360 TO sm360;
GRANT USAGE ON SCHEMA public TO sm360;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO sm360;
```
As default 
```sh
<database.name>=sm360
<database.username>=sm360
<database.password>=techassignment
```
### Application
Clone the repository
```sh
git clone https://github.com/jimsobieski/SM360_assignment.git
```
Navigate to the project directory
Open the file application.properties and set your database properties if necessary
```sh
spring.datasource.url=jdbc:postgresql://<database.host>:<database.port>/<database.name>
spring.datasource.username=<database.username>
spring.datasource.password=<database.userpassword>
```
Build the project
```sh
mvn clean install
```
Run the project by running the java class TechassignmentApplication or executing the following code
```sh
mvn spring-boot:run
```
### Usage
After starting the application, you can access the Swagger documentation at http://localhost:8080/swagger-ui.html