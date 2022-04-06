package com.example.magazin.repository;

import com.example.magazin.entity.Magazin;
import com.example.magazin.entity.Purchase;
import com.example.magazin.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale,Integer> {


    List<Sale> findAllByPurchase(Purchase purchase);
    List<Sale> findAllByMagazin(Magazin magazin);
}
