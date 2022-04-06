package com.example.magazin.payloat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqProduct {

    private String name;

    private Integer categoryId;
}
