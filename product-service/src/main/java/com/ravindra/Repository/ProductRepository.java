package com.ravindra.Repository;

import com.ravindra.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("SELECT p FROM Product p")
    List<Product> getAllProductsUsingJPQL();
}
