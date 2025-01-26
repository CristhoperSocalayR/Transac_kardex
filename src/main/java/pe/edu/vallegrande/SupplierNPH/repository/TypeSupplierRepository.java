package pe.edu.vallegrande.SupplierNPH.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.SupplierNPH.model.TypeSupplier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TypeSupplierRepository extends ReactiveCrudRepository<TypeSupplier, Long> {

}