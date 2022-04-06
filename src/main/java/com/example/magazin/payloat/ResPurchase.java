package com.example.magazin.payloat;

import com.example.magazin.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResPurchase {


    private Integer id;

    private Integer product;

    private String productName;

    private Integer count;

    private Double price;

    private String date;

    private String type;
}
