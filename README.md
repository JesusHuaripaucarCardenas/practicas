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
