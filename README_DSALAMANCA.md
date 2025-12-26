## Paymeter - Backend Assessment

Este repositorio contiene la solución para el sistema de gestión de parqueaderos de Paymeter. 

La aplicación ha sido modificada de una estructura inicial a una arquitectura por capas, garantizando una mejor separación de responsabilidades y facilidad de mantenimiento.

## Tecnologías utilizadas
Java 17 
Spring Boot 3.2.3
Spring Data JPA para la persistencia.
PostgreSQL 15 como base de datos.
Swagger / OpenAPI 3 para documentación.
Docker & Docker Compose para orquestación.

## Evolución de la Arquitectura

Originalmente, el proyecto presentaba una estructura acoplada. Se realizó una refactorización hacia una Arquitectura por capas, organizada de la siguiente manera:

- Capa de Controladores (Web): Gestiona las peticiones HTTP y la comunicación con el cliente.
- Capa de Servicios (Business): Contiene la lógica de negocio central, separada de la infraestructura.
- Capa de Repositorios (Persistence): Abstracción de las consultas a la base de datos mediante Spring Data JPA.
- Capa de Entidades (Domain): Definición de los modelos de datos y mapeo objeto-relacional.

Este cambio permite que el código sea testeable de forma independiente y facilita la escalabilidad del sistema.

## Requisitos previos

Docker Desktop instalado y en ejecución.

## Instrucciones de Despliegue

La aplicación está totalmente dockerizada. La imagen de la aplicación se encuentra publicada en Docker Hub para facilitar su despliegue inmediato.

Para poder levantar el entorno, desde la raíz del proyecto, ejecuta en una terminal o un powershell (Windows):

 - docker-compose up -d

## Swagger

Una vez levantado el proyecto, los servicios se pueden probar en Swagger UI: 

- http://localhost:8080/swagger-ui/index.html