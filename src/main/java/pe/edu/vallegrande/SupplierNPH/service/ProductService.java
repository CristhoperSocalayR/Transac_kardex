package pe.edu.vallegrande.SupplierNPH.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.SupplierNPH.model.Product;
import pe.edu.vallegrande.SupplierNPH.repository.ProductRepository;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Product> createProduct(Product product) {
        return productRepository.save(product);
    }

    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }

    public Mono<Product> softDeleteProduct(Long id) {
        return productRepository.findById(id)
                .flatMap(product -> {
                    product.setStatus("I"); // "I" de Inactivo
                    return productRepository.save(product);
                });
    }

    public Mono<Product> restoreProduct(Long id) {
        return productRepository.findByIdAndStatus(id, "I")
                .flatMap(product -> {
                    product.setStatus("A"); // "A" de Activo
                    return productRepository.save(product);
                });
    }

    public Mono<Product> updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setType(productDetails.getType());
                    existingProduct.setDescription(productDetails.getDescription());
                    existingProduct.setPackageWeight(productDetails.getPackageWeight());
                    existingProduct.setStock(productDetails.getStock());
                    existingProduct.setEntryDate(productDetails.getEntryDate());
                    existingProduct.setExpiryDate(productDetails.getExpiryDate());
                    return productRepository.save(existingProduct);
                });
    }
}
