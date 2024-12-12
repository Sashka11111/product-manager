# Database Interaction with Spring Data 🚀

## 📖 Key Concepts:

- **Entities:** Representation of database tables in Java using JPA annotations.  
- **Repositories:** Simplified database interaction through Spring Data's `JpaRepository`.  
- **Services:** Business logic for processing and managing data.  
- **Controllers:** API endpoints for interacting with the application.  
- **Pagination, Filtering, Sorting:** Core functionalities to manage and retrieve data efficiently.

## 💻 Features Implemented:

1. **Entities:**  
   - `Product`: Includes fields like `id`, `name`, `price` and a relation to `Category`.  
   - `Category`: Contains fields `id` and `name`.  

2. **Repositories:**  
   - `ProductRepository` and `CategoryRepository` extend `JpaRepository` for CRUD operations.  

3. **Services:**  
   - `ProductService`: Handles business logic for products.  
   - `CategoryService`: Manages category operations.  

4. **Controllers:**  
   - `ProductController`: Handles routes for managing products.  
   - `CategoryController`: Manages category routes.

5. **Sorting, Filtering, and Pagination:**  
   - Implemented sorting of products by category.  
   - Added pagination support to display products in manageable chunks.  
   - Implemented filtering by price range and category.

6. **Frontend with Thymeleaf:**  
   - Created a simple frontend to display products and categories.  
   - Integrated sorting, filtering, and pagination functionalities into the UI.

## 🔧 Technologies Used:

- **Java**  
- **Spring Boot**  
- **Spring Data JPA**  
- **Thymeleaf**  
- **H2 Database (or your chosen database)**  

## 📦 Installation:

1. Clone the repository:

   ```bash
   git clone https://github.com/Sashka11111/product-manager
   ```
2. Navigate to the project directory:
    
   ```bash
    cd your-project-directory
   ```
3. Run the application:
4. Access the application at:  
    `http://localhost:8080`

## 📝 Notes:

- The application provides basic CRUD operations for `Product` and `Category`.
- Sorting, filtering, and pagination are fully functional for efficient product management.
