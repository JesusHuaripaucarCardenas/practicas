## Inatalar minikube

```bash
winget install Kubernetes.minikube
```
```bash
Get-ChildItem "C:\Program Files" -Filter minikube.exe -Recurse -ErrorAction SilentlyContinue
```
```bash
$env:Path -split ';'
```
```bash
$env:Path += ";C:\Program Files\Kubernetes\Minikube"
```
```bash
where.exe minikube
```
```bash
minikube version
```

## Iniciar minikube

```bash
minikube start
```
```
minikube delete --profile=minikube
```
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

## RECUPERACION 

BASE DE DATOS
```
DO $$
DECLARE
    r RECORD;
BEGIN
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public')
    LOOP
        EXECUTE 'DROP TABLE IF EXISTS public.' || quote_ident(r.tablename) || ' CASCADE';
    END LOOP;
END $$;

-- (Opcional) Si prefieres borrar tablas puntuales por nombre conocido:
-- DROP TABLE IF EXISTS alquiler CASCADE;
-- DROP TABLE IF EXISTS cliente CASCADE;
-- DROP TABLE IF EXISTS vehiculo CASCADE;

-- =========================================================
-- 2) TABLA VEHICULO (Microservicio Maestro CRUD)
-- =========================================================
CREATE TABLE vehiculo (
    id                BIGSERIAL PRIMARY KEY,
    placa             VARCHAR(15)     NOT NULL UNIQUE,
    marca             VARCHAR(50)     NOT NULL,
    modelo            VARCHAR(50)     NOT NULL,
    anio              INTEGER         NOT NULL CHECK (anio >= 1990),
    color             VARCHAR(30)     NOT NULL,
    precio_por_dia    NUMERIC(10,2)   NOT NULL CHECK (precio_por_dia > 0),
    estado            VARCHAR(20)     NOT NULL DEFAULT 'DISPONIBLE'
                        CHECK (estado IN ('DISPONIBLE','ALQUILADO','MANTENIMIENTO','ELIMINADO'))
);

-- =========================================================
-- 3) TABLA CLIENTE (Microservicio Maestro CRUD)
-- =========================================================
CREATE TABLE cliente (
    id          BIGSERIAL PRIMARY KEY,
    dni         VARCHAR(8)      NOT NULL UNIQUE,
    nombres     VARCHAR(80)     NOT NULL,
    apellidos   VARCHAR(80)     NOT NULL,
    celular     VARCHAR(9)      NOT NULL,
    correo      VARCHAR(120)    NOT NULL UNIQUE,
    licencia    VARCHAR(15)     NOT NULL UNIQUE,
    estado      VARCHAR(20)     NOT NULL DEFAULT 'ACTIVO'
                  CHECK (estado IN ('ACTIVO','INACTIVO'))
);

-- =========================================================
-- 4) TABLA ALQUILER (Microservicio TRANSACCIONAL)
--    Referencia lógica a cliente y vehiculo (viven en otro
--    microservicio, pero como comparten la misma BD Neon,
--    sí podemos usar FK real).
-- =========================================================
CREATE TABLE alquiler (
    id            BIGSERIAL PRIMARY KEY,
    cliente_id    BIGINT          NOT NULL REFERENCES cliente(id),
    vehiculo_id   BIGINT          NOT NULL REFERENCES vehiculo(id),
    dias          INTEGER         NOT NULL CHECK (dias > 0),
    fecha_inicio  DATE            NOT NULL,
    fecha_fin     DATE            NOT NULL,
    total         NUMERIC(10,2)   NOT NULL CHECK (total >= 0),
    estado        VARCHAR(20)     NOT NULL DEFAULT 'ACTIVO'
                    CHECK (estado IN ('ACTIVO','FINALIZADO','CANCELADO')),
    CONSTRAINT chk_fechas CHECK (fecha_fin >= fecha_inicio)
);

-- =========================================================
-- 5) Índices adicionales útiles para búsquedas rápidas
-- =========================================================
CREATE INDEX idx_alquiler_cliente  ON alquiler(cliente_id);
CREATE INDEX idx_alquiler_vehiculo ON alquiler(vehiculo_id);
CREATE INDEX idx_vehiculo_estado   ON vehiculo(estado);

-- =========================================================
-- HCT HACKATON - jesus huaripaucar
-- Datos de prueba (seed) para vehiculo, cliente y alquiler
-- Ejecutar DESPUÉS de 01_setup_database.sql
-- =========================================================

-- =========================================================
-- VEHICULOS
-- =========================================================
INSERT INTO vehiculo (placa, marca, modelo, anio, color, precio_por_dia, estado) VALUES
('ABC-123', 'Toyota',     'Corolla',  2026, 'Blanco',   150.00, 'DISPONIBLE'),
('XYZ-789', 'Hyundai',    'Tucson',   2025, 'Gris',     220.00, 'DISPONIBLE'),
('LMN-456', 'Kia',        'Rio',      2023, 'Rojo',     110.00, 'ALQUILADO'),
('QWE-321', 'Chevrolet',  'Onix',     2024, 'Negro',    120.00, 'MANTENIMIENTO'),
('JKL-654', 'Nissan',     'Sentra',   2022, 'Azul',     130.00, 'DISPONIBLE');

-- =========================================================
-- CLIENTES
-- =========================================================
INSERT INTO cliente (dni, nombres, apellidos, celular, correo, licencia, estado) VALUES
('72654321', 'Miguel Luis',  'Lopez Sanchez',    '987654321', 'miguel@gmail.com',   'Q12345678', 'ACTIVO'),
('70123456', 'Ana Maria',    'Torres Vega',      '955512233', 'ana.torres@gmail.com','Q22334455', 'ACTIVO'),
('68887766', 'Carlos Andres','Ramirez Diaz',     '944478899', 'carlos.r@gmail.com', 'Q33445566', 'ACTIVO'),
('71234567', 'Lucia Fernanda','Quispe Mamani',   '966123456', 'lucia.q@gmail.com',  'Q44556677', 'INACTIVO'),
('69998877', 'Jorge Luis',   'Huaman Rojas',     '977889900', 'jorge.h@gmail.com',  'Q55667788', 'ACTIVO');

-- =========================================================
-- ALQUILERES
-- (cliente_id / vehiculo_id según el orden de inserción arriba: 1..5)
-- =========================================================
INSERT INTO alquiler (cliente_id, vehiculo_id, dias, fecha_inicio, fecha_fin, total, estado) VALUES
(1, 1, 3, '2026-07-03', '2026-07-06', 450.00,  'ACTIVO'),
(2, 2, 5, '2026-06-20', '2026-06-25', 1100.00, 'FINALIZADO'),
(3, 3, 2, '2026-07-10', '2026-07-12', 220.00,  'ACTIVO'),
(5, 5, 4, '2026-07-01', '2026-07-05', 520.00,  'CANCELADO'),
(1, 4, 1, '2026-05-15', '2026-05-16', 120.00,  'FINALIZADO');

```

