
# 🏦 Microservicio Bancario CRUD

## Descripción del Proyecto

Este proyecto es un **Microservicio CRUD** (Crear, Leer, Actualizar, Eliminar) desarrollado con **Spring Boot 3** para gestionar la información de entidades bancarias.

La aplicación sigue las mejores prácticas de una API REST moderna, utilizando **DTOs** (Objetos de Transferencia de Datos) para manejar la entrada y salida de información de manera estructurada, y el patrón **Builder** para la creación limpia de entidades.

### Características Clave

* **RESTful API:** Endpoints para la gestión de bancos (`/api/v1/bancos`).
* **Seguridad por Roles:** Implementación de **Spring Security** para proteger *endpoints* con roles (**ADMIN**, **USER**).
* **Paginación Eficiente:** El *endpoint* `GET /api/v1/bancos` soporta paginación y ordenamiento.
* **Manejo de Excepciones:** Respuestas HTTP estandarizadas (404 Not Found, 409 Conflict) para errores de negocio.
* **Documentación Interactiva:** Uso de **Swagger UI** para la documentación de la API.

-----

## 🛠️ Tecnologías

* **Lenguaje:** Java 21
* **Framework:** Spring Boot 3.x
* **Persistencia:** Spring Data JPA + H2 Database (modo en memoria por defecto)
* **Seguridad:** Spring Security 6+
* **Utilidades:** Lombok, SpringDoc OpenAPI (Swagger)

-----

## 🚀 Ejecución Local del Proyecto

Para ejecutar la aplicación localmente, asumiendo que utilizas Maven y una base de datos en memoria (H2) o una base de datos configurada en `application.properties`.

### 1\. Requisitos

* Java 21 JDK
* Maven

### 2\. Compilación y Ejecución

Desde el directorio raíz del proyecto, compila y ejecuta la aplicación:

```bash
# Empaqueta la aplicación 
mvn clean install -DskipTests

# Ejecuta el JAR generado
java -jar target/banco-microservicio-crud-0.0.1-SNAPSHOT.jar
```

El microservicio estará activo en `http://localhost:8080`.

-----

## 🛡️ Prueba de Endpoints (Seguridad y Roles)

Todos los *endpoints* están protegidos con **HTTP Basic**. Debes usar el encabezado de autenticación con las siguientes credenciales configuradas en memoria:

| Usuario | Contraseña | Rol | Permisos |
| :--- | :--- | :--- | :--- |
| **admin** | `adminpass` | `ADMIN` | CRUD Completo (POST, PUT, DELETE, GET) |
| **user** | `userpass` | `USER` | Solo Lectura (GET) |

### 1\. Obtener Token o Acceder a la API

Para probar, usa `curl` y el comando `-u user:password`.

#### A. Crear un Banco (`POST`) - Solo ADMIN

```bash
# Debería funcionar (Status 201 Created)
curl -u admin:adminpass -X POST http://localhost:8080/api/v1/bancos \
  -H 'Content-Type: application/json' \
  -d '{
    "nombre": "Banco Principal",
    "codigoBic": "BPCLBIC",
    "fechaFundacion": "2010-01-01",
    "capitalSocial": 12000000.00,
    "esNacional": true
  }'
```

#### B. Obtener Bancos Paginados (`GET`) - ADMIN o USER

```bash
# Funciona con paginación (ej. página 0, 5 elementos, ordenado por nombre descendente)
curl -u user:userpass -X GET 'http://localhost:8080/api/v1/bancos?page=0&size=5&sort=nombre,desc'
```

> **Prueba de Denegación:** Intentar un `DELETE` con el usuario `user` resultará en `403 Forbidden`.

-----

## 📄 Documentación de la API

Una vez que la aplicación esté en ejecución, se puede acceder a la documentación interactiva de Swagger:

[http://localhost:8080/swagger-ui.html]
