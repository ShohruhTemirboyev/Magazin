package com.example.magazin.payloat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqPurchase {


    private Integer productId;

    private Integer count;

    private Double price;

    private String date;

    private String type;
}
