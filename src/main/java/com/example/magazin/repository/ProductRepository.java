package com.example.magazin.repository;

import com.example.magazin.entity.Category;
import com.example.magazin.entity.Magazin;
import com.example.magazin.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    Optional<Product> findByName(String name);

    boolean existsByName(String name);

    List<Product> findAllByCategory(Category category);
    List<Product> findAllByMagazin(Magazin magazin);
}
