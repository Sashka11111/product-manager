package com.liamtseva.productmanager.repository;

import com.liamtseva.productmanager.model.Category;
import com.liamtseva.productmanager.model.Product;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  // Метод для отримання всіх продуктів, відсортованих за категорією
  List<Product> findAllByCategory_Id(Long categoryId, Sort sort);
  // Пагінація продуктів
  Page<Product> findAll(Pageable pageable);
  // Пошук продуктів за категорією
  Page<Product> findByCategory(Category category, Pageable pageable);

  // Пошук продуктів за ціною в діапазоні
  Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

  // Пошук продуктів за категорією та ціновим діапазоном
  Page<Product> findByCategoryAndPriceBetween(Category category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

  // Загальний пошук за назвою
  Page<Product> findByNameContaining(String name, Pageable pageable);
}
