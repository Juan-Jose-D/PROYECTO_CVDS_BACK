# PROYECTO_CVDS_BACK

# Reservation Project - Backend

## Descripción

El *Reservation Project* es un sistema de gestión de reservas de laboratorios para la Decanatura de Ingeniería de Sistemas de la Escuela Colombiana de Ingeniería Julio Garavito. Este repositorio contiene la implementación del backend del sistema, desarrollado en *Spring Boot*, con persistencia en *MongoDB Cloud*.

## Tecnologías Utilizadas

- **Java** (OpenJDK 17.x.x)
- **Spring Boot**
- **Apache Maven** (3.9.x)
- **MongoDB Cloud**
- **Docker**
- **Azure DevOps**
- **SonarQube**
- **Jacoco**
- **GitHub Actions**

## Objetivos

- Implementar una API REST con Spring Boot.
- Manejar bases de datos no relacionales.
- Aplicar inyección de dependencias y control de inversiones (DI/IoC).
- Realizar análisis estático de código y pruebas unitarias.
- Integrar CI/CD en el desarrollo.

## Requerimientos del Usuario

- Consultar la disponibilidad de laboratorios.
- Reservar un laboratorio especificando fecha, hora y propósito.
- Cancelar reservas.

## CI/CD
CI/CD con GitHub Actions asegura calidad y despliegue continuo.
Se han implementado pipelines en GitHub Actions para:

- **Build:** Compilación del código.
- **Test:** Ejecución de pruebas unitarias con cobertura de código en Jacoco.
- **Deploy:** Despliegue en Azure App Service.

## Endpoints API (Backend)
| Método  | Endpoint            | Descripción                      |
|---------|---------------------|----------------------------------|
| GET     | /labs               | Listar laboratorios disponibles |
| POST    | /reservations       | Crear una nueva reserva         |
| GET     | /reservations/{id}  | Consultar reserva por ID        |
| DELETE  | /reservations/{id}  | Cancelar una reserva            |

## Análisis estático de código
![AnalisisEstaticoCompleto](img.png)

## Seguridad

Se ha implementado autenticación y autorización con *Spring Security* para garantizar la protección del sistema.

## Diagrama de clases
![Diagrama de clases Backend](image.png)

## Despliegue
Link del proyecto en azure:
[AzureDevOps Project](https://dev.azure.com/JuanJoseDiazGomez/CVDS_ReservationProject)

Por Angie Ramos, Juan Jose Díaz, David Barbosa y Alexandra Moreno
