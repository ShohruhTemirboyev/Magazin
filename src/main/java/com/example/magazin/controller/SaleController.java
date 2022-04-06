package com.example.magazin.controller;

import com.example.magazin.payloat.ApiResponseModel;
import com.example.magazin.payloat.ReqPurchase;
import com.example.magazin.payloat.ReqSale;
import com.example.magazin.payloat.Respons;
import com.example.magazin.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    SaleService saleService;

    @PostMapping("/addSale/{id}")
    public HttpEntity<?> addSale(@PathVariable Integer id,@RequestBody ReqSale reqSale){
        Respons respons=saleService.addSale(id,reqSale);
        return ResponseEntity.ok(respons);
    }

    @GetMapping("/getSaleList")
    public HttpEntity<?> getAllSale(){
        ApiResponseModel apiResponseModel=saleService.getAllSale();
        return ResponseEntity.ok(apiResponseModel);
    }

    @GetMapping("/getSale/{id}")
    public HttpEntity<?> getSale(@PathVariable Integer id){
        ApiResponseModel apiResponseModel=saleService.getSale(id);
        return ResponseEntity.ok(apiResponseModel);
    }

    @GetMapping("/delete/{id}")
    public HttpEntity<?> deleteSale(@PathVariable Integer id){
        Respons respons=saleService.deleteSale(id);
        return ResponseEntity.ok(respons);
    }

    @PostMapping("/edit/{id}")
    public HttpEntity<?> editSale(@PathVariable Integer id,@RequestBody ReqSale reqSale){
        Respons respons=saleService.editSale(id,reqSale);
        return ResponseEntity.ok(respons);
    }
}
