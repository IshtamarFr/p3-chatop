# Chatop

This project was generated with [Java Spring](https://spring.io) version 2.7.15.

## Requires

env.properties file with some settings:
(this file is .gitignored for safety and privacy reasons)

DB_DATASOURCE=localhost:3306/p3_ocr  
DB_USERNAME=username  
DB_PASSWORD=password  
SERVER_PORT=3001  

This project requires a [MariaDB database](https://mariadb.com/kb/en/)

## Database

Run the [script_db.sql] to create the database (uncomment first line to drop table if it already exists)
Create an username/password for this database only and GRANT ALL PRIVILEGES on that database

## Development server

Run as `Spring Boot App` for a dev server. Navigate to `http://localhost:3001/`.
Use a software like [Postman](https://www.postman.com) to test your Endpoints.

## Build

Run `mvn package / mvn install` to build the jar for the project. The build artifacts will be stored in the `target/` directory, and can be served into your server folder (Apache, Nginx, etc.)

## Swagger

API docs are available at local [default settings](http://localhost:3301/api/swagger-ui/) `http://localhost:3301/api/swagger-ui/`

## How it works

This server is the Back-end part for Chatop project, for which Front-end part is available on [Github](https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring.git)