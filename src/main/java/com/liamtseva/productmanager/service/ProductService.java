package com.liamtseva.productmanager.service;

import com.liamtseva.productmanager.ProductSpecification;
import com.liamtseva.productmanager.model.Product;
import com.liamtseva.productmanager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  // Конструктор для впровадження залежності ProductRepository
  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  // Метод для отримання всіх продуктів
  public List<Product> getAllProducts() {
    return productRepository.findAll();
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
  public List<Product> getFilteredProducts(String name, Long categoryId, Double minPrice, Double maxPrice, String sortBy) {
    // Створюємо умови для фільтрації
    Specification<Product> spec = Specification.where(null);

    if (name != null && !name.isEmpty()) {
      spec = spec.and(ProductSpecification.hasName(name)); // Фільтруємо за назвою
    }
    if (categoryId != null) {
      spec = spec.and(ProductSpecification.hasCategoryId(categoryId)); // Фільтруємо за категорією
    }
    if (minPrice != null) {
      spec = spec.and(ProductSpecification.hasMinPrice(minPrice)); // Фільтруємо за мінімальною ціною
    }
    if (maxPrice != null) {
      spec = spec.and(ProductSpecification.hasMaxPrice(maxPrice)); // Фільтруємо за максимальною ціною
    }

    // Сортуємо за зазначеним полем
    Sort sort = Sort.by(Sort.Direction.ASC, sortBy);

    // Повертаємо список продуктів відповідно до фільтрів і сортування
    return productRepository.findAll(spec, sort);
  }

  // Метод для видалення продукту
  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }
  // Метод для отримання всіх продуктів, відсортованих за категорією
  public List<Product> getProductsSortedByCategory() {
    return productRepository.findAll(Sort.by("category.name"));
  }
  //Метод для отримання продуктів з пагінацією
  public Page<Product> getProducts(int page, int size) {
    Pageable pageable = PageRequest.of(page, size); // Створюємо об'єкт Pageable
    return productRepository.findAll(pageable); // Отримуємо продукти для відповідної сторінки
  }
  public List<Product> getAllProductsSortedByCategory() {
    return productRepository.findAll(Sort.by(Sort.Order.asc("category.name")));
  }
}
