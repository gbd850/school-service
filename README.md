# school-service
## About
School service is a Java Spring Boot project created to practice JPA skills using Spring Data JPA.
Project provides secured API with JWT authorization and authentication to freely manage data.

## Run the application
Clone the repository and cd into it, then perform
```
$ mvnw spring-boot:run
```
The application runs on `localhost:8080`
## Specification
### /register endpoint
This endpoint registers a new user in a system.
```http
POST https://localhost:8080/register HTTP/1.1
Content-type: application/json;

{
  "username": "string",
  "password": "string"
}

Response:

{
  "id": 0,
  "username": "string",
  "password": "string",
  "role": "USER",
  "accountNonLocked": true,
  "enabled": true,
  "authorities": [
    {
      "authority": "string"
    }
  ],
  "accountNonExpired": true,
  "credentialsNonExpired": true
}

```
### /login endpoint
This user authenticates and authorizes a user and returns a JWT for the given user.
```http
POST https://localhost:8080/login HTTP/1.1
Content-type: application/json;

{
  "username": "string",
  "password": "string"
}

Response:

string
```
### Tech Stack
* Java 17
* Spring Boot 3
* Spring Data JPA
* Spring Web
* Spring Security
* SpringDoc
* MySQL
