package com.example.magazin.repository;

import com.example.magazin.entity.Category;
import com.example.magazin.entity.Magazin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    boolean existsByName(String name);
    List<Category> findAllByMagazin(Magazin magazin);
}
