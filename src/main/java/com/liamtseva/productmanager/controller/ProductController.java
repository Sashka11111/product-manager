package com.liamtseva.productmanager.controller;

import com.liamtseva.productmanager.ProductSpecification;
import com.liamtseva.productmanager.model.Category;
import com.liamtseva.productmanager.model.Product;
import com.liamtseva.productmanager.repository.CategoryRepository;
import com.liamtseva.productmanager.repository.ProductRepository;
import com.liamtseva.productmanager.service.CategoryService;
import com.liamtseva.productmanager.service.ProductService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
  private ProductRepository productRepository;

  @Autowired
  private CategoryRepository categoryRepository;

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
  // Метод для сортування продуктів за категорією
//  @GetMapping("/sorted")
//  public String getProductsSortedByCategory(@RequestParam Long categoryId, Model model) {
//    List<Product> sortedProducts = productService.getProductsSortedByCategory(categoryId); // Отримуємо відсортовані продукти
//    model.addAttribute("products", sortedProducts); // Додаємо їх в модель для відображення в шаблоні
//    return "product/list"; // Повертаємо шаблон зі списком продуктів
//  }
  @GetMapping("/sorted")
  public List<Product> getProductsSortedByCategory() {
//    List<Category> categories = categoryService.getAllCategories();
//    model.addAttribute("categories", categories);
    return productService.getProductsSortedByCategory();
  }
  @GetMapping("/list")    public String getProducts(@RequestParam(required = false) String search,
      @RequestParam(required = false) Long category,
      @RequestParam(required = false) Double minPrice,
      @RequestParam(required = false) Double maxPrice,
      Model model) {

    // Створення специфікацій для фільтрації
    Specification<Product> specification = Specification.where(null);

    if (search != null && !search.isEmpty()) {
      specification = specification.and(ProductSpecification.hasName(search));
    }
    if (category != null) {
      specification = specification.and(ProductSpecification.hasCategoryId(category));
    }
    if (minPrice != null) {
      specification = specification.and(ProductSpecification.hasMinPrice(minPrice));
    }
    if (maxPrice != null) {
      specification = specification.and(ProductSpecification.hasMaxPrice(maxPrice));
    }

    // Отримання фільтрованих продуктів
    List<Product> products = productRepository.findAll(specification);

    // Отримання всіх категорій для випадаючого списку
    List<Category> categories = categoryRepository.findAll();

    model.addAttribute("products", products);
    model.addAttribute("categories", categories);
    model.addAttribute("search", search);
    model.addAttribute("categoryId", category);
    model.addAttribute("minPrice", minPrice);
    model.addAttribute("maxPrice", maxPrice);

    return "products/list"; // Повертаємо шаблон для відображення
  }

}