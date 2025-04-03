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

# Tecnologías utilizadas en el proyecto 🚀  

## Java (OpenJDK 17.x.x)  
**Lenguaje de programación** utilizado para desarrollar la lógica del backend. OpenJDK 17 es una versión de largo soporte (LTS) que mejora el rendimiento y la seguridad.  

🔹 **Función en el proyecto:**  
- Implementación de la lógica de negocio.  
- Manejo de peticiones HTTP en la API.  
- Conexión con MongoDB y otras tecnologías.

![image](https://github.com/user-attachments/assets/60ed5aa3-0f33-492a-b1fd-93c0c3cb381c)


## Spring Boot  
**Framework para Java** que simplifica la creación de aplicaciones web y servicios RESTful.  

🔹 **Función en el proyecto:**  
- Creación de la API REST.  
- Inyección de dependencias (DI).  
- Gestión de conexiones con MongoDB.  
- Seguridad y autenticación con Spring Security.  
![image](https://github.com/user-attachments/assets/f5e0ed30-1e71-4fbd-b07c-6e484feea678)


## Apache Maven (3.9.x)  
 **Herramienta de gestión de dependencias y automatización de compilación** para proyectos Java.  

🔹 **Función en el proyecto:**  
- Manejo de librerías mediante el archivo `pom.xml`.  
- Compilación y empaquetado del backend.  
- Ejecución de pruebas con `mvn test`.  
![image](https://github.com/user-attachments/assets/e49f4b81-3b08-43bd-a300-c641282fbc2e)


## MongoDB Cloud  
**Base de datos NoSQL** basada en documentos, alojada en la nube con **MongoDB Atlas**.  

🔹 **Función en el proyecto:**  
- Almacenamiento de usuarios, reservas y laboratorios.  
- Escalabilidad y flexibilidad en la gestión de datos.
![image](https://github.com/user-attachments/assets/3525d9bd-908f-40d5-815e-268e40d16639)


## Docker  
**Plataforma para la creación y ejecución de contenedores** que encapsulan la aplicación y sus dependencias.  

🔹 **Función en el proyecto:**  
- Creación de imágenes del backend y frontend.  
- Ejecución en cualquier entorno sin conflictos de dependencias.  
![image](https://github.com/user-attachments/assets/dd26a598-f1bb-4251-aa71-07660fe32b62)


## Azure DevOps  
**Plataforma de integración y despliegue continuo (CI/CD)** utilizada para la gestión del código y automatización del desarrollo.  

🔹 **Función en el proyecto:**  
- Gestión del código en repositorios.  
- Ejecución de pipelines de integración y despliegue.  
- Monitoreo del estado del proyecto.  
![image](https://github.com/user-attachments/assets/926ffc05-8670-4250-809f-48653745c955)


## SonarQube  
**Herramienta de análisis estático de código** que detecta errores, vulnerabilidades y código duplicado.  

🔹 **Función en el proyecto:**  
- Escaneo de calidad del código fuente.  
- Revisión de estándares y buenas prácticas en Java.  
- Generación de reportes sobre seguridad y mantenimiento del código.  
![image](https://github.com/user-attachments/assets/f3d5eb7b-ca77-4bd5-9529-0653abccf191)


## Jacoco  
**Herramienta para medir la cobertura de pruebas unitarias en Java**.  

🔹 **Función en el proyecto:**  
- Generación de reportes de cobertura en el backend.  
- Verificación de que al menos **80% del código** esté cubierto por pruebas.  
![image](https://github.com/user-attachments/assets/01b5dbf6-71b9-44aa-b559-700ba014f926)


## GitHub Actions  
**Servicio de automatización de workflows** dentro de GitHub para CI/CD.  

🔹 **Función en el proyecto:**  
- Automatización de pruebas con cada push o pull request.  
- Construcción y despliegue del backend en Azure y del frontend en Vercel.  
- Generación de reportes de calidad de código con SonarQube.  
![image](https://github.com/user-attachments/assets/badb3ebf-a4e5-44a7-9adb-90ec53071dc3)


## Resumen  
Estas tecnologías trabajan juntas para garantizar que el proyecto sea **escalable, seguro y de alta calidad**. 🚀  


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
