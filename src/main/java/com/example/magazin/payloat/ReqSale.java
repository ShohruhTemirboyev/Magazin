package com.example.magazin.payloat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqSale {

    private Integer purchaseId;

    private Integer count;

    private Double price;

    private String date;
}
