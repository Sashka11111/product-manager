package com.liamtseva.productmanager.service;

import com.liamtseva.productmanager.model.Category;
import com.liamtseva.productmanager.model.Product;
import com.liamtseva.productmanager.repository.ProductRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

  // Метод для видалення продукту
  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }
  // Метод для отримання всіх продуктів, відсортованих за категорією
  public List<Product> getProductsSortedByCategory(Long categoryId) {
    return productRepository.findAllByCategory_Id(categoryId, Sort.by(Sort.Order.asc("category"))); // Сортуємо за категорією
  }
  // Метод для отримання продуктів з пагінацією
  public Page<Product> getProducts(int page, int size) {
    Pageable pageable = PageRequest.of(page, size); // Створюємо об'єкт Pageable
    return productRepository.findAll(pageable); // Отримуємо продукти для відповідної сторінки
  }

  // Фільтрація продуктів за категорією
  public Page<Product> getProductsByCategory(Category category, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return productRepository.findByCategory(category, pageable);
  }

  // Фільтрація продуктів за ціновим діапазоном
  public Page<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
  }

  // Фільтрація продуктів за категорією та ціною
  public Page<Product> getProductsByCategoryAndPriceRange(Category category, BigDecimal minPrice, BigDecimal maxPrice, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return productRepository.findByCategoryAndPriceBetween(category, minPrice, maxPrice, pageable);
  }

  // Фільтрація продуктів за назвою
  public Page<Product> getProductsByName(String name, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return productRepository.findByNameContaining(name, pageable);
  }
}
