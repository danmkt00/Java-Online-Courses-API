# 🧩 PROJECT 6 — Online Courses API (Teacher + Student Roles)

A REST API for managing online courses with **JWT authentication** and **role-based access** using **Spring Boot** and **Spring Data JPA**.

Teachers can create, update, and delete their own courses.  
Students and public users can only view courses.

---

## 🚀 Features

- JWT Authentication + Role-based access:
  - **TEACHER**: full CRUD on *their own* courses  
  - **STUDENT**: read-only  
  - **Unauthenticated**: read-only  
- Ownership enforcement:
  - Only the course owner teacher can edit or delete their course
- Public read access for all courses

---

## 🧩 Endpoints

### PUBLIC (Unauthenticated or Student)

| Method | Endpoint                | Description        |
|--------|--------------------------|--------------------|
| GET    | /courses/public          | View all courses   |

---

### TEACHER (Authenticated)

| Method | Endpoint           | Description             |
|--------|---------------------|-------------------------|
| POST   | /courses            | Create a new course     |
| PUT    | /courses/{id}       | Update *own* course     |
| DELETE | /courses/{id}       | Delete *own* course     |

Teachers can also use all public GET endpoints.
