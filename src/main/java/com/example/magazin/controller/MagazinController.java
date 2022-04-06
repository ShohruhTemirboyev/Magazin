package com.example.magazin.controller;

import com.example.magazin.payloat.ApiResponseModel;
import com.example.magazin.payloat.ReqMagazin;
import com.example.magazin.payloat.Respons;
import com.example.magazin.service.MagazinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/magazin")
public class MagazinController {

    @Autowired
    MagazinService magazinService;

    @PostMapping("/addMagazin")
    public HttpEntity<?> addMagazin(@RequestBody ReqMagazin reqMagazin){
        Respons respons=magazinService.addMagazin(reqMagazin);
        return ResponseEntity.ok(respons);

    }
    @GetMapping("/getMagazinList")
    public HttpEntity<?> getMagazinList(){
        ApiResponseModel apiResponseModel=magazinService.getMagazinList();
        return ResponseEntity.ok(apiResponseModel);
    }
    @GetMapping("/getMagazin/{id}")
    public HttpEntity<?> getMagazin(@PathVariable Integer id){
        ApiResponseModel apiResponseModel=magazinService.getMagazin(id);
        return ResponseEntity.ok(apiResponseModel);
    }
    @GetMapping("/delete/{id}")
    public HttpEntity<?> deleteMagazin(@PathVariable Integer id){
        Respons respons=magazinService.deleteMagazni(id);
        return ResponseEntity.ok(respons);
    }
    @PostMapping("/edit/{id}")
    public HttpEntity<?> editMagazin(@PathVariable Integer id,@RequestBody ReqMagazin reqMagazin){
        Respons respons=magazinService.editMagazin(reqMagazin,id);
        return ResponseEntity.ok(respons);
    }

    @PostMapping("/filter")
    public HttpEntity<?> filterMagazin(@RequestBody ReqMagazin reqMagazin){
        ApiResponseModel apiResponseModel=magazinService.filterMagazin(reqMagazin);
        return ResponseEntity.ok(apiResponseModel);
    }
}
