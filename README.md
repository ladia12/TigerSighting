# Tiger Sighting Application

This is a sample Tiger Sighting application built with Spring Boot and GraphQL. It allows users to manage tiger sightings and view information about tigers.

## Getting Started

To run the Tiger Sighting application locally, follow these steps:

### Prerequisites

- Java Version: 17
- Apache Maven

### Installation

1. Clone the repository:
2. Navigate to the project directory - (cd tiger-sighting)
3. Build the project using Maven - (mvn clean install)


### Configuration

The application requires a connection to a database. This uses a MySQL database. If you want to use a different database, update the database configuration in the `application.properties` file.
It uses Flyway for database migrations.

### Running the Application

To run the application, execute the following command:
```console
mvn spring-boot:run
```

The application will start and be accessible at `http://localhost:8080`.

### GraphQL Playground

The Tiger Sighting application uses GraphQL for API interactions. You can access the GraphQL Playground at `http://localhost:8080/graphql`. The Playground provides an interactive interface to explore and execute GraphQL queries and mutations.

## Tiger API Documentation

### Creating a Tiger

- **URL:** `/api/tigers`
- **Method:** `POST`
- **Description:** Creates a new tiger.
- **Request Body:**
    - `name`: The name of the tiger
    - `dateOfBirth`: The date of birth of the tiger (format: "yyyy-MM-dd")
    - `lastSeenTimestamp`: The timestamp of the tiger's last sighting (format: "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    - `lastSeenLatitude`: The latitude of the tiger's last sighting
    - `lastSeenLongitude`: The longitude of the tiger's last sighting
- **Response:**
    - `tiger`: The created tiger object
    - `errorMessage`: Error message if the creation fails

### Listing All Tigers

- **URL:** `/api/tigers`
- **Method:** `GET`
- **Description:** Retrieves a paginated list of all tigers.
- **Query Parameters:**
    - `page`: The page number (default: 0)
    - `size`: The number of items per page (default: 10)
- **Response:**
    - An array of tiger objects

### Creating a Tiger Sighting

- **URL:** `/api/tigers/{tigerId}/sightings`
- **Method:** `POST`
- **Description:** Creates a new sighting for a specific tiger.
- **Path Parameters:**
    - `tigerId`: The ID of the tiger
- **Request Body:**
    - `timestamp`: The timestamp of the sighting (format: "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    - `latitude`: The latitude of the sighting
    - `longitude`: The longitude of the sighting
    - `image`: The URL or base64-encoded image of the sighting (optional)
- **Response:**
    - `sighting`: The created sighting object
    - `errorMessage`: Error message if the creation fails

### Listing All Tiger Sightings

- **URL:** `/api/tigers/{tigerId}/sightings`
- **Method:** `GET`
- **Description:** Retrieves a paginated list of all sightings for a specific tiger.
- **Path Parameters:**
    - `tigerId`: The ID of the tiger
- **Query Parameters:**
    - `page`: The page number (default: 0)
    - `size`: The number of items per page (default: 10)
- **Response:**
    - An array of sighting objects

Please note that you may need to customize the base URL (`/api`) based on your application's configuration.







   









