# 🚀 Backend Spring Boot - Portal Uniremington

API REST desarrollada con **Spring Boot 3.4** y **Java 17**, conectada a MySQL/MariaDB y con autenticación JWT.

---

## 📦 Tecnologías

- ☕ Java 17
- 🍃 Spring Boot 3.4.1
- 🔒 Spring Security + JWT (jjwt 0.12.6)
- 🗄️ Spring Data JPA + Hibernate
- 🐬 MySQL Connector
- 🛠️ Lombok

---

## ⚙️ Configuración

### 1. Base de Datos

Asegúrate de tener MySQL/MariaDB corriendo con la BD `veterinaria_db` creada.

Edita `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/veterinaria_db
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_AQUI
```

### 2. Variables importantes

```properties
# JWT - CAMBIA esto en producción
app.jwt.secret=uniremington-al-parque-2026-secret-key-super-segura
app.jwt.expiration-ms=86400000

# CORS - URL del frontend Angular
app.cors.allowed-origins=http://localhost:4200
```

---

## 🚀 Cómo Ejecutar

### Opción 1: Con Maven Wrapper
```bash
./mvnw spring-boot:run
```

### Opción 2: Con Maven instalado
```bash
mvn spring-boot:run
```

### Opción 3: Generar JAR ejecutable
```bash
mvn clean package
java -jar target/portal-backend-1.0.0.jar
```

El servidor iniciará en: **http://localhost:8080**

---

## 🔐 Usuarios Pre-cargados

El `DataSeeder` se ejecuta al iniciar y crea/actualiza estos usuarios:

| Email | Password | Rol |
|-------|----------|-----|
| `admin@uniremington.edu.co` | `Admin2026*` | ADMIN |
| `docente@uniremington.edu.co` | `Docente2026*` | TEACHER |
| `estudiante@uniremington.edu.co` | `Estudiante2026*` | STUDENT |
| `veterinario@uniremington.edu.co` | `Vet2026*` | VETERINARIAN |

⚠️ Las contraseñas se hashean automáticamente con BCrypt al ejecutar.

---

## 📡 Endpoints

### 🔓 Públicos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/auth/login` | Iniciar sesión |
| POST | `/api/auth/register` | Registrarse |
| POST | `/api/auth/forgot-password` | Recuperar contraseña |
| GET | `/api/noticias` | Listar noticias |
| GET | `/api/foros` | Listar foros |

### 🔒 Autenticados (Bearer token)

#### Usuarios (Solo ADMIN)
- `GET /api/usuarios`
- `GET /api/usuarios/{id}`
- `POST /api/usuarios`
- `PUT /api/usuarios/{id}`
- `PATCH /api/usuarios/{id}/estado`
- `DELETE /api/usuarios/{id}`

#### Docentes (ADMIN, TEACHER)
- `GET /api/docentes`
- `POST /api/docentes`
- `PUT /api/docentes/{id}`
- `DELETE /api/docentes/{id}`

#### Estudiantes (ADMIN, TEACHER)
- `GET /api/estudiantes`
- `POST /api/estudiantes`
- `PUT /api/estudiantes/{id}`
- `DELETE /api/estudiantes/{id}`

#### Eventos (Autenticado)
- `GET /api/eventos`
- `POST /api/eventos`
- `PUT /api/eventos/{id}`
- `DELETE /api/eventos/{id}`

#### Noticias (CRUD: Autenticado)
- `POST /api/noticias`
- `PUT /api/noticias/{id}`
- `DELETE /api/noticias/{id}`

#### Foros
- `POST /api/foros`
- `POST /api/foros/{id}/respuestas`
- `DELETE /api/foros/{id}`

---

## 📋 Ejemplo de Uso

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@uniremington.edu.co",
    "password": "Admin2026*"
  }'
```

Respuesta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "expiresIn": 86400000,
  "user": {
    "id": "...",
    "email": "admin@uniremington.edu.co",
    "fullName": "Administrador Principal",
    "role": "ADMIN"
  }
}
```

### Petición protegida
```bash
curl -X GET http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

## 🐛 Solución de Problemas

### Error: "Access denied for user 'root'@'localhost'"
→ Verifica `spring.datasource.username` y `spring.datasource.password` en `application.properties`

### Error: "Unknown database 'veterinaria_db'"
→ Crea la BD primero: `CREATE DATABASE veterinaria_db;`

### Error: "Table doesn't exist"
→ Con `spring.jpa.hibernate.ddl-auto=update`, Hibernate creará las tablas automáticamente.
→ Para forzar recreación, usa `create-drop` (¡borra datos!)

### Error CORS desde Angular
→ Verifica que `app.cors.allowed-origins` incluya `http://localhost:4200`

---

## 🏗️ Estructura del Proyecto

```
src/main/java/co/edu/uniremington/portal/
├── PortalApplication.java          # Main
├── config/
│   ├── SecurityConfig.java         # Spring Security + CORS
│   └── DataSeeder.java             # Carga usuarios iniciales
├── security/
│   ├── JwtService.java             # Genera/Valida JWT
│   └── JwtAuthFilter.java          # Filtro de auth en cada request
├── entity/                         # Entidades JPA
│   ├── Usuario.java
│   ├── Role.java (enum)
│   ├── News.java
│   ├── ForumTopic.java
│   ├── ForumReply.java
│   └── VeterinaryJourney.java
├── dto/                            # DTOs (mapeo español ↔ inglés)
│   ├── auth/
│   ├── usuario/
│   ├── docente/
│   ├── estudiante/
│   ├── noticia/
│   ├── evento/
│   └── foro/
├── repository/                     # Spring Data JPA
├── service/                        # Lógica de negocio
└── controller/                     # REST Controllers
```

---

## 🎯 Conectar con el Frontend Angular

En el frontend, edita `src/environments/environment.ts`:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

¡Listo! Inicia ambos proyectos y ya puedes probar el sistema completo.

---

## 📅 Fecha
Enero 2026
