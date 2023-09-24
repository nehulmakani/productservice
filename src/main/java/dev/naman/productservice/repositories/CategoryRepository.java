package dev.naman.productservice.repositories;

import dev.naman.productservice.models.Category;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

@Lazy
public interface CategoryRepository
extends JpaRepository<Category, Long> {

    @Query("SELECT distinct name FROM Category")
    List<String> findAllDistinctCategory();
}
