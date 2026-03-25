# Customer Service

Servicio de gestión de clientes para el sistema bancario. Este microservicio permite crear, leer, actualizar y eliminar clientes (personales y empresariales).

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 4.0.4** (WebFlux para programación reactiva)
- **Spring Cloud Config** (Cliente de configuración centralizada)
- **Spring Cloud Netflix Eureka** (Cliente de servicio de descubrimiento)
- **MongoDB Reactive** (Base de datos NoSQL reactiva)
- **OpenAPI 3.0** (Especificación y generación de código API)
- **Resilience4j** (Patrones de resiliencia)
- **RxJava 3** (Programación reactiva)
- **Lombok** (Reducción de código boilerplate)
- **Swagger** (Documentación API interactiva)

## Herramientas de Calidad de Código

- **JaCoCo** (Cobertura de código)
- **SonarQube** (Análisis de calidad)
- **Checkstyle** (Estilo de código)
- **Spotless** (Formateo automático con Google Java Format)

## Prerrequisitos

- Java 17
- Maven 3.6+
- MongoDB
- Config Server (puerto 8888)
- Eureka Server

## Configuración

El servicio se configura a través de Spring Cloud Config. Los archivos de configuración se ubican en el servidor de configuración.

Configuración básica en `application.yaml`:
- Nombre del servicio: `customer-service`
- Puerto por defecto: 8081
- Base de datos: MongoDB (configurable vía config server)

## Ejecución

### Ejecutar localmente

1. Asegurarse de que MongoDB esté ejecutándose
2. Iniciar el Config Server en puerto 8888
3. Iniciar el Eureka Server
4. Ejecutar el servicio:

```bash
mvn spring-boot:run
```

### Ejecutar con Maven

```bash
mvn clean install
mvn spring-boot:run
```

### Ejecutar con Docker

```bash
docker build -t customer-service .
docker run -p 8081:8081 customer-service
```

## API

La API REST está documentada con OpenAPI 3.0 y se puede acceder a la documentación Swagger en:
`http://localhost:8081/swagger-ui.html`

### Endpoints principales

- `GET /` - Obtener todos los clientes
- `POST /` - Crear un nuevo cliente
- `GET /{id}` - Obtener cliente por ID
- `PUT /{id}` - Actualizar cliente
- `DELETE /{id}` - Eliminar cliente

### Modelo de datos

**Customer:**
- `id`: String (UUID)
- `type`: String (PERSONAL/BUSINESS)
- `documentType`: String
- `documentNumber`: String
- `fullname`: String
- `email`: String (opcional)
- `phone`: String (opcional)

## Pruebas

### Ejecutar pruebas unitarias

```bash
mvn test
```

### Cobertura de código

```bash
mvn jacoco:report
```

Los reportes de JaCoCo se generan en `target/site/jacoco/index.html`

## Análisis de Calidad

### Checkstyle

```bash
mvn checkstyle:check
```

### SonarQube

```bash
mvn sonar:sonar
```

Configurado para conectarse a SonarQube en `http://localhost:9095`

## Generación de Código

El código de la API se genera automáticamente desde la especificación OpenAPI:

```bash
mvn openapi-generator:generate
```

## Logs

Configuración de logs en `log4j2-spring.xml`. Los logs se escriben en consola y archivos según el perfil activo.

## Arquitectura

- **Controlador**: Delegado generado por OpenAPI
- **Servicio**: Lógica de negocio (CustomerServiceImpl)
- **Repositorio**: Acceso a datos reactivo con MongoDB
- **Configuración**: Centralizada vía Spring Cloud Config
- **Descubrimiento**: Registrado en Eureka Server

## Contribución

1. Seguir las reglas de Checkstyle y Spotless
2. Mantener cobertura de código > 10%
3. Actualizar la especificación OpenAPI si se modifican endpoints
4. Ejecutar todas las pruebas antes de commit</content>
<parameter name="filePath">C:\ProjectoBootcampF\Final\customer-service\customer-service\README.md
