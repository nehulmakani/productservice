package dev.naman.productservice.controllers;

import dev.naman.productservice.dtos.ExceptionDto;
import dev.naman.productservice.dtos.GenericProductDto;
import dev.naman.productservice.exceptions.NotFoundException;
import dev.naman.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
//    @Autowired
    // field injection
    private ProductService productService;
    // ....;
    // ...;



    // constructor injection
//    @Autowired
    public ProductController(@Qualifier("selfProductServiceImpl") ProductService productService) {
        this.productService = productService;
    }
//

    // setter injection
//    @Autowired
//    public void setProductService(ProductService productService) {
//        this.productService = productService;
//    }

    // GET /products {}
    @GetMapping
    public ResponseEntity<List<GenericProductDto>> getAllProducts() {
        return  new ResponseEntity<>(
                productService.getAllProducts(), HttpStatus.OK);
    }

    // localhost:8080/products/{id}
    // localhost:8080/products/123
    @GetMapping("/{id}")
    public ResponseEntity<GenericProductDto> getProductById(@PathVariable("id") Long id) {
        GenericProductDto product = productService.getProductById(id);
        if(product==null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericProductDto> deleteProductById(@PathVariable("id") Long id) {
        GenericProductDto deletedProduct = productService.deleteProduct(id);
        if(deletedProduct==null){
            return new ResponseEntity<>(
                    null,
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                deletedProduct,
                HttpStatus.FOUND
        );
    }

//    @PostMapping
//    public GenericProductDto createProduct(@RequestBody GenericProductDto product) {
////        System.out.println(product.name);
//        return productService.createProduct(product);
//    }
    @PostMapping
    public ResponseEntity<GenericProductDto> addProduct(@RequestBody GenericProductDto product){
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PatchMapping("/{id}")
    public ResponseEntity<GenericProductDto> updateProductById(@PathVariable Long id,
                                                               @RequestBody GenericProductDto productDto) {
        GenericProductDto updatedProduct = productService.updateProduct(id, productDto);
        if(updatedProduct==null){
            return new ResponseEntity<>(
                    null,
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                updatedProduct,
                HttpStatus.OK
        );
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories(){
        return new ResponseEntity<>(productService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<GenericProductDto>> getAllProductInCategory(@PathVariable String category){
        List<GenericProductDto> prodList = productService.getProductsByCategory(category);
        return new ResponseEntity<>(prodList, HttpStatus.OK);
    }
}
