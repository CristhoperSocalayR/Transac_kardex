package pe.edu.vallegrande.SupplierNPH.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.SupplierNPH.model.Supplier;
import pe.edu.vallegrande.SupplierNPH.repository.SupplierRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository repository;

    // Listar todos
    public Flux<Supplier> findAll() {
        return repository.findAll();
    }

    // Buscar por ID
    public Mono<Supplier> findById(Long id) {
        return repository.findById(id);
    }

    // Crear o actualizar
    public Mono<Supplier> save(Supplier supplier) {
        return repository.save(supplier);
    }

    // Eliminar físicamente
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }

    // Eliminación lógica (cambiar el estado a "I")
    public Mono<Supplier> logicalDelete(Long id) {
        return repository.findById(id)
                .flatMap(supplier -> {
                    supplier.setStatus("I");
                    return repository.save(supplier);
                });
    }

    // Restaurar (cambiar el estado a "A")
    public Mono<Supplier> restore(Long id) {
        return repository.findById(id)
                .flatMap(supplier -> {
                    supplier.setStatus("A");
                    return repository.save(supplier);
                });
    }
}
