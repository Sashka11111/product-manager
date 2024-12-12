package com.liamtseva.productmanager.service;

import com.liamtseva.productmanager.model.Product;
import com.liamtseva.productmanager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  // Конструктор для впровадження залежності ProductRepository
  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }
  // Метод для отримання продукту за його id
  public Optional<Product> getProductById(Long id) {
    return productRepository.findById(id);
  }

  // Метод для додавання нового продукту
  public Product addProduct(Product product) {
    return productRepository.save(product);
  }

  // Метод для оновлення продукту
  public Product updateProduct(Long id, Product product) {
    if (productRepository.existsById(id)) {
      product.setId(id);
      return productRepository.save(product);
    }
    return null; // Якщо продукт не знайдений
  }

  // Метод для видалення продукту
  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }

  public Page<Product> getFilteredProducts(String sortBy, Long categoryId, Double minPrice, Double maxPrice, int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy == null ? "id" : sortBy));

    if (categoryId != null || minPrice != null || maxPrice != null) {
      return productRepository.findFilteredProducts(categoryId, minPrice, maxPrice, pageable);
    }

    return productRepository.findAll(pageable);
  }
}
