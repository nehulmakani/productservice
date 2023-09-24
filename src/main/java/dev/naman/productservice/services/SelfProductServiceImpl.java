package dev.naman.productservice.services;

import dev.naman.productservice.dtos.GenericProductDto;
import dev.naman.productservice.models.Category;
import dev.naman.productservice.models.Price;
import dev.naman.productservice.models.Product;
import dev.naman.productservice.repositories.CategoryRepository;
import dev.naman.productservice.repositories.PriceRepository;
import dev.naman.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("selfProductServiceImpl")
public class SelfProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private PriceRepository priceRepository;
    public SelfProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
                                  PriceRepository priceRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.priceRepository = priceRepository;
    }
    @Override
    public GenericProductDto getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            return null;
        }
        GenericProductDto productDto = new GenericProductDto();
        productDto.setId(id);
        productDto.setTitle(product.get().getTitle());
        productDto.setCategory(product.get().getCategory().getName());
        productDto.setPrice(product.get().getPrice().getPrice());
        productDto.setDescription(product.get().getDescription());
        productDto.setImage(product.get().getImage());
        return productDto;
    }

//    @Override
//    public GenericProductDto createProduct(GenericProductDto product) {
//        return null;
//    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<GenericProductDto> allProducts = new ArrayList<>();
        for(Product p:products){
            GenericProductDto pr = new GenericProductDto();
            pr.setId(p.getUuid());
            pr.setTitle(p.getTitle());
            pr.setCategory(p.getCategory().getName());
            pr.setPrice(p.getPrice().getPrice());
            pr.setDescription(p.getDescription());
            pr.setImage(p.getImage());
            allProducts.add(pr);
        }
        return allProducts.size()>0?allProducts:null;
    }

    @Override
    public GenericProductDto deleteProduct(Long id) {
        GenericProductDto product = getProductById(id);
        if(product != null){
            productRepository.deleteById(id);
        }
        return product;
    }

    @Override
    public List<String> getAllCategories() {
//        List<Ca> categories = categoryRepository.findAllDistinctCategory();
        List<String> allCategories = categoryRepository.findAllDistinctCategory();
//        for(Category c:categories) allCategories.add(c.getName());
        return allCategories.size()>0?allCategories:null;
    }

    @Override
    public List<GenericProductDto> getProductsByCategory(String category) {
        Optional<List<Product>> products = productRepository.findAllByCategory_Name(category);
//        if(products.get().size()==0) return null;
        List<Product> prodList = products.get();
//        if(prodList.size()==0) return null;
        List<GenericProductDto> allProducts = new ArrayList<>();
        for(Product p:prodList){
            GenericProductDto pr = new GenericProductDto();
            pr.setId(p.getUuid());
            pr.setTitle(p.getTitle());
            pr.setCategory(p.getCategory().getName());
            pr.setPrice(p.getPrice().getPrice());
            pr.setDescription(p.getDescription());
            pr.setImage(p.getImage());
            allProducts.add(pr);
        }
        return allProducts.size()>0?allProducts:null;
    }

    @Override
    public GenericProductDto addProduct(GenericProductDto genericProductDto){
        Category category = new Category();
        category.setName(genericProductDto.getCategory());
        Category savedCategory = categoryRepository.save(category);

        Price price = new Price();
        price.setPrice(genericProductDto.getPrice());
        Price savedPrice = priceRepository.save(price);

        Product product = new Product();
        product.setCategory(savedCategory);
        product.setPrice(savedPrice);
        product.setDescription(genericProductDto.getDescription());
        product.setTitle(genericProductDto.getTitle());
        product.setImage(genericProductDto.getImage());
        Product savedProduct = productRepository.save(product);

        genericProductDto.setId(savedProduct.getUuid());
        return genericProductDto;
    }

    @Override
    public GenericProductDto updateProduct(Long id, GenericProductDto genericProductDto) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()) return null;
        Product prd = product.get();
        if(genericProductDto.getTitle()!=null) prd.setTitle(genericProductDto.getTitle());
        else genericProductDto.setTitle(prd.getTitle());
        if(genericProductDto.getCategory()!=null) {
            Category category = prd.getCategory();
            category.setName(genericProductDto.getCategory());
            Category savedCategory = categoryRepository.save(category);
            prd.setCategory(savedCategory);
        }else genericProductDto.setCategory(prd.getCategory().getName());
        if(genericProductDto.getPrice()!=0){
            Price price = prd.getPrice();
            price.setPrice(genericProductDto.getPrice());
            Price savedPrice = priceRepository.save(price);
            prd.setPrice(savedPrice);
        }else genericProductDto.setPrice(prd.getPrice().getPrice());
        if(genericProductDto.getDescription()!=null) prd.setDescription(genericProductDto.getDescription());
        else genericProductDto.setDescription(prd.getDescription());
        if(genericProductDto.getImage()!=null) prd.setImage(genericProductDto.getImage());
        else genericProductDto.setImage(prd.getImage());
        productRepository.save(prd);
        genericProductDto.setId(id);
        return genericProductDto;
    }
}
