package com.example.magazin.controller;

import com.example.magazin.payloat.ApiResponseModel;
import com.example.magazin.payloat.ReqCategory;
import com.example.magazin.payloat.Respons;
import com.example.magazin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/addCategory/{id}")
   public HttpEntity<?> addCategory(@PathVariable Integer id,@RequestBody ReqCategory reqCategory){
        Respons respons=categoryService.addCategory(id,reqCategory);
        return ResponseEntity.ok(respons);
    }

    @GetMapping("/getAllCategory")
    public HttpEntity<?> getAllCategory(){
        ApiResponseModel apiResponseModel=categoryService.getAllCategory();
        return ResponseEntity.ok(apiResponseModel);
    }

    @GetMapping("/getCategory/{id}")
    public HttpEntity<?> getCategory(@PathVariable Integer id){
        ApiResponseModel apiResponseModel=categoryService.getCategory(id);
        return ResponseEntity.ok(apiResponseModel);
    }
    @GetMapping("/delete/{id}")
    public HttpEntity<?> deleteCategoey(@PathVariable Integer id){
        Respons respons=categoryService.deleteCategory(id);
        return ResponseEntity.ok(respons);
    }
    @PostMapping("/edit/{id}")
    public HttpEntity<?> editCategory(@PathVariable Integer id,@RequestBody ReqCategory reqCategory){
        Respons respons=categoryService.editCategory(id,reqCategory);
        return ResponseEntity.ok(respons);
    }
    @PostMapping("/filter")
    public HttpEntity<?> filterCategory(@RequestBody ReqCategory reqCategory){
        ApiResponseModel apiResponseModel=categoryService.filterCategory(reqCategory);
        return ResponseEntity.ok(apiResponseModel);
    }

}
