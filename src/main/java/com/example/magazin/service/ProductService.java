package com.example.magazin.service;

import com.example.magazin.entity.Category;
import com.example.magazin.entity.Magazin;
import com.example.magazin.entity.Product;
import com.example.magazin.entity.Purchase;
import com.example.magazin.payloat.ApiResponseModel;
import com.example.magazin.payloat.ReqFilterProduct;
import com.example.magazin.payloat.ReqProduct;
import com.example.magazin.payloat.Respons;
import com.example.magazin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    MagazinRepository magazinRepository;

    private final JdbcTemplate jdbcTemplate;

    public ProductService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Respons addProduct(Integer id,ReqProduct reqProduct){
        Respons respons=new Respons();
        try {
            Optional<Magazin> magazinOptional=magazinRepository.findById(id);
            if (magazinOptional.isPresent()) {
                    Optional<Category> category = categoryRepository.findById(reqProduct.getCategoryId());
                    Product product = new Product();
                    product.setName(reqProduct.getName());
                    product.setCategory(category.get());
                    product.setMagazin(magazinOptional.get());
                    productRepository.save(product);
                    magazinRepository.save(magazinOptional.get());
                    respons.setStatus(messageRepository.findByCode(0));
            }else {
                respons.setStatus(messageRepository.findByCode(101));
            }

        }catch (Exception e){
            respons.setStatus(messageRepository.findByCode(1));
        }
        return respons;
    }

    public ApiResponseModel getAllProduct(){
        ApiResponseModel apiResponseModel=new ApiResponseModel();
        try {
            List<Product> productList=productRepository.findAll();
            apiResponseModel.setData(productList);
            apiResponseModel.setStatus(messageRepository.findByCode(0));

        }catch (Exception e){
            apiResponseModel.setStatus(messageRepository.findByCode(1));
        }
        return apiResponseModel;
    }

    public ApiResponseModel getProduct(Integer id){
        ApiResponseModel apiResponseModel=new ApiResponseModel();
        try {
            Optional<Magazin> magazinOptional=magazinRepository.findById(id);
            if (magazinOptional.isPresent()){
                List<Product> productList=productRepository.findAllByMagazin(magazinOptional.get());
                apiResponseModel.setStatus(messageRepository.findByCode(0));
                apiResponseModel.setData(productList);

            }else {
                apiResponseModel.setStatus(messageRepository.findByCode(101));
            }

        }catch (Exception e){
            apiResponseModel.setStatus(messageRepository.findByCode(1));
        }
        return apiResponseModel;
    }


    public Respons deleteProduct(Integer id){
        Respons respons=new Respons();
        try {
            Optional<Product> productOptional=productRepository.findById(id);
            if (productOptional.isPresent()){
                Optional<Purchase> purchaseOptional=purchaseRepository.findByProduct(productOptional.get());
                if (purchaseOptional.isEmpty()){
                    productRepository.delete(productOptional.get());
                    respons.setStatus(messageRepository.findByCode(0));
                }else {
                    respons.setStatus(messageRepository.findByCode(110));
                }
            }else {
                respons.setStatus(messageRepository.findByCode(105));
            }

        }catch (Exception e){
            respons.setStatus(messageRepository.findByCode(1));
        }
        return respons;
    }

    public Respons editProduct(Integer id,ReqProduct reqProduct){
        Respons respons=new Respons();
        try {
            Optional<Product> productOptional=productRepository.findById(id);
            if (productOptional.isPresent()){
                Optional<Category> categoryOptional=categoryRepository.findById(reqProduct.getCategoryId());
                if (categoryOptional.isPresent()){
                  productOptional.get().setName(reqProduct.getName());
                  productOptional.get().setCategory(categoryOptional.get());
                  productRepository.save(productOptional.get());
                  respons.setStatus(messageRepository.findByCode(0));
                }else {
                    respons.setStatus(messageRepository.findByCode(103));
                }
            }else {
                respons.setStatus(messageRepository.findByCode(105));
            }

        }catch (Exception e){
            respons.setStatus(messageRepository.findByCode(1));
        }
        return respons;
    }

    public ApiResponseModel filterProduct(ReqFilterProduct reqFilterProduct){
        ApiResponseModel apiResponseModel=new ApiResponseModel();
        try {
            String str="";
            if (reqFilterProduct.getCategoryName()!=null){
                str+="select * from product p inner join category c on c.id = p.category_id where upper(c.name) like upper('%"+reqFilterProduct.getCategoryName()+"%')";
            }
            if (reqFilterProduct.getName()!=null){
                str+=" and upper(p.name) like upper('%"+reqFilterProduct.getName()+"%')";
            }
            if (str==null){
                List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select * from product");
                apiResponseModel.setStatus(messageRepository.findByCode(0));
                apiResponseModel.setData(mapList);
                return apiResponseModel;
            }
            if (reqFilterProduct.getCategoryName()!=null && reqFilterProduct.getName()!=null){
                List<Map<String,Object>> mapList=jdbcTemplate.queryForList(str);
                apiResponseModel.setStatus(messageRepository.findByCode(0));
                apiResponseModel.setData(mapList);
                return apiResponseModel;
            }
            if (reqFilterProduct.getCategoryName()!=null && reqFilterProduct.getName()==null){
                List<Map<String,Object>> mapList=jdbcTemplate.queryForList(str);
                apiResponseModel.setStatus(messageRepository.findByCode(0));
                apiResponseModel.setData(mapList);
                return apiResponseModel;
            }
            if (reqFilterProduct.getCategoryName()==null && reqFilterProduct.getName()!=null){
                List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select * from product p where id>0 "+str);
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
