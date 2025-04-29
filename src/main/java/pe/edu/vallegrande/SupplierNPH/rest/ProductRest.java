package pe.edu.vallegrande.SupplierNPH.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.SupplierNPH.model.Product;
import pe.edu.vallegrande.SupplierNPH.service.ProductService;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/NPH/products")
public class ProductRest {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductoModel> createProduct(@RequestBody ProductoModel product) {
        return productoService.createProduct(product);
    }

    @GetMapping
    public Flux<ProductoModel> getAllProducts() {
        return productoService.getAllProducts();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(@PathVariable Long id) {
        return productoService.deleteProduct(id);
    }

    @PutMapping("/logic/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductoModel> softDeleteProduct(@PathVariable Long id) {
        return productoService.softDeleteProduct(id);
    }

    @PutMapping("/restore/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductoModel> restoreProduct(@PathVariable Long id) {
        return productoService.restoreProduct(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductoModel> updateProduct(@PathVariable Long id, @RequestBody ProductoModel productDetails) {
        return productoService.updateProduct(id, productDetails);
    }

}
