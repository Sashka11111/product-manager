package com.liamtseva.productmanager;

import com.liamtseva.productmanager.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

  public static Specification<Product> hasName(String name) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.like(root.get("name"), "%" + name + "%");
  }

  public static Specification<Product> hasCategoryId(Long categoryId) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("category").get("id"), categoryId);
  }

  public static Specification<Product> hasMinPrice(Double minPrice) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
  }

  public static Specification<Product> hasMaxPrice(Double maxPrice) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
  }
}
