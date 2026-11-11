# ♻️ API REST - Gestión de Inventario de Polímeros (ERS-EMRESAB S.A.S)

API REST corporativa desarrollada en Java con Spring Boot y MySQL para la gestión, control y trazabilidad de lotes de polímeros reciclables (**HDPE**, **PP**, **LDPE**).

## 🚀 Tecnologías y Herramientas

* **Lenguaje:** Java 8 (JDK 1.8)
* **Framework:** Spring Boot 2.7.18 (Spring Web, Spring Data JPA)
* **Base de Datos:** MySQL (Base de datos: `emresab_db`)
* **ORM / Persistencia:** Hibernate / JPA
* **Gestor de Dependencias:** Maven
* **IDE de Desarrollo:** NetBeans

## 📌 Endpoints de la API REST

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/materiales` | Consulta el listado completo de inventario |
| `GET` | `/api/materiales/{id}` | Obtiene los detalles de un lote por su ID |
| `GET` | `/api/materiales/buscar?tipo=HDPE` | Filtra los lotes por tipo de polímero |
| `POST` | `/api/materiales` | Registra un nuevo lote de material |
| `PUT` | `/api/materiales/{id}` | Actualiza los datos de un lote registrado |
| `DELETE` | `/api/materiales/{id}` | Elimina un registro de la base de datos |

## ⚙️ Configuración e Instalación Local

1. Clonar el repositorio:
   ```bash
   git clone [https://github.com/TU_USUARIO/api-emresab.git](https://github.com/TU_USUARIO/api-emresab.git)