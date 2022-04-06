package com.example.magazin.repository;

import com.example.magazin.entity.Category;
import com.example.magazin.entity.Magazin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MagazinRepository  extends JpaRepository<Magazin,Integer> {

    Optional<Magazin> findById(Integer id);

}
