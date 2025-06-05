# AngularServer

Este documento proporciona instrucciones paso a paso para arrancar e iniciar el proyecto AngularServer.

## Requisitos Previos

Asegúrate de tener instalado lo siguiente:

1. Java 8 (JDK 1.8)
2. Maven 3.x
3. SQL Server (con acceso a las bases de datos configuradas)

## Configuración de la Base de Datos

El proyecto está configurado para conectarse a varias bases de datos SQL Server:

1. Base de datos principal (PConnect)
2. Base de datos secundaria (ChecadorGDL)
3. Base de datos terciaria (ChecadorCVA)

Verifica que las credenciales y URLs de conexión en `src/main/resources/application.properties` sean correctas para tu entorno:

```properties
# Base de datos principal
spring.datasource.url=jdbc:sqlserver://172.24.20.10:1433;DatabaseName=PConnect
spring.datasource.username=sa
spring.datasource.password=tHatEdr4

# Base de datos secundaria
second.datasource.url=jdbc:sqlserver://192.168.3.14:1433;DatabaseName=ChecadorGDL
second.datasource.username=sa
second.datasource.password=tHatEdr4

# Base de datos terciaria
third.datasource.url=jdbc:sqlserver://192.168.2.14:1433;DatabaseName=ChecadorCVA
third.datasource.username=sa
third.datasource.password=tHatEdr4
```

Si necesitas cambiar a otro entorno (QA, UAT, DEV), descomenta las líneas correspondientes en el archivo `application.properties`.

## Pasos para Arrancar el Proyecto

### 1. Compilar el Proyecto

Abre una terminal en la raíz del proyecto y ejecuta:

```bash
mvn clean install
```

Este comando compilará el proyecto, ejecutará las pruebas y generará el archivo WAR en la carpeta `target`.

### 2. Ejecutar la Aplicación

Hay dos formas de ejecutar la aplicación:

#### Opción 1: Ejecutar como aplicación Spring Boot

```bash
mvn spring-boot:run
```

#### Opción 2: Ejecutar el JAR generado

```bash
java -jar target\proquifanet-0.0.1-SNAPSHOT.war
```

### 3. Verificar que la Aplicación está Funcionando

Una vez iniciada, la aplicación estará disponible en:

```
http://localhost:8081/api
```

La documentación de la API (Swagger) estará disponible en:

```
http://localhost:8081/api/swagger-ui.html
```

## Solución de Problemas

### Problemas de Conexión a la Base de Datos

Si tienes problemas para conectarte a la base de datos:

1. Verifica que las credenciales en `application.properties` sean correctas
2. Asegúrate de que las bases de datos estén accesibles desde tu máquina
3. Verifica que los puertos necesarios estén abiertos en el firewall

### Problemas de Memoria

Si la aplicación se detiene con errores de memoria, puedes aumentar la memoria asignada a la JVM:

```bash
java -Xmx1024m -jar target\proquifanet-0.0.1-SNAPSHOT.war
```

### Logs

Los logs de la aplicación pueden ayudar a diagnosticar problemas. Revisa la consola o los archivos de log generados.

## Información Adicional

- Puerto del servidor: 8081 (configurable en `application.properties`)
- Contexto de la aplicación: `/api`
- Versión de Spring Boot: 2.0.1.RELEASE
- Packaging: WAR
