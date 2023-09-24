package dev.naman.productservice.repositories;

import dev.naman.productservice.models.Category;
import dev.naman.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository
extends JpaRepository<Product, Long> {
//    @Override
//    @Query("Select * from Product where category")
    Optional<List<Product>> findAllByCategory_Name(String name);

    void deleteById(Long id);
}
