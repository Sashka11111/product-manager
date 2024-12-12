package com.liamtseva.productmanager.repository;

import com.liamtseva.productmanager.model.Category;
import com.liamtseva.productmanager.model.Product;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  @Query("SELECT p FROM Product p WHERE " +
      "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
      "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
      "(:maxPrice IS NULL OR p.price <= :maxPrice)")
  Page<Product> findFilteredProducts(
      @Param("categoryId") Long categoryId,
      @Param("minPrice") Double minPrice,
      @Param("maxPrice") Double maxPrice,
      Pageable pageable);
}
