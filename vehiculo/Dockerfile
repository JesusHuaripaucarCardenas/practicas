# =========================
# Etapa 1: Build (Maven + JDK 26)
# =========================
FROM eclipse-temurin:26-jdk AS build

WORKDIR /app

# Copiamos primero el wrapper y el pom.xml para aprovechar la cache de capas de Docker
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copiamos el código fuente y compilamos
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# =========================
# Etapa 2: Runtime (JRE 26, imagen liviana)
# =========================
FROM eclipse-temurin:26-jre AS runtime

WORKDIR /app

# Usuario no root por seguridad
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Copiamos únicamente el jar generado en la etapa de build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]