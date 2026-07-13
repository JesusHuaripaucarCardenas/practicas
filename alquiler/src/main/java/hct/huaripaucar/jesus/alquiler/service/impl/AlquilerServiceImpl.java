package hct.huaripaucar.jesus.alquiler.service.impl;

import hct.huaripaucar.jesus.alquiler.dto.EstadoRequest;
import hct.huaripaucar.jesus.alquiler.dto.VehiculoDTO;
import hct.huaripaucar.jesus.alquiler.model.Alquiler;
import hct.huaripaucar.jesus.alquiler.repository.AlquilerRepository;
import hct.huaripaucar.jesus.alquiler.service.AlquilerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AlquilerServiceImpl implements AlquilerService {

    private final AlquilerRepository repository;

    @Qualifier("vehiculoWebClient")
    private final WebClient vehiculoWebClient;

    @Qualifier("clienteWebClient")
    private final WebClient clienteWebClient;

    @Override
    public Flux<Alquiler> listar() {
        return repository.findAll();
    }

    @Override
    public Mono<Alquiler> obtener(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Alquiler no encontrado: " + id)));
    }

    @Override
    public Mono<Alquiler> crear(Alquiler alquiler) {
        alquiler.setId(null);
        return validarCliente(alquiler.getClienteId())
                .then(validarVehiculoDisponible(alquiler.getVehiculoId()))
                .then(repository.save(alquiler))
                .flatMap(guardado ->
                        cambiarEstadoVehiculo(guardado.getVehiculoId(), "ALQUILADO")
                                .thenReturn(guardado));
    }

    @Override
    public Mono<Alquiler> actualizar(Long id, Alquiler cambios) {
        return obtener(id)
                .flatMap(actual -> {
                    boolean cambioVehiculo = !actual.getVehiculoId().equals(cambios.getVehiculoId());
                    Mono<Void> validaciones = validarCliente(cambios.getClienteId())
                            .then(cambioVehiculo
                                    ? validarVehiculoDisponible(cambios.getVehiculoId())
                                    : validarVehiculo(cambios.getVehiculoId()));
                    return validaciones.thenReturn(actual);
                })
                .flatMap(actual -> {
                    Long vehiculoAnterior = actual.getVehiculoId();
                    actual.setClienteId(cambios.getClienteId());
                    actual.setVehiculoId(cambios.getVehiculoId());
                    actual.setDias(cambios.getDias());
                    actual.setFechaInicio(cambios.getFechaInicio());
                    actual.setFechaFin(cambios.getFechaFin());
                    actual.setTotal(cambios.getTotal());
                    actual.setEstado(cambios.getEstado());
                    return repository.save(actual).flatMap(guardado -> {
                        // Si cambió de vehiculo, liberamos el anterior y reservamos el nuevo
                        if (!vehiculoAnterior.equals(guardado.getVehiculoId())) {
                            return cambiarEstadoVehiculo(vehiculoAnterior, "DISPONIBLE")
                                    .then(cambiarEstadoVehiculo(guardado.getVehiculoId(), "ALQUILADO"))
                                    .thenReturn(guardado);
                        }
                        return Mono.just(guardado);
                    });
                });
    }

    @Override
    public Mono<Alquiler> eliminar(Long id) {
        return obtener(id).flatMap(a -> {
            a.setEstado("CANCELADO");
            return repository.save(a)
                    .flatMap(guardado ->
                            cambiarEstadoVehiculo(guardado.getVehiculoId(), "DISPONIBLE")
                                    .thenReturn(guardado));
        });
    }

    @Override
    public Mono<Alquiler> restaurar(Long id) {
        return obtener(id).flatMap(a -> {
            a.setEstado("ACTIVO");
            return repository.save(a)
                    .flatMap(guardado ->
                            cambiarEstadoVehiculo(guardado.getVehiculoId(), "ALQUILADO")
                                    .thenReturn(guardado));
        });
    }

    private Mono<Void> validarCliente(Long clienteId) {
        return clienteWebClient.get().uri("/api/clientes/{id}", clienteId)
                .retrieve()
                .toBodilessEntity()
                .onErrorMap(e -> new RuntimeException("Cliente no encontrado: " + clienteId))
                .then();
    }

    private Mono<Void> validarVehiculo(Long vehiculoId) {
        return vehiculoWebClient.get().uri("/api/vehiculos/{id}", vehiculoId)
                .retrieve()
                .toBodilessEntity()
                .onErrorMap(e -> new RuntimeException("Vehiculo no encontrado: " + vehiculoId))
                .then();
    }

    private Mono<Void> validarVehiculoDisponible(Long vehiculoId) {
        return vehiculoWebClient.get().uri("/api/vehiculos/{id}", vehiculoId)
                .retrieve()
                .bodyToMono(VehiculoDTO.class)
                .onErrorMap(e -> new RuntimeException("Vehiculo no encontrado: " + vehiculoId))
                .flatMap(v -> {
                    if (!"DISPONIBLE".equals(v.getEstado())) {
                        return Mono.error(new RuntimeException(
                                "El vehiculo " + vehiculoId + " no esta disponible (estado: " + v.getEstado() + ")"));
                    }
                    return Mono.empty();
                });
    }

    private Mono<Void> cambiarEstadoVehiculo(Long vehiculoId, String estado) {
        return vehiculoWebClient.patch()
                .uri("/api/vehiculos/{id}/estado", vehiculoId)
                .bodyValue(new EstadoRequest(estado))
                .retrieve()
                .toBodilessEntity()
                .onErrorMap(e -> new RuntimeException(
                        "No se pudo actualizar el estado del vehiculo " + vehiculoId))
                .then();
    }
}