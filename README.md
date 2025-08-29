# 🚀 Word of the Day API (Spring Boot)

This is a Spring Boot REST application that provides a **Word of the Day** feature.
It fetches random words from the **[Random Word API](https://random-word-api.herokuapp.com/word)** and retrieves definitions using **[Dictionary API](https://dictionaryapi.dev/)**.
The results are cached **daily using Caffeine cache** and stored in a MySQL database for history tracking.

---

## 📦 Requirements

* **Java 17+** (or 21 if you use the latest LTS)
* **Maven 3.8+**
* **MySQL** database

---

## ⚙️ Build the Project

From the project root (where `pom.xml` is located), run:

```bash
mvn clean package
```

This will generate a JAR file in the `target/` directory:

```
target/word-of-the-day-api-0.0.1-SNAPSHOT.jar
```

---

## ▶️ Run the Project

Run the generated JAR with:

```bash
java -jar target/word-of-the-day-api-0.0.1-SNAPSHOT.jar
```

By default, the app will start on **[http://localhost:8080](http://localhost:8080)**.

To run on a custom port:

```bash
java -jar target/word-of-the-day-0.0.1-SNAPSHOT.jar --server.port=9090
```

---

## 🌐 Endpoints

### 1️⃣ Get Word of the Day

```http
GET /wordOfTheDay
```

#### Example Request:

```bash
curl "http://localhost:8080/wordOfTheDay"
```

#### Example Response:

```json
{
  "word": "unlaced",
  "responses": [
    {
      "definition": "not under constraint in action or expression",
      "partOfSpeech": "adjective"
    },
    {
      "definition": "with laces not tied",
      "partOfSpeech": "adjective"
    }
  ]
}
```

* The **same word** is returned for the day thanks to Caffeine cache.
* **History** of all words is stored in MySQL.
---

## 📂 Project Structure

```
src/main/java/com/AsadUllah/WordOfTheDay/
│
├── WordOfTheDayApplication.java       # Main Spring Boot application
├── controller/
│   └── WordRestController.java        # REST endpoints
├── service/
│   └── WordService.java               # Business logic + Caffeine caching
├── entity/
│   └── WordEntity.java                # JPA entity for word history
├── mapper/
│   └── WordMapper.java                # Maps Word -> WordEntity
├── repository/
│   └── WordRepository.java            # JPA repository
└── Model/
    ├── Word.java
    ├── Response.java
    └── WordResponse.java

```

---

## 🧪 Features

* Fetches **daily word** with definitions and parts of speech.
* Uses **Caffeine caching** to avoid multiple external API calls.
* Stores **word history** in MySQL.
* Handles missing definitions gracefully.
* Ready for Swagger/OpenAPI documentation.

---

## 📝 License

This project is for **educational purposes** and is free to use.

---
## 👨‍💻 Author

**Asad Ullah**