# 💳 Bank API - Prueba Técnica

Este es un sistema bancario básico desarrollado con **Spring Boot**, **PostgreSQL** y **Angular**. Implementa operaciones CRUD para clientes, cuentas y movimientos, además de un **trigger en la base de datos** que actualiza el saldo automáticamente.

## 🛠️ Tecnologías usadas

- **Backend:** Spring Boot 3, JPA, PostgreSQL, Docker
- **Frontend:** Angular 16, Bootstrap
- **Base de datos:** PostgreSQL 15
- **Orquestación:** Docker Compose

---

## 🚀 Instrucciones para correr

1. Clona el repositorio y correr el siguiente comando para levantar los contenedores:

docker-compose up --build

2.- Acceder a los servicios:

Backend: 

http://localhost:8081/api/clientes
http://localhost:8081/api/cuentas
http://localhost:8081/api/movimientos

Frontend: http://localhost:4200

PostgreSQL: localhost:5432

Usuario: postgres
Contraseña: dannyalejo7123tapia

⚙️ Funcionalidad del Trigger
Cada vez que se crea, actualiza o elimina un movimiento, se ejecuta automáticamente un trigger SQL (schema.sql) que recalcula el saldo de la cuenta correspondiente según el tipo de movimiento (Deposito o Retiro).

Autor
Daniel Tapia
