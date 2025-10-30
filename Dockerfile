# ----------------------------------------
# 1. ETAPA DE BUILDER (Compilación)
# ----------------------------------------
# Usa una imagen de Maven con Java 21 para compilar
FROM maven:3.9.6-openjdk-21 AS builder

# Establece el directorio de trabajo
WORKDIR /app

# Copia pom.xml para aprovechar el cache de dependencias
COPY pom.xml .

# Descarga las dependencias
RUN mvn dependency:go-offline

# Copia el código fuente
COPY src /app/src

# Empaqueta la aplicación, saltando los tests
RUN mvn package -DskipTests

# ----------------------------------------
# 2. ETAPA DE EJECUCIÓN (Runtime)
# ----------------------------------------
# Usa una imagen base más pequeña con Java 21 para el runtime
FROM openjdk:21-jdk-slim

# Crea un volumen para que Spring Boot lo use
VOLUME /tmp

# Define el nombre del JAR (ajusta si es diferente a tu target)
ARG JAR_FILE=target/*.jar
COPY --from=builder /app/${JAR_FILE} app.jar

# Expone el puerto por defecto de Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]