VEHICULO
```
https://hub.docker.com/r/jesushuaripaucarcardenas/hct-vehiculo/tags
```

```
docker build -t jesushuaripaucarcardenas/hct-vehiculo:latest .
```
```
http://localhost:8081/api/vehiculos
```
```
docker push jesushuaripaucarcardenas/hct-vehiculo:latest
```
```
docker pull jesushuaripaucarcardenas/hct-vehiculo:latest
```
```
docker run -d \
  --name hct-vehiculo \
  -p 8081:8081 \
  jesushuaripaucarcardenas/hct-vehiculo:latest
```
crear
```
kubectl apply -f hct-vehiculo-huaripaucar-jesus-namespace.yml
```
```
kubectl apply -f hct-vehiculo-huaripaucar-jesus-secret.yml
```
```
kubectl apply -f hct-vehiculo-huaripaucar-jesus-deployment.yml
```
```
kubectl apply -f hct-vehiculo-huaripaucar-jesus-service.yml
```
eliminar
```
kubectl delete -f hct-vehiculo-huaripaucar-jesus-service.yml
```
```
kubectl delete -f hct-vehiculo-huaripaucar-jesus-deployment.yml
```
```
kubectl delete -f hct-vehiculo-huaripaucar-jesus-secret.yml
```
```
kubectl delete -f hct-vehiculo-huaripaucar-jesus-namespace.yml
```
# CLIENTE
```
https://hub.docker.com/r/jesushuaripaucarcardenas/hct-cliente/tags
```
```
http://localhost:8082/api/clientes
```
```
docker build -t jesushuaripaucarcardenas/hct-cliente:latest .
```
```
docker push jesushuaripaucarcardenas/hct-cliente:latest
```
```
docker pull jesushuaripaucarcardenas/hct-cliente:latest
```
```
docker run -d \
  --name hct-cliente \
  -p 8082:8082 \
  jesushuaripaucarcardenas/hct-cliente:latest
```
```
docker run -d --name hct-cliente -p 8082:8082 jesushuaripaucarcardenas/hct-cliente:latest
```
```
minikube start
```
```
kubectl apply -f k8s/manifest-cliente/
```
```
kubectl get all -n hct-huaripaucar
```
```
kubectl port-forward -n hct-huaripaucar svc/hct-cliente-svc 30082:8082
```
```
minikube service hct-cliente-svc -n hct-huaripaucar
```
```
kubectl port-forward -n hct-huaripaucar svc/hct-cliente-svc 30082:8082 &
```
crear
```
kubectl apply -f hct-cliente-huaripaucar-jesus-secret.yml
```
```
kubectl apply -f hct-cliente-huaripaucar-jesus-deployment.yml
```
```
kubectl apply -f hct-cliente-huaripaucar-jesus-service.yml
```
eliminar
```
kubectl delete -f hct-cliente-huaripaucar-jesus-service.yml
```
```
kubectl delete -f hct-cliente-huaripaucar-jesus-deployment.yml
```
```
kubectl delete -f hct-cliente-huaripaucar-jesus-secret.yml
```
# ALQUILER
crear

