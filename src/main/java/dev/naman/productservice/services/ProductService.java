package dev.naman.productservice.services;

import dev.naman.productservice.dtos.GenericProductDto;
import dev.naman.productservice.exceptions.NotFoundException;

import java.util.List;

public interface ProductService {

//    GenericProductDto createProduct(GenericProductDto product);

    GenericProductDto getProductById(Long id);

    List<GenericProductDto> getAllProducts();

   GenericProductDto deleteProduct(Long id);

   List<String> getAllCategories();

   List<GenericProductDto> getProductsByCategory(String category);

   GenericProductDto addProduct(GenericProductDto genericProductDto);

   GenericProductDto updateProduct(Long id, GenericProductDto genericProductDto);
}
