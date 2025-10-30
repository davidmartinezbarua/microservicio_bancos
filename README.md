# 🏦 Microservicio Bancario CRUD

## Descripción del Proyecto

Este proyecto es un **Microservicio CRUD** (Crear, Leer, Actualizar, Eliminar) desarrollado con **Spring Boot 3** para gestionar la información de entidades bancarias.

La aplicación sigue las mejores prácticas de una API REST moderna, utilizando DTOs (Objetos de Transferencia de Datos) para manejar la entrada y salida de información de manera estructurada, y el patrón Builder para la creación limpia de entidades.

### Características Clave ✨

  * **RESTful API:** Endpoints para la gestión de bancos (`/api/v1/bancos`).
  * **Seguridad por Roles (HTTP Basic):** Implementación de **Spring Security** para proteger *endpoints* con roles (**ADMIN**, **USER**).
  * **Paginación Eficiente:** El *endpoint* `GET /api/v1/bancos` soporta paginación y ordenamiento.
  * **Manejo de Excepciones:** Respuestas HTTP estandarizadas (404 Not Found, 409 Conflict).
  * **Documentación Interactiva:** Uso de **Swagger UI** para la documentación de la API.

-----

## 🛠️ Tecnologías

  * **Lenguaje:** Java 21
  * **Framework:** Spring Boot 3.x
  * **Persistencia:** Spring Data JPA + H2 Database (modo en memoria por defecto)
  * **Seguridad:** Spring Security 6+
  * **Testing:** JUnit 5, Mockito, Spring Boot Test
  * **Utilidades:** Lombok, SpringDoc OpenAPI (Swagger)

-----

## 🚀 Ejecución Local del Proyecto

Para ejecutar la aplicación localmente, asumiendo que utilizas Maven.

### 1\. Requisitos

  * Java 21 JDK
  * Apache Maven

### 2\. Opciones de Ejecución

Puedes elegir entre ejecutar directamente desde Maven (ideal para desarrollo) o ejecutar el JAR empaquetado (ideal para producción/despliegue).

| Propósito | Comando | Nota |
| :--- | :--- | :--- |
| **Desarrollo (Recomendado)** | `mvn spring-boot:run` | Ejecuta la aplicación sin generar el JAR. |
| **Producción (JAR)** | `java -jar target/banco-microservicio-crud-0.0.1-SNAPSHOT.jar` | Requiere ejecutar `mvn clean install` previamente. |

El microservicio estará activo en `http://localhost:8080`.

-----

## 🧪 Ejecución de Pruebas

El proyecto incluye pruebas unitarias y pruebas de integración para validar la lógica y la capa de controladores.

### Ejecutar Todas las Pruebas

Para ejecutar todas las pruebas (`@Test`) del proyecto:

```bash
mvn clean install
```

### Ejecutar Solo las Pruebas

Para ejecutar solo las pruebas sin recompilar el proyecto ni generar el JAR:

```bash
mvn test
```

-----

## 🛡️ Prueba de Endpoints (HTTP Basic)

Todos los *endpoints* están protegidos con **HTTP Basic**. Las credenciales deben enviarse en el encabezado `Authorization`.

| Usuario | Contraseña | Rol | Permisos |
| :--- | :--- | :--- | :--- |
| **admin** | `adminpass` | `ADMIN` | CRUD Completo (POST, PUT, DELETE, GET) |
| **user** | `userpass` | `USER` | Solo Lectura (GET) |

### 1\. Crear un Banco (`POST`) - Solo ADMIN

```bash
# Ejemplo: Creación exitosa (Status 201 Created)
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

### 2\. Obtener Bancos Paginados (`GET`) - ADMIN o USER

```bash
# Ejemplo: Consulta con paginación (Status 200 OK)
curl -u user:userpass -X GET 'http://localhost:8080/api/v1/bancos?page=0&size=5&sort=nombre,desc'
```

-----

## 📄 Documentación de la API

Una vez que la aplicación esté en ejecución, se puede acceder a la documentación interactiva de Swagger, la cual es de acceso público:

http://localhost:8080/swagger-ui.html
