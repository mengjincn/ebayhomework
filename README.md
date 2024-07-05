# User Management System

This Spring Boot application provides a simple user management system with role-based access control. It allows administrators to add user access rights and users to access specific resources based on their permissions.

## Features

- Role-based authentication using Base64 encoded headers
- Admin functionality to add user access rights
- User access control for specific resources
- File-based persistence for user access information
- Standardized API responses for easier client-side handling
  
## Requirements

- JDK 21
- Maven 3.6 or higher

## API Endpoints

### 1. Add User Access (Admin only)

- **URL**: `/admin/addUser`
- **Method**: POST
- **Auth Required**: Yes (Admin role)
- **Headers**:
  - `Authorization`: Base64 encoded user information
- **Body**:
  ```json
  {
    "userId": 123456,
    "endpoints": ["resource A", "resource B", "resource C"]
  }
  ```
- **Success Response**:
  - **Code**: 200
  - **Content**: 
    ```json
    {
      "code": 200,
      "message": "User access added successfully"
    }
    ```
- **Error Response**:
  - **Code**: 403
  - **Content**: 
    ```json
    {
      "code": 403,
      "message": "Access denied: Admin role required"
    }
    ```

### 2. Access Resource (User)

- **URL**: `/user/{resource}`
- **Method**: GET
- **Auth Required**: Yes
- **Headers**:
  - `Authorization`: Base64 encoded user information
- **URL Params**: 
  - `resource`: The resource the user is trying to access
- **Success Response**:
  - **Code**: 200
  - **Content**: 
    ```json
    {
      "code": 200,
      "message": "Access granted to {resource}"
    }
    ```
- **Error Response**:
  - **Code**: 403
  - **Content**: 
    ```json
    {
      "code": 403,
      "message": "Access denied to {resource}"
    }
    ```

## Authentication

All requests must include an `Authorization` header with a Base64 encoded JSON string containing the user's information. The decoded JSON should have the following format:

```json
{
  "userId": 123456,
  "accountName": "username",
  "role": "admin" or "user"
}
```

## Error Handling

All API responses follow a standard format:

```json
{
  "code": 400,
  "message": "Error description"
}
```

The `code` field contains the HTTP status code, and the `message` field provides a description of the error.

## Setup and Running

1. Ensure you have Java 11 and Maven installed on your system.
2. Clone the repository to your local machine.
3. Navigate to the project directory.
4. Run the following command to start the application:
   ```
   mvn spring-boot:run
   ```
5. The application will start and be available at `http://localhost:8080`.

## Testing

You can test the API endpoints using tools like cURL, Postman, or any HTTP client. Remember to include the `Authorization` header with the Base64 encoded user information in your requests.

## Notes

- This system uses file-based persistence for storing user access information. In a production environment, you might want to consider using a database for better scalability and performance.
- Ensure that you handle the Base64 encoded headers securely in your client applications to prevent unauthorized access.

For any issues or feature requests, please open an issue in the project repository.
