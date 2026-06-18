# UrbanGent Product Service

A microservice for managing men's fashion products. Part of the UrbanGent e-commerce platform.

## Features
- CRUD operations for men's fashion products (Shirts, Trousers, Shoes, Suits, Outerwear, Accessories)
- Input validation with Spring Validation
- PostgreSQL database with JPA/Hibernate
- Swagger/OpenAPI documentation
- Global exception handling
- Dockerized for cloud deployment

## Tech Stack
- Java 17, Spring Boot 3.2
- Spring Data JPA, PostgreSQL
- SpringDoc OpenAPI (Swagger)
- JUnit 5, Mockito
- Docker

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/products` | Create a new product |
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get product by ID |
| DELETE | `/api/products/{id}` | Delete product by ID |

## Running Locally

### Prerequisites
- Java 17+
- Maven 3.9+
- PostgreSQL (or use Docker Compose)

### Start Infrastructure
```bash
docker-compose up -d
```

### Run the Service
```bash
export DB_HOST=localhost DB_PORT=5432 DB_NAME=product_db DB_USER=admin DB_PASSWORD=admin123
mvn spring-boot:run
```

### Run Tests
```bash
mvn test
```

## API Documentation
Swagger UI: http://localhost:8081/swagger-ui.html

## Docker

### Build
```bash
docker build -t urbangent-product-service:latest .
```

### Run
```bash
docker run -p 8081:8081 \
  -e DB_HOST=host.docker.internal \
  -e DB_PORT=5432 \
  -e DB_NAME=product_db \
  -e DB_USER=admin \
  -e DB_PASSWORD=admin123 \
  urbangent-product-service:latest
```

## Cloud Deployment
- **Azure App Service**: https://urbangent-product-gautham.azurewebsites.net/api/products
- **Docker Hub**: kumargautham/urbangent-product-service
