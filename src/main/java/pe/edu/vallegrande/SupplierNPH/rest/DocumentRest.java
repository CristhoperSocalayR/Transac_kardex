package pe.edu.vallegrande.SupplierNPH.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.SupplierNPH.model.Document;
import pe.edu.vallegrande.SupplierNPH.service.DocumentService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/NPH/documents")
public class DocumentRest {

    private final DocumentService documentService;

    public DocumentRest(DocumentService documentService) {
        this.documentService = documentService;
    }

    // Listar todos los documentos
    @GetMapping
    public Flux<Document> listAll() {
        return documentService.findAll();
    }

    // Obtener un documento por ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Document>> getById(@PathVariable Long id) {
        return documentService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Crear un nuevo documento
    @PostMapping
    public Mono<ResponseEntity<Document>> create(@RequestBody Document document) {
        return documentService.save(document)
                .map(savedDocument -> ResponseEntity.ok().body(savedDocument));
    }

    // Actualizar un documento existente
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Document>> update(@PathVariable Long id, @RequestBody Document document) {
        return documentService.update(id, document)
                .map(updatedDocument -> ResponseEntity.ok().body(updatedDocument))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Eliminar un documento por ID
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return documentService.deleteById(id)
                .map(unused -> ResponseEntity.noContent().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}