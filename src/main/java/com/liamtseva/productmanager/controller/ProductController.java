package com.liamtseva.productmanager.controller;

import com.liamtseva.productmanager.model.Product;
import com.liamtseva.productmanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products") // REST API маршрут
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  // GET: Посторінковий список товарів із фільтрацією
  @GetMapping
  public ResponseEntity<Page<Product>> getAllProducts(
      @RequestParam(value = "sortBy", required = false) String sortBy,
      @RequestParam(value = "category", required = false) Long categoryId,
      @RequestParam(value = "minPrice", required = false) Double minPrice,
      @RequestParam(value = "maxPrice", required = false) Double maxPrice,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {

    Page<Product> products = productService.getFilteredProducts(sortBy, categoryId, minPrice, maxPrice, page, size);

    if (products.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.ok(products);
  }

  // GET: Отримати товар за ID
  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    return productService.getProductById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  // POST: Додати товар
  @PostMapping
  public ResponseEntity<String> addProduct(@RequestBody Product product) {
    // Перевірка на наявність ID у тілі запиту (не можна додавати товар з існуючим ID)
    if (product.getId() != null) {
      return ResponseEntity
          .status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS) // 451
          .body("Зміна товару через POST не дозволено. Використовуйте PUT для зміни.");
    }

    // Збереження товару
    productService.save(product);
    return ResponseEntity
        .status(HttpStatus.CREATED) // 201
        .body("Товар успішно додано.");
  }

  @PutMapping
  public ResponseEntity<String> updateProduct(@RequestBody Product product) {
    if (product.getId() == null) {
      return ResponseEntity
          .status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS) // 451
          .body("Додавання товару через PUT заборонено. Використовуйте POST для додавання товару.");
    }

    // Збереження зміненого товару
    productService.save(product);
    return ResponseEntity
        .status(HttpStatus.OK) // 200
        .body("Товар успішно оновлено.");
  }

  // DELETE: Видалити товар за ID
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    if (productService.getProductById(id).isPresent()) {
      productService.deleteProduct(id);
      return ResponseEntity.noContent().build(); // 204
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
  }
}
