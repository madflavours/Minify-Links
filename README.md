# 🚀 Reactive URL Shortener API

A simple reactive REST API built with **Spring Boot** and **Reactive MongoDB**. This project demonstrates a lightweight URL shortening service using non-blocking programming principles.

---

## 🛠 Tech Stack

- **Spring Boot**
- **Reactive MongoDB**
- **Project Reactor**
- **Lombok**

---

## 📦 Requirements

- Java 17+
- Maven or Gradle
- MongoDB (running locally or via cloud e.g. MongoDB Atlas)

---

## 🌐 API Endpoints

Below are the available API endpoints in this service, including request types, parameters, and their purpose.

---

### 🔹 `POST /shorten`

**Description:** Create a new shortened URL.

- **Query Param:** `url` (required) – the original long URL

### 🔹 `GET /shorten/{shortCode}`

**Description:** Get the details about the URL

- **Path Variable** `shortCode` (required) – the shortCode created for the URL

### 🔹 `PUT /shorten/{shortCode}`

**Description:** Updates the URL object with a new URL

- **Path Variable** `shortCode` (required) – the shortCode created for the URL
- **Query Param:** `url` (required) – the new long URL

### 🔹 `DELETE /shorten/{shortCode}`

**Description:** Deletes the URL object

- **Path Variable** `shortCode` (required) – the shortCode created for the URL

### 🔹 `GET /shorten/{shortCode}/stats`

**Description:** Gets the access data for the URL

- **Path Variable** `shortCode` (required) – the shortCode created for the URL

## ▶️ Running the Application

### Using Maven

```bash
./mvnw spring-boot:run



