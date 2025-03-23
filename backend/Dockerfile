# Usamos una imagen base con Java 17 (ajusta la versión según necesites)
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo
WORKDIR /app

# Copia el jar generado (asegúrate de compilar el proyecto con Maven o Gradle y generar el jar)
COPY target/periferiaRedSocial-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que corre la aplicación (por defecto 8080)
EXPOSE 8080

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
