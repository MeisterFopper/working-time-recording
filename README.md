# Work & Project Time Tracking

A simple **time tracking web app** built with **Spring Boot** and **Thymeleaf**.  
It lets you:

- **Clock in / Clock out** to track working days
- **Track time on projects** during the day
- **Add comments** when finishing a project session
- View **past workdays** with a list of projects and notes
- Explore the REST API via built‑in **Swagger UI**

---

## ✨ Features

- ✅ Start / stop your workday with one click  
- ✅ Start and stop multiple **project time entries** during a workday  
- ✅ Automatically starts a workday if you start a project and forgot to clock in  
- ✅ Add comments when stopping a project  
- ✅ Filter the number of **past days** to show  
- ✅ Clean, responsive UI built with **Bootstrap 5** and **Thymeleaf**  
- ✅ REST API with Swagger UI documentation (`/swagger-ui.html`)

---

## 🚀 Tech Stack

- **Backend:** Java 17, Spring Boot (Web, Data JPA)
- **Database:** H2 / MySQL / PostgreSQL (configurable)
- **Frontend:** Thymeleaf, Bootstrap 5, Vanilla JS
- **Build:** Maven
- **API Docs:** Swagger / springdoc-openapi

---

## ⚙️ Installation & Setup

### 1. Clone the repository

```bash
git clone https://github.com/MeisterFopper/working-time-recording.git
cd working-time-recording
```

### 2. Configure database (optional)

By default, the app uses an in-memory **H2** database.  
To use MySQL or PostgreSQL, adjust `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/worktime
    username: ${MYSQL_WORKTIME_USER}
    password: ${MYSQL_WORKTIME_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```
Use environment variables for DB credentials:

```bash
export MYSQL_WORKTIME_USER=worktime_user
export MYSQL_WORKTIME_PASSWORD=secretpassword
```

### 3. Build & run

```bash
./mvnw spring-boot:run
```

The app will start at:  
👉 `http://localhost:8080`

Swagger UI is available at:  
👉 `http://localhost:8080/swagger-ui.html`

---

## 🗂️ Project Structure

```
src
 ├─ main
 │   ├─ java/com/mrfop/worktime
 │   │   ├─ controller/      # REST + Thymeleaf controllers
 │   │   ├─ model/           # DTOs & Entities
 │   │   ├─ repository/      # Spring Data JPA repos
 │   │   ├─ service/         # Business logic
 │   │   └─ WorktimeApplication.java
 │   └─ resources
 │       ├─ templates/       # Thymeleaf templates (dashboard, layout, etc.)
 │       ├─ static/          # JS, CSS
 │       └─ application.yml
```

---

## 🧑‍💻 API Endpoints

| Method | Endpoint                     | Description                     |
|--------|------------------------------|---------------------------------|
| GET    | `/api/worktime/all`          | Get all workdays                |
| POST   | `/api/worktime/start`        | Start workday                   |
| POST   | `/api/worktime/stop`         | Stop workday                    |
| GET    | `/api/project/active`        | List all active projects        |
| POST   | `/api/projecttime/{id}/start`| Start tracking for project `id` |
| POST   | `/api/projecttime/{id}/stop` | Stop project time (with comment)|

Full interactive docs: **`/swagger-ui.html`**

---

## 🧪 Development Notes

- UI is built using **Thymeleaf** + **Bootstrap**.
- JavaScript dashboard logic is inline in `dashboard.html`.
- Comments when stopping a project are handled with a **Bootstrap modal** instead of browser `prompt()`.

---

## 🛡️ License

This project is licensed under the [MIT License](LICENSE).

---

## 🙌 Contributions

Pull requests and issues are welcome!  
If you have ideas for improvements (e.g., reports, charts, better UI), open an issue or submit a PR.
