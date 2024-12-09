package com.liamtseva.productmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Генерує унікальний id
  private Long id; // Поле id — це ключ таблиці

  private String name; // Назва категорії

  // Пустий конструктор (необхідний для JPA)
  public Category() {}

  // Конструктор з параметрами
  public Category(String name) {
    this.name = name;
  }

  // Геттери й сеттери для полів
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

  // Перевизначення методу toString для зручного виведення
  @Override
  public String toString() {
    return "Category{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
