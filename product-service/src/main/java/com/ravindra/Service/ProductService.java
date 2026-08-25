package com.ravindra.Service;


import com.ravindra.Dto.ProductRequestDTO;
import com.ravindra.Dto.ProductResponseDTO;
import com.ravindra.Entity.Product;
import com.ravindra.Exception.ProductNotFoundException;
import com.ravindra.Repository.ProductRepository;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

       private final ProductRepository productRepository;
       private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    //Pagination
    public Page<ProductResponseDTO> getAllProducts(int page, int size, String sortBy,String derection) {

          Sort sort =  derection.equalsIgnoreCase("esc")
             ? Sort.by(sortBy).ascending()
               : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(product ->
                modelMapper.map(product, ProductResponseDTO.class));
    }

       public List<ProductResponseDTO> getAllProductUsingJpql()
       {
         List<Product> products=  productRepository.getAllProductsUsingJPQL();
         return products.stream()

                 .map(product -> modelMapper.map(product,ProductResponseDTO.class)).toList();
       }

      public ProductResponseDTO saveProduct(ProductRequestDTO productRequestDTO)
      {
          Product product =  modelMapper.map(productRequestDTO,Product.class);

           Product saveProduct =productRepository.save(product);

           return modelMapper.map(saveProduct,ProductResponseDTO.class);

      }

      public ProductResponseDTO getProductById(Long id)
      {
         Product product =  productRepository.findById(id).orElseThrow(
                  ()->  new ProductNotFoundException("product not fount with id:" + id));
            return modelMapper.map(product,ProductResponseDTO.class);
      }

          public ProductResponseDTO updateProduct(Long id,ProductRequestDTO productRequestDTO)
          {

              Product existingProduct =   productRepository.findById(id).orElseThrow(
                         () -> new ProductNotFoundException("product not fount with id :" + id));

            existingProduct.setName(productRequestDTO.getName());
            existingProduct.setPrice(productRequestDTO.getPrice());;

           Product updateProduct = productRepository.save(existingProduct);

            return modelMapper.map(updateProduct,ProductResponseDTO.class);
          }


    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id: " + id));

        productRepository.delete(product);
    }
}



