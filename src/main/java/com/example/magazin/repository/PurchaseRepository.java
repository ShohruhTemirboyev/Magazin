package com.example.magazin.repository;

import com.example.magazin.entity.Magazin;
import com.example.magazin.entity.Product;
import com.example.magazin.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {

    Optional<Purchase> findByProduct(Product id);
    List<Purchase> findAllByMagazin(Magazin magazin);
}
