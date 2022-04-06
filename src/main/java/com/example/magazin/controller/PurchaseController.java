package com.example.magazin.controller;

import com.example.magazin.payloat.*;
import com.example.magazin.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping("/addPurchase/{id}")
    public HttpEntity<?> addPurchase(@PathVariable Integer id,@RequestBody ReqPurchase reqPurchase){
        Respons respons=purchaseService.addPurchase(id,reqPurchase);
        return ResponseEntity.ok(respons);
    }

    @GetMapping("/getPurchaseList")
    public HttpEntity<?> getAllPurchase(){
        ApiResponseModel apiResponseModel=purchaseService.getAllPurchase();
        return ResponseEntity.ok(apiResponseModel);
    }
    @GetMapping("/getPurchase/{id}")
    public HttpEntity<?> getPurchase(@PathVariable Integer id){
        ApiResponseModel apiResponseModel=purchaseService.getPurchase(id);
        return ResponseEntity.ok(apiResponseModel);
    }

    @PostMapping("/filterCount")
    public HttpEntity<?> filterCount(@RequestBody ReqFilter reqFilter){
        ApiResponseModel apiResponseModel=purchaseService.filterCount(reqFilter);
        return ResponseEntity.ok(apiResponseModel);
    }

    @PostMapping("/filterPrice")
    public HttpEntity<?> filterPrice(@RequestBody ReqFilter reqFilter){
        ApiResponseModel apiResponseModel=purchaseService.filterPrice(reqFilter);
        return ResponseEntity.ok(apiResponseModel);
    }

    @GetMapping("/delete/{id}")
    public HttpEntity<?> deletePurchase(@PathVariable Integer id){
        Respons respons=purchaseService.deletePurchase(id);
        return ResponseEntity.ok(respons);
    }
    @PostMapping("/edit/{id}")
    public HttpEntity<?> editPurchase(@PathVariable Integer id,@RequestBody ReqPurchase reqPurchase){
        Respons respons=purchaseService.editpurchase(id,reqPurchase);
        return ResponseEntity.ok(respons);
    }

    @PostMapping("/filter")
    public HttpEntity<?> filter(@RequestBody ReqFilterPurchase reqFilterPurchase){
        ApiResponseModel apiResponseModel=purchaseService.filter(reqFilterPurchase);
        return ResponseEntity.ok(apiResponseModel);
    }
}
