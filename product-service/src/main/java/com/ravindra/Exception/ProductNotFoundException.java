package com.ravindra.Exception;

public class ProductNotFoundException extends RuntimeException{

     public ProductNotFoundException(String msg)
     {
         super(msg);
     }
}
