package pe.edu.vallegrande.SupplierNPH.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.vallegrande.SupplierNPH.model.Product;
import pe.edu.vallegrande.SupplierNPH.repository.ProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository repository;
    private ProductService service;
    private Product product;

    @BeforeEach
    void setUp() {
        repository = mock(ProductRepository.class);
        service = new ProductService(repository);

        product = new Product();
        product.setId(1L);
        product.setType("Lácteo");
        product.setDescription("Leche evaporada");
        product.setPackageWeight(new BigDecimal("0.5"));
        product.setStock(100);
        product.setEntryDate(LocalDate.of(2025, 5, 10));
        product.setExpiryDate(LocalDate.of(2025, 6, 10));
        product.setTypeProduct("Consumible");
        product.setStatus("A");
    }

    @Test
    void testGetAllProducts() {
        when(repository.findAll()).thenReturn(Flux.just(product));

        StepVerifier.create(service.getAllProducts())
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void testDeleteProduct() {
        when(repository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(service.deleteProduct(1L))
                .verifyComplete();

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testSoftDeleteProduct() {
        when(repository.findById(1L)).thenReturn(Mono.just(product));
        when(repository.save(any(Product.class))).thenAnswer(invocation -> {
            Product updated = invocation.getArgument(0);
            updated.setStatus("I");
            return Mono.just(updated);
        });

        StepVerifier.create(service.softDeleteProduct(1L))
                .expectNextMatches(p -> p.getStatus().equals("I"))
                .verifyComplete();
    }

    @Test
    void testRestoreProduct() {
        product.setStatus("I");
        when(repository.findByIdAndStatus(1L, "I")).thenReturn(Mono.just(product));
        when(repository.save(any(Product.class))).thenAnswer(invocation -> {
            Product updated = invocation.getArgument(0);
            updated.setStatus("A");
            return Mono.just(updated);
        });

        StepVerifier.create(service.restoreProduct(1L))
                .expectNextMatches(p -> p.getStatus().equals("A"))
                .verifyComplete();
    }
}
