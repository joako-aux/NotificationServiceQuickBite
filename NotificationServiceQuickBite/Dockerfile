# --- ETAPA 1: Compilación ---
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos el pom.xml para preparar la descarga de dependencias en caché
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código fuente de tu servicio de notificaciones
COPY src ./src

# Compilamos generando el artefacto final sin ejecutar los tests
RUN mvn clean package -DskipTests

# --- ETAPA 2: Ejecución ---
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copiamos el .jar usando el artifactId y version de tu pom.xml
COPY --from=build /app/target/NotificationServiceQuickBite-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto en el que va a correr Spring internamente
EXPOSE 8080

# Parámetros óptimos para el manejo de memoria y errores en contenedores
ENV JAVA_OPTS="-XX:+UseG1GC -XX:+ExitOnOutOfMemoryError"

# Comando de arranque del contenedor
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]