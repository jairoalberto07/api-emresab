# ♻️ API REST - Gestión de Inventario de Polímeros (ERS-EMRESAB S.A.S)

![Java](https://img.shields.io/badge/Java-8-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-brightgreen?style=flat-square&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)
![Swagger](https://img.shields.io/badge/OpenAPI-Swagger%20UI-85EA2D?style=flat-square&logo=swagger)

Backend empresarial desarrollado en Java con Spring Boot y MySQL para el registro, control, validación y trazabilidad de lotes de polímeros reciclables (**HDPE**, **PP**, **LDPE**) de **ERS-EMRESAB S.A.S**.

---

## 🚀 Características Principales

Operaciones CRUD completas: Creación, consulta general, búsqueda por identificador, actualización y eliminación de lotes de material.
Filtrado por tipo de polímero: Búsqueda optimizada por categoría de plástico reciclable (`HDPE`, `PP`, `LDPE`).
Validaciones de negocio (`Bean Validation`): Restricciones a nivel de entidad para impedir registros sin tipo de material, sin punto de acopio o con pesos negativos/en cero.
Manejo global de excepciones (`@RestControllerAdvice`): Respuestas JSON estructuradas y legibles ante errores de validación (`HTTP 400 Bad Request`).
Documentación interactiva (Swagger / OpenAPI 3): Interfaz gráfica integrada para probar todos los endpoints directamente desde el navegador.
Trazabilidad automática:** Asignación automática de fecha y hora de ingreso (`@PrePersist`) al almacenar cada lote en MySQL.

---

## 🛠️ Stack Tecnológico

Lenguaje: Java 8 (JDK 1.8)
Framework: Spring Boot 2.7.18
Módulos Spring: Spring Web, Spring Data JPA, Spring Boot Validation
ORM / Persistencia: Hibernate
Base de Datos: MySQL (`emresab_db`)
Documentación API: SpringDoc OpenAPI UI (`1.7.0`)
Gestor de Dependencias: Maven

---

## 📂 Arquitectura del Proyecto

```text
src/main/java/api_emresab/
├── config/
│   └── OpenApiConfig.java           # Configuración de portada y metadatos de Swagger UI
├── controller/
│   └── MaterialController.java      # Endpoints REST y documentación @Operation / @Tag
├── exception/
│   └── GlobalExceptionHandler.java  # Captura centralizada de errores de validación
├── model/
│   └── Material.java                # Entidad JPA con restricciones @NotBlank y @Positive
├── repository/
│   └── MaterialRepository.java      # Interfaz JpaRepository y consultas personalizadas
└── ApiApplication.java              # Clase principal de arranque Spring Boot
```

---

## 📌 Endpoints de la API REST

| Método | Endpoint | Descripción | Código de Respuesta |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/materiales` | Obtiene el listado completo de lotes registrados | `200 OK` |
| `GET` | `/api/materiales/{id}` | Consulta un lote específico por su ID | `200 OK` / `404 Not Found` |
| `GET` | `/api/materiales/buscar?tipo=HDPE` | Filtra los lotes por tipo de polímero | `200 OK` |
| `POST` | `/api/materiales` | Valida y registra un nuevo lote en MySQL | `200 OK` / `400 Bad Request` |
| `PUT` | `/api/materiales/{id}` | Actualiza los datos de un lote existente | `200 OK` / `400` / `404` |
| `DELETE` | `/api/materiales/{id}` | Elimina un registro del inventario por ID | `204 No Content` / `404` |

### Ejemplo de Cuerpo de Petición (`POST` / `PUT`)

```json
{
  "tipoPolimero": "HDPE",
  "pesoKilos": 250.5,
  "origen": "Acopio Sincé Centro"
}
```

### Ejemplo de Respuesta ante Error de Validación (`400 Bad Request`)

```json
{
  "pesoKilos": "El peso en kilos debe ser mayor a cero",
  "tipoPolimero": "El tipo de polímero es obligatorio (ej. HDPE, PP, LDPE)",
  "origen": "El origen o punto de acopio es obligatorio"
}
```

---

## 📖 Documentación Interactiva (Swagger UI)

Con el servidor en ejecución, accede desde cualquier navegador a la interfaz de pruebas interactiva:

Swagger UI: `http://localhost:8080/swagger-ui.html`
Especificación OpenAPI (JSON):** `http://localhost:8080/v3/api-docs`

---

## ⚙️ Configuración e Instalación Local

1. Clonar el repositorio:
   ```bash
   git clone [https://github.com/jairoalberto07/api-emresab.git](https://github.com/jairoalberto07/api-emresab.git)
   cd api-emresab
   ```

2. Crear la base de datos en MySQL:
   ```sql
   CREATE DATABASE emresab_db;
   ```

3. Configurar la conexión en `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/emresab_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   spring.datasource.username=root
   spring.datasource.password=TU_CONTRASEÑA
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

4. Compilar y ejecutar la aplicación:
   Desde NetBeans ejecutando `ApiApplication.java` (`Shift + F6`) o mediante Maven en consola:
   ```bash
   mvn spring-boot:run
   ```