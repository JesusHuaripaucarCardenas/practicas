<img width="665" height="446" alt="Captura de pantalla 2026-07-02 a las 3 58 04 p  m" src="https://github.com/user-attachments/assets/527e0da8-9204-4001-b68e-db23ccd13976" />

## estructura de carpetas

```
application/
├── port/
│   ├── in/
│   │   └── IArchivoServicePort.java
│   ├── out/
│   │   └── IArchivoRepositoryPort.java
│
├── service/
│   └── ArchivoService.java
│
domain/
├── model/
│   └── Archivo.java
│
infrastructure/
├── adapter/
│   ├── in/
│   │   └── rest/
│   │       └── ArchivoRest.java
│   │
│   ├── out/
│   │   ├── persistence/
│   │   │   ├── ArchivoRepository.java
│   │   │   └── ArchivoRepositoryAdapter.java
│   │   │
│   │   └── client/
│   │       └── ArchivoClientAdapter.java
│
└── ArchivoApplication.java
```

## Iniciar sesion en docker hub

```
docker login
```

## armar dockerfile

```dockerfile
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## Construir la imagen

```bash
docker build -t jesushuaripaucarcardenas/practica:1.0 .
```

## Subir la imagen a Docker Hub

```bash
docker push jesushuaripaucarcardenas/practica:1.0
```

## Descargar la imagen

```
docker pull jesushuaripaucarcardenas/practica:1.0
```

## Iniciar minikube

```bash
minikube start
```

## Aplicar los manifiestos de Kubernetes (en orden)

```
kubectl apply -f k8s/jesus.namespace.yml
```
```
kubectl apply -f k8s/jesus.secret.yml
```
```
kubectl apply -f k8s/jesus.deployment.yml
```
```
kubectl apply -f k8s/jesus.service.yml
```

---

## Verificar que los Pods están corriendo

```bash
kubectl get pods -n jesus-practica-namespace
```

```
kubectl get all -n pepe-gonzales-namespace
```


Si algo falla, revisar logs:

```bash
kubectl logs -n jesus-practica-namespace -l app=jesus-practica-deployment
```


## base de datos PostgresSQL

host:

```
ep-polished-leaf-acn11qxh-pooler.sa-east-1.aws.neon.tech
```
nombre de usuario:

```
neondb_owner
```

contraseña:

```
npg_MNeK54wzgqdt
```

Base de datos:

```
neondb
```

angular zone

```
npm install zone.js --save
```

base de datos

```
CREATE TABLE practica (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    dni INTEGER NOT NULL UNIQUE,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telefono VARCHAR(20) UNIQUE,
    date TIMESTAMP NOT NULL DEFAULT now(),
    active BOOLEAN NOT NULL DEFAULT true
);

INSERT INTO practica (dni, firstname, lastname, email, telefono)
VALUES 
    (12345678, 'Juan', 'Pérez', 'juan.perez@example.com', '999111222'),
    (87654321, 'María', 'García', 'maria.garcia@example.com', '999333444');

CREATE TABLE IF NOT EXISTS producto (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio NUMERIC(10,2) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT true
);

INSERT INTO producto (nombre, precio, stock, active) VALUES
('Laptop Lenovo IdeaPad', 2499.90, 15, true),
('Mouse Inalámbrico Logitech', 79.50, 50, true);

telefono VARCHAR(20) NOT NULL UNIQUE,

```
