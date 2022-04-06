package com.example.magazin.payloat;

import com.example.magazin.entity.Purchase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResSale {

    private Integer id;

    private Integer purchase;

    private String productName;

    private Integer count;

    private Double price;

    private String date;

}
