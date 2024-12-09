package com.liamtseva.productmanager.controller;

import com.liamtseva.productmanager.model.Category;
import com.liamtseva.productmanager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categories") // Шлях до запитів категорій
public class CategoryController {

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  // Метод для отримання всіх категорій
  @GetMapping
  public String getAllCategories(Model model) {
    List<Category> categories = categoryService.getAllCategories();
    model.addAttribute("categories", categories); // Додаємо категорії в модель для Thymeleaf
    return "category/list"; // Повертатимемо назву HTML-шаблону для списку категорій
  }

  // Метод для отримання категорії за id
  @GetMapping("/{id}")
  public String getCategoryById(@PathVariable Long id, Model model) {
    Optional<Category> category = categoryService.getCategoryById(id);
    if (category.isPresent()) {
      model.addAttribute("category", category.get()); // Додаємо категорію в модель
      return "category/detail"; // Шаблон для перегляду детальної інформації про категорію
    }
    return "error"; // Якщо категорія не знайдена, повертаємо сторінку помилки
  }

  // Метод для додавання нової категорії
  @GetMapping("/add")
  public String showAddCategoryForm(Model model) {
    model.addAttribute("category", new Category()); // Створюємо новий об'єкт категорії
    return "category/add"; // Шаблон для форми додавання категорії
  }

  @PostMapping("/add")
  public String addCategory(@ModelAttribute Category category) {
    categoryService.addCategory(category); // Викликаємо сервіс для додавання категорії
    return "redirect:/categories"; // Перенаправляємо на список категорій
  }

  // Метод для оновлення категорії
  @GetMapping("/edit/{id}")
  public String showEditCategoryForm(@PathVariable Long id, Model model) {
    Optional<Category> category = categoryService.getCategoryById(id);
    if (category.isEmpty()) {
      throw new IllegalArgumentException("Не знайдено категорію з ID: " + id);
    }
    model.addAttribute("category", category.get());
    return "category/edit";
  }

  @PostMapping("/edit/{id}")
  public String updateCategory(@PathVariable Long id, @ModelAttribute Category category) {
    categoryService.updateCategory(id, category); // Оновлюємо категорію
    return "redirect:/categories"; // Перенаправляємо на список категорій
  }

  // Метод для видалення категорії
  @GetMapping("/delete/{id}")
  public String deleteCategory(@PathVariable Long id) {
    categoryService.deleteCategory(id); // Видаляємо категорію
    return "redirect:/categories"; // Перенаправляємо на список категорій
  }
}
