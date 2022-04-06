package com.example.magazin.controller;

import com.example.magazin.payloat.ApiResponseModel;
import com.example.magazin.payloat.ReqFilterProduct;
import com.example.magazin.payloat.ReqProduct;
import com.example.magazin.payloat.Respons;
import com.example.magazin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/addProduct/{id}")
    public HttpEntity<?> addProduct(@PathVariable Integer id,@RequestBody ReqProduct reqProduct){
        Respons respons=productService.addProduct(id,reqProduct);
        return ResponseEntity.ok(respons);
    }

    @GetMapping("/getProductList")
    public HttpEntity<?> getAllProduct(){
        ApiResponseModel apiResponseModel=productService.getAllProduct();
        return ResponseEntity.ok(apiResponseModel);
    }
    @GetMapping("/getProduct/{id}")
    public HttpEntity<?> getProduct(@PathVariable Integer id){
        ApiResponseModel apiResponseModel=productService.getProduct(id);
        return ResponseEntity.ok(apiResponseModel);
    }
    @GetMapping("/delete/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable Integer id){
        Respons respons=productService.deleteProduct(id);
        return ResponseEntity.ok(respons);
    }

    @PostMapping("/edit/{id}")
    public HttpEntity<?> editProduct(@PathVariable Integer id,@RequestBody ReqProduct reqProduct){
        Respons respons=productService.editProduct(id,reqProduct);
        return ResponseEntity.ok(respons);
    }

    @PostMapping("/filter")
    public HttpEntity<?> filterProduct(@RequestBody ReqFilterProduct reqFilterProduct){
        ApiResponseModel apiResponseModel=productService.filterProduct(reqFilterProduct);
        return ResponseEntity.ok(apiResponseModel);
    }
}
