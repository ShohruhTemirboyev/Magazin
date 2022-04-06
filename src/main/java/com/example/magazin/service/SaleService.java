package com.example.magazin.service;

import com.example.magazin.entity.Magazin;
import com.example.magazin.entity.Purchase;
import com.example.magazin.entity.Sale;
import com.example.magazin.payloat.ApiResponseModel;
import com.example.magazin.payloat.ReqSale;
import com.example.magazin.payloat.ResSale;
import com.example.magazin.payloat.Respons;
import com.example.magazin.repository.MagazinRepository;
import com.example.magazin.repository.MessageRepository;
import com.example.magazin.repository.PurchaseRepository;
import com.example.magazin.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleService {
    @Autowired
    SaleRepository saleRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    MagazinRepository magazinRepository;

    public SaleService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private JdbcTemplate jdbcTemplate;

    public Respons addSale(Integer id,ReqSale reqSale){
        Respons respons=new Respons();
        try {
            Optional<Purchase> purchaseOptional=purchaseRepository.findById(reqSale.getPurchaseId());
            if (purchaseOptional.isPresent()){
                if (purchaseOptional.get().getCount()> reqSale.getCount()){
                    Sale sale=new Sale();
                    sale.setCount(reqSale.getCount());
                    sale.setDate(reqSale.getDate());
                    sale.setPrice(reqSale.getPrice());
                    sale.setPurchase(purchaseOptional.get());
                    saleRepository.save(sale);
                    purchaseOptional.get().setCount(purchaseOptional.get().getCount()- reqSale.getCount());
                    Optional<Magazin> magazinOptional=magazinRepository.findById(id);
                    sale.setMagazin(magazinOptional.get());
                    purchaseRepository.save(purchaseOptional.get());
                    magazinRepository.save(magazinOptional.get());
                    respons.setStatus(messageRepository.findByCode(0));
                }
                else {
                    respons.setStatus(messageRepository.findByCode(107));
                }
            }else {
                respons.setStatus(messageRepository.findByCode(106));
            }

        }catch (Exception e){
            respons.setStatus(messageRepository.findByCode(1));
        }
        return respons;
    }


    public ApiResponseModel getAllSale(){
        ApiResponseModel apiResponseModel=new ApiResponseModel();
        try {
            List<Map<String,Object>> maps=jdbcTemplate.queryForList("select *from sale");
            apiResponseModel.setStatus(messageRepository.findByCode(0));
            apiResponseModel.setData(maps);

        }catch (Exception e){
            apiResponseModel.setStatus(messageRepository.findByCode(1));
        }
        return apiResponseModel;
    }

    public ApiResponseModel getSale(Integer id){
        ApiResponseModel apiResponseModel=new ApiResponseModel();
        try {
            Optional<Magazin> magazinOptional=magazinRepository.findById(id);
            if (magazinOptional.isPresent()){
                List<ResSale> resSales=saleRepository.findAllByMagazin(magazinOptional.get()).stream().map(sale -> sortSale(sale)).collect(Collectors.toList());
            apiResponseModel.setStatus(messageRepository.findByCode(0));
            apiResponseModel.setData(resSales);
            }else {
                apiResponseModel.setStatus(messageRepository.findByCode(101));
            }
        }catch (Exception e){
            apiResponseModel.setStatus(messageRepository.findByCode(1));
        }
        return apiResponseModel;
    }

    public ResSale sortSale(Sale sale){
        ResSale resSale=new ResSale();
        resSale.setId(sale.getId());
        resSale.setPurchase(sale.getPurchase().getId());
        resSale.setProductName(sale.getPurchase().getProduct().getName());
        resSale.setPrice(sale.getPrice());
        resSale.setDate(sale.getDate());
        resSale.setCount(sale.getCount());
        return resSale;
    }


    public Respons deleteSale(Integer id){
        Respons respons=new Respons();
        try {
            Optional<Sale> saleOptional=saleRepository.findById(id);
            if (saleOptional.isPresent()){
                saleRepository.delete(saleOptional.get());
                respons.setStatus(messageRepository.findByCode(0));
            }else {
                respons.setStatus(messageRepository.findByCode(108));
            }
        }catch (Exception e){
            respons.setStatus(messageRepository.findByCode(1));
        }
        return respons;
    }

    public Respons editSale(Integer id,ReqSale reqSale){
        Respons respons=new Respons();
        try {
            Optional<Sale> saleOptional=saleRepository.findById(id);
            if (saleOptional.isPresent()){
                Optional<Purchase> purchase=purchaseRepository.findById(reqSale.getPurchaseId());
                if (purchase.isPresent()){
                    saleOptional.get().setPurchase(purchase.get());
                    saleOptional.get().setCount(reqSale.getCount());
                    saleOptional.get().setPrice(reqSale.getPrice());
                    saleOptional.get().setDate(reqSale.getDate());
                    saleRepository.save(saleOptional.get());
                    respons.setStatus(messageRepository.findByCode(0));
                }else {
                    respons.setStatus(messageRepository.findByCode(106));
                }
            }else {
                respons.setStatus(messageRepository.findByCode(108));
            }

        }catch (Exception e){
            respons.setStatus(messageRepository.findByCode(1));
        }
        return respons;
    }
}
