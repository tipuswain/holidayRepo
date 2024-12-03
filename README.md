# Federal holidays for(USA, Canada)

This application manages federal holidays for different countries (USA, Canada) using Spring Boot, Spring Data JPA, and an H2 in-memory database.

## Features
- File upload with dataset.
- Add holiday.
- Update holiday.
- Get holidays by countries.
- Delete holiday

## Technologies
- Spring Boot
- h2-console
- Swagger


## Running the Application
1. Set up h2-console and update `application.properties`.
2. Run the application with 
- mvn clean package
3. Access Swagger at `http://localhost:8080/swagger-ui/index.html`.
4. Java version 17

## Testing
Run tests with `mvn test`.

## Endpoints
- **POST /api/holidays/upload**: To Upload a  datasets.
- **POST /api/holidays/**: Add a new holiday.
- **PUT /api/holidays/{id}**: To update holidays details.
- **GET /api/holidays/**: To get holidays details.
- **DELETE /api/holidays/{id}**: To delete holiday.
