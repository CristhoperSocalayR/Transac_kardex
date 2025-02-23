package pe.edu.vallegrande.SupplierNPH.service;

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.SupplierNPH.model.Document;
import pe.edu.vallegrande.SupplierNPH.repository.DocumentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    // Listar todos los documentos
    public Flux<Document> findAll() {
        return documentRepository.findAll();
    }

    // Buscar un documento por ID
    public Mono<Document> findById(Long id) {
        return documentRepository.findById(id);
    }

    // Crear un nuevo documento
    public Mono<Document> save(Document document) {
        return documentRepository.save(document);
    }

    // Actualizar un documento existente
    public Mono<Document> update(Long id, Document document) {
        return documentRepository.findById(id)
                .flatMap(existingDocument -> {
                    existingDocument.setIssueDate(document.getIssueDate());
                    existingDocument.setConcept(document.getConcept());
                    existingDocument.setDocumentType(document.getDocumentType());
                    existingDocument.setDocumentNumber(document.getDocumentNumber());
                    existingDocument.setTypeKardexId(document.getTypeKardexId());
                    return documentRepository.save(existingDocument);
                });
    }

    // Eliminar un documento por ID
    public Mono<Void> deleteById(Long id) {
        return documentRepository.deleteById(id);
    }
}
