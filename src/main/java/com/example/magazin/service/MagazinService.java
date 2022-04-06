package com.example.magazin.service;

import com.example.magazin.entity.Magazin;
import com.example.magazin.payloat.ApiResponseModel;
import com.example.magazin.payloat.ReqMagazin;
import com.example.magazin.payloat.Respons;
import com.example.magazin.repository.MagazinRepository;
import com.example.magazin.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MagazinService {

    @Autowired
    MagazinRepository magazinRepository;

    @Autowired
    MessageRepository messageRepository;

    private final JdbcTemplate jdbcTemplate;

    public MagazinService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Respons addMagazin(ReqMagazin reqMagazin){
        Respons respons=new Respons();
        try {
            Magazin magazin=new Magazin();
            magazin.setName(reqMagazin.getName());
            magazin.setAddress(reqMagazin.getAddress());
            magazin.setPhone(reqMagazin.getPhone());
            magazinRepository.save(magazin);
            respons.setStatus(messageRepository.findByCode(0));
        }catch (Exception e){
            respons.setStatus(messageRepository.findByCode(1));
        }
        return respons;
    }

    public ApiResponseModel getMagazinList(){
        ApiResponseModel apiResponseModel=new ApiResponseModel();
        try {
            List<Magazin> magazinList=magazinRepository.findAll();
            apiResponseModel.setStatus(messageRepository.findByCode(0));
            apiResponseModel.setData(magazinList);

        }catch (Exception e){
            apiResponseModel.setStatus(messageRepository.findByCode(1));
        }
        return apiResponseModel;
    }

    public ApiResponseModel getMagazin(Integer id){
        ApiResponseModel apiResponseModel=new ApiResponseModel();
        try {
            Optional<Magazin> magazinOptional=magazinRepository.findById(id);
            if (magazinOptional.isPresent()){
                apiResponseModel.setStatus(messageRepository.findByCode(0));
                apiResponseModel.setData(magazinOptional.get());

            }else {
                apiResponseModel.setStatus(messageRepository.findByCode(101));
            }

        }catch (Exception e){
            apiResponseModel.setStatus(messageRepository.findByCode(1));
        }
        return apiResponseModel;
    }


    public Respons deleteMagazni(Integer id){
        Respons respons=new Respons();
        try {
            Optional<Magazin> magazinOptional=magazinRepository.findById(id);
            if (magazinOptional.isPresent()){
                  magazinRepository.delete(magazinOptional.get());
                  respons.setStatus(messageRepository.findByCode(0));
            }else {
                respons.setStatus(messageRepository.findByCode(101));
            }

        }catch (Exception e){
            respons.setStatus(messageRepository.findByCode(1));
        }
        return respons;
    }

    public Respons editMagazin(ReqMagazin reqMagazin,Integer id){
        Respons respons=new Respons();
        try {
            Optional<Magazin> magazinOptional=magazinRepository.findById(id);
            if (magazinOptional.isPresent()){
              magazinOptional.get().setAddress(reqMagazin.getAddress());
              magazinOptional.get().setName(reqMagazin.getName());
              magazinOptional.get().setPhone(reqMagazin.getPhone());
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

    public ApiResponseModel filterMagazin(ReqMagazin reqMagazin){
        ApiResponseModel apiResponseModel=new ApiResponseModel();
        try {
            String str="";
            if (reqMagazin.getName()!=null){
                str+=" and upper(m.name) like upper('%"+reqMagazin.getName()+"%')";
            }
            if (reqMagazin.getPhone()!=null){
                str+=" and upper(m.phone) like upper('%"+reqMagazin.getPhone()+"%')";
            }
            if (reqMagazin.getAddress()!=null){
                str+=" and upper(m.address) like upper('%"+reqMagazin.getAddress()+"%')";
            }
            if (str!=null){
                List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select * from magazin m where m.id>0"+str);
                apiResponseModel.setData(mapList);
                apiResponseModel.setStatus(messageRepository.findByCode(0));
            }else {
                List<Map<String,Object>> mapList=jdbcTemplate.queryForList("select * from magazin");
                apiResponseModel.setStatus(messageRepository.findByCode(0));
                apiResponseModel.setData(mapList);
            }

        }catch (Exception e){
            apiResponseModel.setStatus(messageRepository.findByCode(1));
        }
        return apiResponseModel;
    }
}
