package pe.edu.vallegrande.SupplierNPH.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.SupplierNPH.model.Document;

public interface DocumentRepository extends ReactiveCrudRepository<Document, Long> {
}
