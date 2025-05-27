# Challange Twitter (BackEnd)

![Version Badge](https://img.shields.io/badge/0.0.1_SNAPSHOT-green?style=flat&label=Version)
![Version Badge](https://img.shields.io/badge/No-red?style=flat&label=Activo)


## Índice

* [Integrantes](#integrantes)

* [Arquitectura](#arquitectura)

* [Docker](#docker)

* [Dependencias](#dependencias)

* [Wiki](#wiki)

* [Convenciones](#convenciones)

<!--suppress ALL -->
<hr/>

# Integrantes

| <center>Nombre</center> | <center>Mail</center> | <center>GitHub</center>                                                                                                          |
|:------------------------|:----------------------|:---------------------------------------------------------------------------------------------------------------------------------|
| **Esteban Peña**        | tatas323@gmail.com    | <img align="center" src="https://github.com/espenia.png" height=32 width=32 /> [espenia](https://github.com/espenia)             |

<hr width="30%" align="left" />

# Arquitectura

El proyecto sigue una arquitectura de Clean Architecture con separación clara de responsabilidades:

## Capas de la Aplicación

1. **Entrypoint (Controllers)**: Maneja las solicitudes HTTP y respuestas.
   - Ubicación: `src/main/java/twitter/challenge/espenia/entrypoint/`
   - Componentes: `UserController`, `TweetController`

2. **Core (Dominio)**: Contiene la lógica de negocio y modelos de dominio.
   - Ubicación: `src/main/java/twitter/challenge/espenia/core/`
   - Subdirectorios principales:
     - `domain`: Entidades principales (`User`, `Tweet`)
     - `usecase`: Servicios y lógica de negocio
     - `gateway`: Interfaces para acceso a datos
     - `exception`: Excepciones de dominio

3. **Infraestructura**: Implementa mecanismos de persistencia y servicios externos.
   - Ubicación: `src/main/java/twitter/challenge/espenia/infra/`
   - Componentes:
     - `mongodb`: Configuración y documentos de MongoDB
     - `gateway`: Implementaciones de las interfaces del gateway

## Flujo de Datos

1. Las peticiones HTTP llegan a los controladores en la capa Entrypoint
2. Los controladores delegan en casos de uso (Core)
3. Los casos de uso utilizan gateways para acceder a los datos
4. La capa de infraestructura implementa estos gateways para interactuar con MongoDB

<hr width="30%" align="left" />

# Docker

El proyecto incluye configuración para despliegue con Docker, facilitando su ejecución en cualquier entorno.

## Requisitos Previos

- Docker
- Docker Compose

## Configuración

### Dockerfile

El proyecto utiliza un enfoque de construcción multi-etapa:

1. **Etapa de construcción**: Compila la aplicación usando JDK 17
2. **Etapa de ejecución**: Ejecuta la aplicación con una imagen liviana

### Docker Compose

El archivo `docker-compose.yml` configura:

- **Servicio principal**: La aplicación Spring Boot
- **MongoDB**: Base de datos NoSQL para almacenamiento
- **Servicio de inicialización**: Configura usuarios y colecciones en MongoDB

## Uso

Para iniciar la aplicación con Docker:

```bash
docker-compose up -d
```

Para detener la aplicación:

```bash
docker-compose down
```

Para ver los logs:

```bash
docker-compose logs -f
```

## Variables de Entorno

El servicio principal utiliza las siguientes variables de entorno que pueden ser modificadas según necesidad:

- `SCOPE_SUFFIX`: Perfil activo (por defecto: `prod`)
- `MONGODB_URI`: URI de conexión a MongoDB
- `MONGODB_DATABASE`: Nombre de la base de datos
- `SERVER_PORT`: Puerto de la aplicación (por defecto: 8090)

<hr width="30%" align="left" />

# Dependencias

| <center>Dependencia</center>                                      | <center>Versión</center> |                             <center>Descripción</center>                             |
|:------------------------------------------------------------------|:------------------------:|:------------------------------------------------------------------------------------:|
| [Spring Boot](https://spring.io/projects/spring-boot)             |          3.3.5           | La librería base sobre la que se construyen interacciones con el server del BackEnd. |
| [MongoDB](https://www.mongodb.com/)                               |          Latest          | Base de datos NoSQL para almacenamiento de usuarios y tweets.                        |
| [MapStruct](https://mapstruct.org/)                               |          1.6.3           | Framework para mapeo de objetos entre capas de dominio y persistencia.               |
| [Lombok](https://projectlombok.org/)                              |          1.18.30         | Reduce el código repetitivo mediante anotaciones.                                    |
| [Spring Validation](https://spring.io/guides/gs/validating-form-input/) |     3.3.5         | Validación de datos en los requests.                                                 |

<hr width="30%" align="left" />

# Wiki

Si bien la documentación del [código fuente](./CONTRIBUTING.md#código-fuente) viene incluida en el mismo,
el uso de los _end points_ para interactuar con el FrontEnd viene mejor explicado en la
[wiki](https://github.com/espenia/split-travel-be/wiki) del proyecto.

## API Endpoints

### Usuarios

- **GET /api/users/{id}**: Obtiene un usuario por ID
- **GET /api/users/username/{username}**: Obtiene un usuario por nombre de usuario
- **POST /api/users**: Crea un nuevo usuario
- **PATCH /api/users/{id}**: Actualiza un usuario existente
- **DELETE /api/users/{id}**: Elimina un usuario

### Tweets

- **POST /api/tweets**: Crea un nuevo tweet

### Follow

- **POST /api/follows/{followerId}** : Usuario sigue a otro usaurio
- **DELETE /api/follows/{followerId}/{followedId}** : Usuario deja de seguir a otro usuario.
- **GET /api/follows/following/{userId}** : Get devuelve usuarios a los que sigue
- **GET /api/follows/followers/{userId}** : Get devuelve usuarios que los siguen

### Timeline

- **GET /api/timeline/{userId}** : trae los (actualmente harcoded 20 tweets PENDING DEV configurable) tweets del user y de los users que sigue. A su vez genera un cache de este timeline, para llamadas proximas (TTL 5 minutos).


La documentación completa de la API está disponible en formato Swagger en `docs/specs/swagger.yaml`.

<hr width="30%" align="left" />

# Convenciones

Las convenciones utilizadas en el proyecto, como las utilizadas para el
[código fuente](./CONTRIBUTING.md#código-fuente),
[*pull requests*](./CONTRIBUTING.md#pull-requests) o la formación de
[*issues*](./CONTRIBUTING.md#issues) se encuentran en el [archivo](./CONTRIBUTING.md)
correspondiente.

<hr width="30%" align="left" />

# CI/CD

El proyecto utiliza GitHub Actions para integración continua. (Actualmente hay issues con CI para test integración ya que usa una base de mongo local y esta dando timeouts, esto se soluciona usando una base embedida en memoria. PENDING DEV )

## Workflow de CI

El flujo de trabajo de CI está definido en `.github/workflows/gradle.yml` y se ejecuta automáticamente en:
- Push a la rama `main`
- Pull Requests dirigidas a `main`

### Etapas del Pipeline

1. **Checkout**: Obtiene el código fuente
2. **Setup JDK**: Configura Java 17
3. **Setup Gradle**: Configura Gradle con caché para dependencias
4. **Build**: Compila y ejecuta pruebas

### Ejecución Local

Para ejecutar el build localmente antes de hacer push:

```bash
./gradlew build
```
