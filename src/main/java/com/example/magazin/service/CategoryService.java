package com.example.magazin.service;

import com.example.magazin.entity.Category;
import com.example.magazin.entity.Magazin;
import com.example.magazin.entity.Product;
import com.example.magazin.payloat.ApiResponseModel;
import com.example.magazin.payloat.ReqCategory;
import com.example.magazin.payloat.Respons;
import com.example.magazin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MagazinRepository magazinRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    private final JdbcTemplate jdbcTemplate;

    public CategoryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Respons addCategory(Integer id,ReqCategory reqCategory){
        Respons respons=new Respons();
        try {
            if (!categoryRepository.existsByName(reqCategory.getName())){
                Category category=new Category();
                category.setName(reqCategory.getName());
                Optional<Magazin> magazinOptional=magazinRepository.findById(id);
                category.setMagazin(magazinOptional.get());
                categoryRepository.save(category);

                respons.setStatus(messageRepository.findByCode(0));
            }
            else {
                respons.setStatus(messageRepository.findByCode(102));
            }

        }catch (Exception e){
            respons.setStatus(messageRepository.findByCode(1));
        }
        return respons;
    }

    public ApiResponseModel getAllCategory(){
        ApiResponseModel apiResponseModel=new ApiResponseModel();
        try {
            List<Category> categoryList=categoryRepository.findAll();
            apiResponseModel.setStatus(messageRepository.findByCode(0));
            apiResponseModel.setData(categoryList);

        }catch (Exception e){
            apiResponseModel.setStatus(messageRepository.findByCode(1));
        }
        return apiResponseModel;
    }

    public ApiResponseModel getCategory(Integer id){
        ApiResponseModel apiResponseModel=new ApiResponseModel();
        try {Optional<Magazin> magazinOptional=magazinRepository.findById(id);
            if (magazinOptional.isPresent()){
            List<Category> categories=categoryRepository.findAllByMagazin(magazinOptional.get());
                apiResponseModel.setStatus(messageRepository.findByCode(0));
                apiResponseModel.setData(categories);}
            else {
                apiResponseModel.setStatus(messageRepository.findByCode(101));
            }
        }
        catch (Exception e){
            apiResponseModel.setStatus(messageRepository.findByCode(1));
        }
        return apiResponseModel;
    }


    public Respons deleteCategory(Integer id){
        Respons respons=new Respons();
        try {
            Optional<Category> categoryOptional=categoryRepository.findById(id);
            if (categoryOptional.isPresent()){
                List<Product> productOptional=productRepository.findAllByCategory(categoryOptional.get());
               if (productOptional.size()==0){
                categoryRepository.deleteById(id);
               respons.setStatus(messageRepository.findByCode(0));
               }
               else {
                   respons.setStatus(messageRepository.findByCode(109));
               }
            }
            else {
                respons.setStatus(messageRepository.findByCode(103));
            }

        }catch (Exception e){
            respons.setStatus(messageRepository.findByCode(1));
        }
        return respons;
    }

    public Respons editCategory(Integer id,ReqCategory reqCategory){
        Respons respons=new Respons();
        try {
            Optional<Category> categoryOptional=categoryRepository.findById(id);
            if (categoryOptional.isPresent()){
                categoryOptional.get().setId(categoryOptional.get().getId());
                categoryOptional.get().setName(reqCategory.getName());
                categoryRepository.save(categoryOptional.get());
                respons.setStatus(messageRepository.findByCode(0));
            }else {
                respons.setStatus(messageRepository.findByCode(103));
            }
        }catch (Exception e){
            respons.setStatus(messageRepository.findByCode(1));
        }
        return respons;
    }

    public ApiResponseModel filterCategory(ReqCategory reqCategory){
        ApiResponseModel apiResponseModel=new ApiResponseModel();
        try {
            if (reqCategory.getName()!=null){
                List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select * from category c where upper(c.name) like upper('%"+reqCategory.getName()+"%')");
                apiResponseModel.setStatus(messageRepository.findByCode(0));
                apiResponseModel.setData(mapList);
            }else {
                List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select * from category");
                apiResponseModel.setStatus(messageRepository.findByCode(0));
                apiResponseModel.setData(mapList);
            }
        }catch (Exception e){
            apiResponseModel.setStatus(messageRepository.findByCode(1));
        }
        return apiResponseModel;
    }

}