```
{
  "clienteId": 2,
  "vehiculoId": 2,
  "dias": 3,
  "fechaInicio": "2026-07-15",
  "fechaFin": "2026-07-18",
  "total": 360.00,
  "estado": "ACTIVO"
}
```
```
minikube start
```
```
http://localhost:8083/api/alquileres
```
```
https://hub.docker.com/r/jesushuaripaucarcardenas/hct-alquiler/tags
```
```
kubectl apply -f k8s/manifest-alquiler/
```
```
kubectl port-forward -n hct-huaripaucar svc/hct-alquiler-svc 30083:8083
```
```
minikube service hct-alquiler-svc -n hct-huaripaucar
```
```
docker build -t jesushuaripaucarcardenas/hct-alquiler:latest . 
```
```
docker push jesushuaripaucarcardenas/hct-alquiler:latest   
```
crear
```
kubectl apply -f hct-alquiler-huaripaucar-jesus-secret.yml
```
```
kubectl apply -f hct-alquiler-huaripaucar-jesus-deployment.yml
```
```
kubectl apply -f hct-alquiler-huaripaucar-jesus-service.yml
```
eliminar
```
kubectl delete -f hct-alquiler-huaripaucar-jesus-service.yml
```
```
kubectl delete -f hct-alquiler-huaripaucar-jesus-deployment.yml
```
```
kubectl delete -f hct-alquiler-huaripaucar-jesus-secret.yml
```

# FRONT END
```
docker build -t jesushuaripaucarcardenas/hct-frontend:latest .
```
```
docker push jesushuaripaucarcardenas/hct-frontend:latest
```
```
minikube service hct-frontend-svc -n hct-huaripaucar
```
```
kubectl apply -f k8s/manifest-frontend/
```

crear
```
kubectl apply -f hct-frontend-huaripaucar-jesus-secret.yml
```
```
kubectl apply -f hct-frontend-huaripaucar-jesus-deployment.yml
```
```
kubectl apply -f hct-frontend-huaripaucar-jesus-service.yml
```
eliminar
```
kubectl delete -f hct-frontend-huaripaucar-jesus-service.yml
```
```
kubectl delete -f hct-frontend-huaripaucar-jesus-deployment.yml
```
```
kubectl delete -f hct-frontend-huaripaucar-jesus-secret.yml
```
