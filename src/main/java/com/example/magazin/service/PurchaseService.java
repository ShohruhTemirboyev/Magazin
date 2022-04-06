package com.example.magazin.service;

import com.example.magazin.entity.Magazin;
import com.example.magazin.entity.Product;
import com.example.magazin.entity.Purchase;
import com.example.magazin.entity.Sale;
import com.example.magazin.payloat.*;
import com.example.magazin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    MagazinRepository magazinRepository;

    private final JdbcTemplate jdbcTemplate;

    public PurchaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Respons addPurchase(Integer id,ReqPurchase reqPurchase){
        Respons respons=new Respons();
        try {
            Optional<Product> productOptional=productRepository.findById(reqPurchase.getProductId());
            Optional<Purchase> purchaseOptional=purchaseRepository.findByProduct(productOptional.get());
            if (purchaseOptional.isPresent()){
                purchaseOptional.get().setCount(purchaseOptional.get().getCount()+ reqPurchase.getCount());
                purchaseOptional.get().setDate(reqPurchase.getDate());
                purchaseOptional.get().setPrice(reqPurchase.getPrice());
                purchaseOptional.get().setType(reqPurchase.getType());
                purchaseRepository.save(purchaseOptional.get());
                respons.setStatus(messageRepository.findByCode(0));
            }
            else {
                Purchase purchase = new Purchase();
                purchase.setType(reqPurchase.getType());
                purchase.setDate(reqPurchase.getDate());
                purchase.setCount(reqPurchase.getCount());
                purchase.setPrice(reqPurchase.getPrice());
                purchase.setProduct(productOptional.get());
                Optional<Magazin> magazinOptional=magazinRepository.findById(id);
                purchase.setMagazin(magazinOptional.get());
                purchaseRepository.save(purchase);
                respons.setStatus(messageRepository.findByCode(0));
            }

        }catch (Exception e){
         respons.setStatus(messageRepository.findByCode(1));
        }
        return respons;
    }

    public ApiResponseModel getAllPurchase(){
        ApiResponseModel apiResponseModel=new ApiResponseModel();
        try {
            List<Purchase> purchaseList=purchaseRepository.findAll();
            apiResponseModel.setData(purchaseList);
            apiResponseModel.setStatus(messageRepository.findByCode(0));

        }catch (Exception e){
            apiResponseModel.setStatus(messageRepository.findByCode(1));
        }
        return apiResponseModel;
    }


    public ApiResponseModel getPurchase(Integer id){
        ApiResponseModel apiResponseModel=new ApiResponseModel();
        try {

            Optional<Magazin> magazinOptional=magazinRepository.findById(id);
            if (magazinOptional.isPresent()){

                List<ResPurchase> resPurchases=purchaseRepository.findAllByMagazin(magazinOptional.get()).stream().map(purchase -> sortPurchase(purchase)).collect(Collectors.toList());

          apiResponseModel.setData(resPurchases);
          apiResponseModel.setStatus(messageRepository.findByCode(0));
            }
            else {
                apiResponseModel.setStatus(messageRepository.findByCode(101));
            }
        }catch (Exception e){
            apiResponseModel.setStatus(messageRepository.findByCode(1));
        }
        return apiResponseModel;
    }

    public ResPurchase sortPurchase(Purchase purchase){
        ResPurchase resPurchase=new ResPurchase(
                purchase.getId(),
                purchase.getProduct().getId(),
                purchase.getProduct().getName(),
                purchase.getCount(),
                purchase.getPrice(),
                purchase.getDate(),
                purchase.getType()
        );
        return resPurchase;
    }


    public ApiResponseModel filterCount(ReqFilter reqFilter){
        ApiResponseModel apiResponseModel=new ApiResponseModel();
        try {
            switch (reqFilter.getType()){
                case 1:{
                  List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where count="+reqFilter.getA());
                  apiResponseModel.setStatus(messageRepository.findByCode(0));
                  apiResponseModel.setData(mapList);
                };
                break;

                case 2:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where count="+reqFilter.getA()+" or count>"+reqFilter.getA()+"");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;

                case 3:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where count>"+reqFilter.getA()+"");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;

                case 4:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where count="+reqFilter.getA()+" or count<"+reqFilter.getA()+"");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;

                case 5:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where count<"+reqFilter.getA()+"");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;

                case 6:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where count!="+reqFilter.getA()+"");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;

                case 7:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where (count="+reqFilter.getA()+" or count>"+reqFilter.getA()+") and (count="+reqFilter.getB()+" or count<"+reqFilter.getB()+")");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;

                case 8:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where (count="+reqFilter.getA()+" or count>"+reqFilter.getA()+") and ( count<"+reqFilter.getB()+")");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;
                case 9:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where (count>"+reqFilter.getA()+") and (count="+reqFilter.getB()+" or count<"+reqFilter.getB()+")");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;

                case 10:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where (count>"+reqFilter.getA()+") and (count<"+reqFilter.getB()+")");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;
            }


        }catch (Exception e){
            System.out.println(e);
            apiResponseModel.setStatus(messageRepository.findByCode(1));
        }
        return apiResponseModel;
    }

    public ApiResponseModel filterPrice(ReqFilter reqFilter){
        ApiResponseModel apiResponseModel=new ApiResponseModel();
        try {
            switch (reqFilter.getType()){
                case 1:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where price="+reqFilter.getA());
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);
                };
                break;

                case 2:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where price="+reqFilter.getA()+" or price>"+reqFilter.getA()+"");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;

                case 3:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where price>"+reqFilter.getA()+"");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;

                case 4:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where price="+reqFilter.getA()+" or price<"+reqFilter.getA()+"");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;

                case 5:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where price<"+reqFilter.getA()+"");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;

                case 6:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where price!="+reqFilter.getA()+"");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;

                case 7:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where (price="+reqFilter.getA()+" or price>"+reqFilter.getA()+") and (price="+reqFilter.getB()+" or price<"+reqFilter.getB()+")");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;

                case 8:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where (price="+reqFilter.getA()+" or price>"+reqFilter.getA()+") and ( price<"+reqFilter.getB()+")");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;
                case 9:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where (price>"+reqFilter.getA()+") and (price="+reqFilter.getB()+" or price<"+reqFilter.getB()+")");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;

                case 10:{
                    List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase where (price>"+reqFilter.getA()+") and (price<"+reqFilter.getB()+")");
                    apiResponseModel.setStatus(messageRepository.findByCode(0));
                    apiResponseModel.setData(mapList);

                };
                break;
            }


        }catch (Exception e){
            System.out.println(e);
            apiResponseModel.setStatus(messageRepository.findByCode(1));
        }
        return apiResponseModel;
    }



    public Respons deletePurchase(Integer id){
        Respons respons=new Respons();
        try {
            Optional<Purchase> purchaseOptional=purchaseRepository.findById(id);
            if (purchaseOptional.isPresent()){
                List<Sale> saleList=saleRepository.findAllByPurchase(purchaseOptional.get());
                if (saleList.size()==0){
                    purchaseRepository.delete(purchaseOptional.get());
                    respons.setStatus(messageRepository.findByCode(0));
                }else {
                    respons.setStatus(messageRepository.findByCode(111));
                }
            }else {
                respons.setStatus(messageRepository.findByCode(106));
            }

        }catch (Exception e){
            respons.setStatus(messageRepository.findByCode(1));
        }
        return respons;
    }


    public Respons editpurchase(Integer id,ReqPurchase reqPurchase){
        Respons respons=new Respons();
        try {
            Optional<Purchase> purchaseOptional=purchaseRepository.findById(id);
            if (purchaseOptional.isPresent()){
                Optional<Product> productOptional=productRepository.findById(reqPurchase.getProductId());
                if (productOptional.isPresent()){
                    purchaseOptional.get().setProduct(productOptional.get());
                    purchaseOptional.get().setDate(reqPurchase.getDate());
                    purchaseOptional.get().setCount(reqPurchase.getCount());
                    purchaseOptional.get().setPrice(reqPurchase.getPrice());
                    purchaseOptional.get().setType(reqPurchase.getType());
                    purchaseRepository.save(purchaseOptional.get());
                    respons.setStatus(messageRepository.findByCode(0));
                }else {
                    respons.setStatus(messageRepository.findByCode(105));
                }
            }else {
                respons.setStatus(messageRepository.findByCode(106));
            }
        }catch (Exception e){
            respons.setStatus(messageRepository.findByCode(1));
        }
        return respons;
    }

    public ApiResponseModel filter(ReqFilterPurchase reqFilterPurchase){
        ApiResponseModel apiResponseModel=new ApiResponseModel();
        try {
            String str1="";
            String str2="";
            if (reqFilterPurchase.getProductName()!=null){
                str1+=" inner join product p on p.id = pur.product_id where upper(p.name) like upper('%"+reqFilterPurchase.getProductName()+"%')";
            }
            if (reqFilterPurchase.getDate1()!=null && reqFilterPurchase.getDate2()!=null){
               str2+=" pur.date between "+reqFilterPurchase.getDate1()+" and "+reqFilterPurchase.getDate2()+"";
            }

            if (str1==null && str2==null){
                List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase");
                apiResponseModel.setStatus(messageRepository.findByCode(0));
                apiResponseModel.setData(mapList);
                return apiResponseModel;
            }
            if (str1==null && str2!=null){
                List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase pur where "+str2);
                apiResponseModel.setStatus(messageRepository.findByCode(0));
                apiResponseModel.setData(mapList);
                return apiResponseModel;
            }
            if (str1!=null && str2==null){
                List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase pur "+str1);
                apiResponseModel.setStatus(messageRepository.findByCode(0));
                apiResponseModel.setData(mapList);
                return apiResponseModel;
            }
            if (str1!=null && str2!=null){
                List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select *from purchase pur "+str1+" and "+str2);
                apiResponseModel.setStatus(messageRepository.findByCode(0));
                apiResponseModel.setData(mapList);
                return apiResponseModel;
            }

        }catch (Exception e){
            apiResponseModel.setStatus(messageRepository.findByCode(1));
        }
        return apiResponseModel;
    }
}
