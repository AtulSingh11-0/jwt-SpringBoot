# Spring Boot JWT Authentication

This is a Spring Boot project demonstrating JWT (JSON Web Token) authentication.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Endpoints](#endpoints)

## Introduction

This project showcases how to implement JWT authentication in a Spring Boot application. It provides endpoints for user registration and authentication using JWT tokens.

## Features

- User registration and login with JWT authentication
- JWT token generation and authentication
- Stateless authentication using JWT
- Customizable security configuration
- Session management
  
* Password encryption using BCrypt
* Role-based authorization with Spring Security

## Requirements

- Java Development Kit (JDK) 17 or higher
- Maven 3 or higher
- MySQL or any other database for storing user data

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/AtulSingh11-0/jwt-SpringBoot.git
    ```

2. Navigate to the project directory:

    ```bash
    cd jwt-SpringBoot
    ```

3. Build the project:

    ```bash
    mvn clean install
    ```

## Configuration

1. Configure the database connection in `application.properties`:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/mydatabase
    spring.datasource.username=myuser
    spring.datasource.password=mypassword
    ```

2. Configure other application properties as needed.

## Usage

1. Run the application:

    ```bash
    mvn spring-boot:run
    ```
    Your application will be live and runnning at http://localhost:8080.

2. Access the endpoints as described in the [Endpoints](#endpoints) section.

## Endpoints

- `POST /api/v1/auth/register`: Register a new user.
- `POST /api/v1/auth/login`: Authenticate a user and generate JWT token.
- `GET /api/v1/demo/hello`: This route can only be accessed after authentication
- `GET /api/v1/unsecured/hello`: This route doesnt need any authentication to be accessed

