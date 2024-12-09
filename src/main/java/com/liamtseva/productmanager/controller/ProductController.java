package com.liamtseva.productmanager.controller;

import com.liamtseva.productmanager.model.Category;
import com.liamtseva.productmanager.model.Product;
import com.liamtseva.productmanager.service.CategoryService;
import com.liamtseva.productmanager.service.ProductService;
import java.math.BigDecimal;
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
  public String getAllProducts(Model model) {
    List<Product> products = productService.getAllProducts();
    model.addAttribute("products", products); // Додаємо продукти в модель для Thymeleaf
    return "product/list"; // Повертатимемо назву HTML-шаблону для списку продуктів
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
  // Метод для сортування продуктів за категорією
  @GetMapping("/sorted")
  public String getProductsSortedByCategory(@RequestParam Long categoryId, Model model) {
    List<Product> sortedProducts = productService.getProductsSortedByCategory(categoryId); // Отримуємо відсортовані продукти
    model.addAttribute("products", sortedProducts); // Додаємо їх в модель для відображення в шаблоні
    return "product/list"; // Повертаємо шаблон зі списком продуктів
  }
  @GetMapping("/list")
  public String getProductsPage(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Long categoryId,
      @RequestParam(required = false) BigDecimal minPrice,
      @RequestParam(required = false) BigDecimal maxPrice,
      Model model) {

    Page<Product> productsPage;

    if (name != null && !name.isEmpty()) {
      productsPage = productService.getProductsByName(name, page, size);
    } else if (categoryId != null && minPrice != null && maxPrice != null) {
      Optional<Category> categoryOpt = categoryService.getCategoryById(categoryId);
      if (categoryOpt.isPresent()) {
        Category category = categoryOpt.get();
        productsPage = productService.getProductsByCategoryAndPriceRange(category, minPrice, maxPrice, page, size);
      } else {
        productsPage = Page.empty();
      }
    } else if (categoryId != null) {
      Optional<Category> categoryOpt = categoryService.getCategoryById(categoryId);
      if (categoryOpt.isPresent()) {
        Category category = categoryOpt.get();
        productsPage = productService.getProductsByCategory(category, page, size);
      } else {
        productsPage = Page.empty();
      }
    } else if (minPrice != null && maxPrice != null) {
      productsPage = productService.getProductsByPriceRange(minPrice, maxPrice, page, size);
    } else {
      productsPage = productService.getProducts(page, size);
    }

    model.addAttribute("products", productsPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", productsPage.getTotalPages());
    model.addAttribute("totalItems", productsPage.getTotalElements());
    model.addAttribute("categories", categoryService.getAllCategories());

    return "product/list";
  }

}