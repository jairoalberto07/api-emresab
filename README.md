# ♻️ ERS-EMRESAB S.A.S - API REST de Gestión y Transformación de Polímeros

![Java](https://img.shields.io/badge/Java-1.8-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.7.18-brightgreen.svg)
![MySQL](https://img.shields.io/badge/MySQL-Database-orange.svg)
![CI/CD](https://img.shields.io/badge/CI%2FCD-GitHub_Actions-2088FF.svg)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI_3-85EA2D.svg)

## 📖 Descripción del Proyecto
Sistema backend corporativo diseñado para **ERS-EMRESAB S.A.S**, orientado a la gestión de inventarios y trazabilidad en la industria de la economía circular. La API permite registrar el acopio de polímeros reciclables (HDPE, PP, LDPE) y gestionar su transformación industrial en postes agrícolas (madera plástica).

El núcleo del sistema incluye un algoritmo contable tipo **FIFO (First In, First Out)**, el cual deduce automáticamente el material consumido del inventario global de forma transaccional, garantizando la integridad de los datos de manufactura.

## 🚀 Características Principales
*   **Arquitectura Multicapa:** Separación estricta de responsabilidades (Controllers, Services, Repositories, DTOs).
*   **Gestión de Inventario:** Entradas, salidas y cálculo dinámico de métricas de peso por tipo de polímero.
*   **Módulo de Transformación Segura:** Algoritmo FIFO respaldado por la anotación `@Transactional` de Spring, previniendo saldos negativos en base de datos si ocurre una interrupción.
*   **Manejo Global de Excepciones:** Uso de `@RestControllerAdvice` para interceptar errores de validación de negocio (`IllegalArgumentException`) y validaciones de DTOs (`@Valid`), retornando JSON estructurados (HTTP 400).
*   **Documentación Interactiva:** Interfaz Swagger/OpenAPI integrada para pruebas de endpoints.
*   **Integración Continua (CI):** Flujo de GitHub Actions automatizado con Maven para compilar y ejecutar pruebas unitarias en cada *push* a la rama principal.

## 🛠️ Stack Tecnológico
**Backend:**
*   **Lenguaje:** Java 8
*   **Framework:** Spring Boot 2.7.18
*   **Persistencia:** Spring Data JPA (Hibernate)
*   **Base de Datos:** MySQL
*   **Testing:** JUnit 5, Mockito
*   **Gestor de Dependencias:** Maven
*   **IDE:** Apache NetBeans

**Frontend Integrado (Panel de Control):**
*   HTML5, Vanilla JavaScript (Fetch API), Bootstrap 5.

## ⚙️ Instalación y Despliegue Local

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/jairoalberto07/api-emresab.git](https://github.com/jairoalberto07/api-emresab.git)
   cd api-emresab
   Configurar la Base de Datos:
2. Crear una base de datos en MySQL llamada emresab_db. Hibernate generará las tablas materiales y produccion_postes automáticamente.
Configurar credenciales en src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/emresab_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update

3. Compilar y Ejecutar:
   mvn clean install
mvn spring-boot:run
4. Acceder a la Documentación (Swagger):
Navegar a: http://localhost:8080/swagger-ui.html
Pruebas Unitarias

El proyecto cuenta con cobertura de pruebas en la capa de servicios (MaterialServiceTest y ProduccionPosteServiceTest), utilizando Mockito para aislar la base de datos. Se validan escenarios de éxito (normalización de datos, cálculo de totales) y reglas de negocio críticas (rechazo de producción por inventario insuficiente).

Para ejecutar los tests manualmente:
mvn test
