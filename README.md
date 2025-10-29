# ZarifTool Backend

## Overview
ZarifTool is an AI-powered physiotherapy and posture assessment application designed to help users detect postural issues such as kyphosis, lordosis, and forward head posture using computer vision.
This repository contains the backend of ZarifTool, built using Spring Boot (Java).
The backend handles all user management, authentication, data storage, and API communication with the mobile frontend (built with React Native).

## Features

### 🔐 Authentication & Security
* Secure user signup and login using JWT (JSON Web Token).
* Passwords securely stored with BCrypt hashing.
* Protected endpoints with Spring Security authorization.

### 💪 Exercise & Progress Management

* Assign and update personalized exercise plans for each user.
* Track user progress and generate improvement reports.
* Store and retrieve progress reports and AI posture analysis results.

### 🧑‍⚕️ Physiotherapist Recommendations
* Search and retrieve nearby physiotherapists based on user location.
* Each physiotherapist includes contact info, location, and working hours.

### Posture Assessment Storage
* Integration with Cloudinary to store and manage user-uploaded posture images.
* AI-generated results saved and tracked over time.

### Additional Functionalities
* Support for multiple postural problems per user.
* Reward system with prizes to encourage user engagement.
* RESTful API design for easy frontend integration.

## Project Structure
src/main/java/com/project/physio_backend/

│

├── Config/                # Security and app configurations

├── Controllers/           # REST API endpoints (User, Physiotherapist, Exercise, etc.)

├── Entities/              # Core models (User, Progress, Exercise, Report, Problem, etc.)

├── Exceptions/            # Global and custom exception handling

├── Repositories/          # JPA repositories for database access

├── Services/              # Business logic and service layer

├── payload/               # DTOs and request/response models

├── security/              # JWT authentication and authorization setup

│

├── PhysioBackendApplication.java

└── TokenRetriever.java

## Testing
Comprehensive unit and integration tests were implemented using JUnit and Mockito.

Example:
UserControllerTest.java includes tests for:
* GET /api/users – Fetch all users
* POST /api/users – Create new user
* GET /api/users/{id} – Retrieve user by ID
* PUT /api/users/{id} – Update user data
* DELETE /api/users/{id} – Delete user and verify response

## Database Design

### Technology Used
* MySQL (relational database)
* Spring Data JPA for ORM and repository layer

### Key Entities
* Users: personal data, credentials, and history
* Exercises: corrective routines assigned to users
* Progress: tracks user improvement and completion
* Problems: 20+ posture problems with symptoms, causes, and solutions
* Physiotherapists: registered professionals with contact and location details
* Reports: posture analysis summaries
* Prize: motivational system for consistent progress

### View the entity relationships and diagrams here:
👉 [Figma Database Diagram](https://www.figma.com/board/3QeplB2hdBpIWQH0unRwUu/Diagrams-for-physiO?node-id=0-1&p=f&t=TbMJ8RSAdRBvxdlt-0) 

## Technologies Used
* Backend Framework	Spring Boot
* Security	Spring Security, JWT, BCrypt
* Database	MySQL, JPA (Hibernate)
* Cloud Storage	Cloudinary API
* Testing	JUnit 5, Mockito, MockMvc, RestAssured
* API Style	RESTful
* Language	Java 17+

## How It Works
* Users sign up and log in securely via JWT authentication.
* The app collects user details (age, weight, posture photo, etc.).
* AI model analyzes posture and sends results to the backend.
* Backend stores posture data, suggests exercises, and tracks progress.
* Users can monitor improvement, earn rewards, and connect with physiotherapists.

## Demo 
https://www.linkedin.com/posts/tala-aldibs_a-short-demo-video-that-highlights-the-key-ugcPost-7335760947183079426-p80p?utm_source=share&utm_medium=member_desktop&rcm=ACoAAD-5uvIB2giyjYESgALM5bT9piitHroAgOo 

## 🧑‍💻 Team
* Zainab Atwa
* Tala Al-Dibs
* Nour Alhuda Abunassar

## Project Context
Developed as part of a university software engineering project at Bethlehem University, focused on applying AI, computer vision, and health technology to support better posture and well-being.

## License
This project is for educational and research purposes.
All rights reserved © 2025 — ZarifTool Team.
