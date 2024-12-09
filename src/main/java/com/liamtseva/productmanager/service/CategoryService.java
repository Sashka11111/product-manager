package com.liamtseva.productmanager.service;

import com.liamtseva.productmanager.model.Category;
import com.liamtseva.productmanager.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  // Конструктор для впровадження залежності CategoryRepository
  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  // Метод для отримання всіх категорій
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  // Метод для отримання категорії за її id
  public Optional<Category> getCategoryById(Long id) {
    return categoryRepository.findById(id);
  }

  // Метод для додавання нової категорії
  public Category addCategory(Category category) {
    return categoryRepository.save(category);
  }

  // Метод для оновлення категорії
  public Category updateCategory(Long id, Category category) {
    if (categoryRepository.existsById(id)) {
      category.setId(id);
      return categoryRepository.save(category);
    }
    return null; // Якщо категорія не знайдена
  }
  // Метод для видалення категорії
  public void deleteCategory(Long id) {
    categoryRepository.deleteById(id);
  }
}
