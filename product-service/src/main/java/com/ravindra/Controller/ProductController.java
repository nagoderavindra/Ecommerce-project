package com.ravindra.Controller;

import com.ravindra.Dto.ProductRequestDTO;
import com.ravindra.Dto.ProductResponseDTO;
import com.ravindra.Entity.Product;
import com.ravindra.Service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }
       @GetMapping
      public Page<ProductResponseDTO> getAllProducts(
              @RequestParam(defaultValue = "0")int page,
              @RequestParam(defaultValue = "5")int size,
              @RequestParam(defaultValue = "id")String sortBy,
              @RequestParam(defaultValue = "asc")String derection)

      {
          return productService.getAllProducts(page,size,sortBy,derection);
      }

        @PostMapping
        public ProductResponseDTO saveProduct(@Valid  @RequestBody ProductRequestDTO productRequestDTO)
      {
          return productService.saveProduct(productRequestDTO);
      }

      @GetMapping("/Jpql")
      public List<ProductResponseDTO> getAllProductsUsingJPQL()
      {
         return productService.getAllProductUsingJpql();
      }


    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id) {

        return productService.getProductById(id);

    }

    @PutMapping("/{id}")
       public ProductResponseDTO updateProduct(@PathVariable Long id,@RequestBody ProductRequestDTO productRequestDTO)
       {
           return productService.updateProduct(id,productRequestDTO);
       }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return "Product deleted successfully.";

    }
}
