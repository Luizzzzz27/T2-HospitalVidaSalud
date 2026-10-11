# 🏥 Hospital Vida Salud - Arquitectura de Microservicios

[![CI Microservicios](https://github.com/Luizzzzz27/T2-HospitalVidaSalud/actions/workflows/ci.yml/badge.svg)](https://github.com/Luizzzzz27/T2-HospitalVidaSalud/actions/workflows/ci.yml)

Sistema distribuido para la gestión hospitalaria desarrollado con **Spring Boot 3.x**, **Spring Cloud**, **Spring Data R2DBC** (reactivo no bloqueante), mensajería asíncrona mediante **RabbitMQ** y un pipeline de **Integración Continua (CI)** con **GitHub Actions**.

---

## 🏛️ 1. Arquitectura del Sistema

El ecosistema está compuesto por 6 microservicios desacoplados:

| Microservicio | Puerto | Base de Datos / Broker | Responsabilidad Principal |
| :--- | :--- | :--- | :--- |
| **`ms-eureka-server`** | `8761` | N/A | Servidor de descubrimiento y registro dinámico de instancias. |
| **`ms-api-gateway`** | `8080` | N/A | Pasarela perimetral de enrutamiento y balanceo de carga reactivo. |
| **`ms-paciente`** | `8081` | MySQL (`db_paciente`) | Gestión reactiva de pacientes y control de unicidad de documento. |
| **`ms-cita`** | `8082` | MySQL (`db_cita`) / RabbitMQ | Gestión reactiva de citas, validación síncrona vía `WebClient` y publicación de eventos. |
| **`ms-atencion`** | `8083` | MySQL (`db_atencion`) | Registro reactivo del acto médico/atención. |
| **`ms-notificacion`** | `8084` | RabbitMQ | Consumidor asíncrono (`@RabbitListener`) de confirmación de citas. |
| **`ms-pago`** | `8085` | MySQL (`db_pago`) | Gestión reactiva de pagos con validación estricta de importes. |

---

## 🛠️ 2. Stack Tecnológico

- **Lenguaje:** Java 17 (LTS)
- **Framework Principal:** Spring Boot 3.x
- **Patrón Reactivo:** Spring WebFlux & Project Reactor
- **Acceso a Datos:** Spring Data R2DBC (MySQL Reactive Driver)
- **Descubrimiento y Gateway:** Spring Cloud Netflix Eureka & Spring Cloud Gateway
- **Mensajería Asíncrona:** Spring Boot Starter AMQP / RabbitMQ
- **Bases de Datos:** MySQL Server 8.0
- **Testing:** JUnit 5 & Mockito
- **CI/CD:** GitHub Actions (`ubuntu-latest`, JDK 17)

---

## 🚀 3. Guía de Ejecución Local

### Prerrequisitos

1. **JDK 17** instalado y configurado en el PATH.
2. **MySQL Server** en ejecución (puerto 3306).
3. **RabbitMQ Broker** en ejecución (puerto 5672).

### Paso 1: Configurar las Bases de Datos

Ejecutar los scripts SQL incluidos en el repositorio mediante MySQL Workbench:

- `init-db.sql` (Crea los esquemas `db_paciente`, `db_cita`, `db_atencion`, `db_pago`).
- `fase2-db.sql` (Crea las tablas `paciente` y `cita`).
- `fase3-db.sql` (Crea las tablas `atencion` y `pago`).

### Paso 2: Orden de Inicio de los Microservicios

Para garantizar la resolución de nombres y registro en Eureka:

1. `ms-eureka-server` (http://localhost:8761)
2. `ms-api-gateway` (http://localhost:8080)
3. `ms-paciente` (http://localhost:8081)
4. `ms-cita` (http://localhost:8082)
5. `ms-atencion` (http://localhost:8083)
6. `ms-notificacion` (http://localhost:8084)
7. `ms-pago` (http://localhost:8085)

---

## 📡 4. Catálogo de Endpoints (Vía API Gateway :8080)

### Pacientes (`ms-paciente`)

- `POST /api/v1/pacientes`: Registra un paciente. Valida unicidad de documento (`HTTP 400` si está duplicado).
- `GET /api/v1/pacientes/{id}`: Obtiene paciente por ID.

### Citas (`ms-cita`)

- `POST /api/v1/citas`: Registra cita en estado `PENDIENTE`. Consulta síncronamente a `ms-paciente` mediante `WebClient`.
- `PUT /api/v1/citas/{id}/confirmar`: Actualiza estado a `CONFIRMADA` y emite evento a RabbitMQ (`q.citas.confirmadas`).

### Atenciones (`ms-atencion`)

- `POST /api/v1/atenciones`: Registra atención médica con estado `ATENDIDA`.

### Pagos (`ms-pago`)

- `POST /api/v1/pagos`: Registra pago con estado `COMPLETADO`. Rechaza transacciones con monto `<= 0` (`HTTP 400 Bad Request`).

---

## 🔄 5. Integración Continua (GitHub Actions)

El repositorio cuenta con un pipeline automatizado (`.github/workflows/ci.yml`) que protege la rama `main` ejecutando compilación y pruebas unitarias (`mvn test`) ante cada `push`:

- **Endpoint protegido por CI:** `POST /api/v1/pagos` (`ms-pago`).
- **Prueba unitaria:** `PagoValidationTest.java`.
- **Comprobación de ciclo CI:**
  1. `Verde`: Compilación y pruebas unitarias iniciales aprobadas.
  2. `Rojo (Fallo controlado)`: Detección automática de error de aserción en `ms-pago` con código de salida 1 (`BUILD FAILURE`).
  3. `Verde`: Restauración y validación exitosa del pipeline.

---

## 👨‍💻 Autor

- **Estudiante:** Luis Angel Gonzales Arguedas
- **Curso:** Desarrollo de Aplicaciones Web II
- **Ciclo:** 6to Ciclo - Cibertec
