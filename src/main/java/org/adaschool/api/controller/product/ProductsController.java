package org.adaschool.api.controller.product;

import org.adaschool.api.exception.ProductNotFoundException;
import org.adaschool.api.repository.product.Product;
import org.adaschool.api.service.product.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/products/")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(@Autowired ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product newProduct) {
        Product savedProduct = productsService.save(newProduct);
        URI createdProductUri = URI.create("" + savedProduct.getId());
        return ResponseEntity.created(createdProductUri).body(savedProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productsService.all();
        return ResponseEntity.ok(productList);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Product>> findById(@PathVariable("id") String id) {
        Optional<Product> optionalProduct = productsService.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("511");
        } else{
            return ResponseEntity.ok().body(optionalProduct);
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {
        Optional<Product> optionalProduct = productsService.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(id);
        } else {
            Product existingProduct = optionalProduct.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setPrice(updatedProduct.getPrice());

            Product savedProduct = productsService.save(existingProduct);
            return ResponseEntity.ok(savedProduct);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String id) {
        Optional<Product> optionalProduct = productsService.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(id);
        } else {
            productsService.deleteById(id);
            return ResponseEntity.ok().build();
        }
    }
}
