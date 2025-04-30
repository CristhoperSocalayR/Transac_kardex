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

    // Crear un nuevo producto
    public Mono<Product> createProduct(Product product) {
        // Aquí podrías validar que la fecha de caducidad no sea anterior a la fecha de entrada
        if (product.getExpiryDate() != null && product.getEntryDate() != null &&
                product.getExpiryDate().isBefore(product.getEntryDate())) {
            return Mono.error(new IllegalArgumentException("La fecha de caducidad no puede ser anterior a la fecha de entrada"));
        }
        return productRepository.save(product);
    }

    // Obtener todos los productos
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Eliminar un producto por su ID
    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }

    // Eliminar un producto de forma lógica (cambiar el estado a "Inactivo")
    public Mono<Product> softDeleteProduct(Long id) {
        return productRepository.findById(id)
                .flatMap(product -> {
                    product.setStatus("I"); // "I" de Inactivo
                    return productRepository.save(product);
                });
    }

    // Restaurar un producto (cambiar el estado a "Activo")
    public Mono<Product> restoreProduct(Long id) {
        return productRepository.findByIdAndStatus(id, "I")
                .flatMap(product -> {
                    product.setStatus("A"); // "A" de Activo
                    return productRepository.save(product);
                });
    }

    // Actualizar un producto existente
    public Mono<Product> updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    // Validaciones antes de actualizar el producto
                    if (productDetails.getExpiryDate() != null && productDetails.getEntryDate() != null &&
                            productDetails.getExpiryDate().isBefore(productDetails.getEntryDate())) {
                        return Mono.error(new IllegalArgumentException("La fecha de caducidad no puede ser anterior a la fecha de entrada"));
                    }

                    // Actualizar los detalles del producto
                    existingProduct.setType(productDetails.getType());
                    existingProduct.setDescription(productDetails.getDescription());
                    existingProduct.setPackageWeight(productDetails.getPackageWeight());
                    existingProduct.setStock(productDetails.getStock());
                    existingProduct.setEntryDate(productDetails.getEntryDate());
                    existingProduct.setExpiryDate(productDetails.getExpiryDate());
                    existingProduct.setTypeProduct(productDetails.getTypeProduct());  // Actualizamos el campo 'typeProduct'
                    existingProduct.setStatus(productDetails.getStatus()); // Actualizamos el estado del producto

                    return productRepository.save(existingProduct);
                });
    }
}
