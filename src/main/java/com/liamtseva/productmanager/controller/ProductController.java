package com.liamtseva.productmanager.controller;

import com.liamtseva.productmanager.model.Category;
import com.liamtseva.productmanager.model.Product;
import com.liamtseva.productmanager.service.CategoryService;
import com.liamtseva.productmanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products") // Шлях до запитів продуктів
public class ProductController {

  private final ProductService productService;
  private final CategoryService categoryService;

  @Autowired
  public ProductController(ProductService productService, CategoryService categoryService) {
    this.productService = productService;
    this.categoryService = categoryService;
  }

  // Метод для отримання всіх продуктів
  @GetMapping
  public String getAllProducts(
      @RequestParam(value = "sortBy", required = false) String sortBy,
      @RequestParam(value = "category", required = false) Long categoryId,
      @RequestParam(value = "minPrice", required = false) Double minPrice,
      @RequestParam(value = "maxPrice", required = false) Double maxPrice,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      Model model) {

    // Отримати відфільтровані та відсортовані продукти з сервісу
    Page<Product> products = productService.getFilteredProducts(sortBy, categoryId, minPrice, maxPrice, page, size);

    // Передати дані в модель для відображення на фронтенді
    model.addAttribute("products", products.getContent());
    model.addAttribute("totalPages", products.getTotalPages());
    model.addAttribute("currentPage", page);

    // Потрібно для фільтрів за категоріями
    List<Category> categories = categoryService.getAllCategories();
    model.addAttribute("categories", categories);

    return "product/list";
  }

  // Метод для отримання продукту за id
  @GetMapping("/{id}")
  public String getProductById(@PathVariable Long id, Model model) {
    Optional<Product> product = productService.getProductById(id);
    if (product.isPresent()) {
      model.addAttribute("product", product.get()); // Додаємо продукт в модель
      return "product/detail"; // Шаблон для перегляду детальної інформації про продукт
    }
    return "error"; // Якщо продукт не знайдений, повертаємо сторінку помилки
  }

  // Метод для додавання нового продукту
  @GetMapping("/add")
  public String showAddProductForm(Model model) {
    model.addAttribute("product", new Product()); // Створюємо новий об'єкт продукту
    List<Category> categories = categoryService.getAllCategories();
    model.addAttribute("categories", categories);
    return "product/add"; // Шаблон для форми додавання продукту
  }

  @PostMapping("/add")
  public String addProduct(@ModelAttribute Product product) {
    productService.addProduct(product); // Викликаємо сервіс для додавання продукту
    return "redirect:/products"; // Перенаправляємо на список продуктів
  }

  // Метод для оновлення продукту
  @GetMapping("/edit/{id}")
  public String showEditProductForm(@PathVariable Long id, Model model) {
    Optional<Product> product = productService.getProductById(id);
    if (product.isPresent()) {
      model.addAttribute("product", product.get()); // Додаємо продукт для редагування
      List<Category> categories = categoryService.getAllCategories(); // Отримуємо всі категорії
      model.addAttribute("categories", categories); // Додаємо категорії до моделі
      return "product/edit"; // Шаблон для форми редагування продукту
    }
    return "error"; // Якщо продукт не знайдений
  }

  @PostMapping("/edit/{id}")
  public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
    productService.updateProduct(id, product); // Оновлюємо продукт
    return "redirect:/products"; // Перенаправляємо на список продуктів
  }

  // Метод для видалення продукту
  @GetMapping("/delete/{id}")
  public String deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id); // Видаляємо продукт
    return "redirect:/products"; // Перенаправляємо на список продуктів
  }

}