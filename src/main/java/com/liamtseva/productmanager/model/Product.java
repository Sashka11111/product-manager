package com.liamtseva.productmanager.model;

import jakarta.persistence.*;

@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Double price;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  // Пустий конструктор (необхідний для JPA)
  public Product() {}

  // Конструктор з параметрами
  public Product(String name, Double price, Category category) {
    this.name = name;
    this.price = price;
    this.category = category;
  }

  // Геттери і сеттери
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  // Перевизначення методу toString для зручного виведення
  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", price=" + price +
        ", category=" + category.getName() +
        '}';
  }
}
