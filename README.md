# Movie Database and Query System

## Project Overview

This project is a movie database management system that allows users to add, query, and manage actors, movies, directors, awards, and relationships between them. It also includes the functionality to calculate the shortest connection path between actors based on their movie collaborations.

## Features

### API Endpoints:
- **Add Actor**: Adds actors to the database.
- **Add Movie**: Adds movies to the database.
- **Add Director**: Adds directors to the database.
- **Add Awards**: Links awards to actors.
- **Add Relationships**: Establishes relationships between actors, directors, and movies.
- **Get Actor**: Retrieves actor information, including the movies they've acted in.
- **Get Movie**: Retrieves movie information.
- **Get Director**: Retrieves director information.
- **Find Shortest Path Between Actors**: Finds the shortest path between two actors based on shared movies.

## Technologies Used

- **Backend**: Java with JUnit for unit tests.
- **Database**: Neo4j for managing actor, movie, and relationship data.
- **Testing**: JUnit for testing API endpoints.

---

## API Endpoints Documentation

### PUT Requests

#### `PUT /api/v1/addActor`
- **Description**: Adds an actor node to the database.  
- **Body Parameters**:
  - `name`: String (e.g., `"Denzel Washington"`)
  - `actorId`: String (e.g., `"nm1001213"`)
- **Expected Responses**:
  - `200 OK`: Successful addition.
  - `400 BAD REQUEST`: Invalid or missing body parameters.
  - `500 INTERNAL SERVER ERROR`: If the addition failed.

---

#### `PUT /api/v1/addMovie`
- **Description**: Adds a movie node to the database.  
- **Body Parameters**:
  - `name`: String (e.g., `"Parasite"`)
  - `movieId`: String (e.g., `"nm7001453"`)
- **Expected Responses**:
  - `200 OK`: Successful addition.
  - `400 BAD REQUEST`: Invalid or missing body parameters.
  - `500 INTERNAL SERVER ERROR`: If the addition failed.

---

#### `PUT /api/v1/addRelationship`
- **Description**: Adds an `ACTED_IN` relationship between an actor and a movie in the database.  
- **Body Parameters**:
  - `actorId`: String
  - `movieId`: String
- **Expected Responses**:
  - `200 OK`: Relationship successfully added.
  - `400 BAD REQUEST`: Invalid or missing body parameters.
  - `404 NOT FOUND`: Actor or movie not found.
  - `500 INTERNAL SERVER ERROR`: If the addition failed.

---

### GET Requests

#### `GET /api/v1/getActor`
- **Description**: Retrieves actor information, including the movies they’ve acted in.  
- **Body Parameters**:
  - `actorId`: String
- **Expected Responses**:
  - `200 OK`: Actor found.
  - `404 NOT FOUND`: Actor not found.
  - `500 INTERNAL SERVER ERROR`: Internal server error.

---

#### `GET /api/v1/getMovie`
- **Description**: Retrieves movie information.  
- **Body Parameters**:
  - `movieId`: String
- **Expected Responses**:
  - `200 OK`: Movie found.
  - `404 NOT FOUND`: Movie not found.
  - `500 INTERNAL SERVER ERROR`: Internal server error.

---

#### `GET /api/v1/findActorConnection`
- **Description**: Finds the shortest connection between two actors based on their movie collaborations.  
- **Body Parameters**:
  - `actorId`: String (starting actor)
  - `targetActorId`: String (target actor to find the shortest connection to)
- **Expected Responses**:
  - `200 OK`: Connection found.
  - `404 NOT FOUND`: Actor or connection not found.
  - `500 INTERNAL SERVER ERROR`: Internal server error.

---

## Setup and Prerequisites

### Prerequisites:
- Neo4j database running on `localhost:7474`
- Java 8+ installed.
- Apache Maven for building and running the project.
