package com.example.magazin.payloat;

import com.example.magazin.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Respons {

    private Status status;

    public void setStatus(Message status) {
        this.status =new Status(status.getCode(),status.getMessage(),status.getMessage_ru(), status.getMessage_uz());
    }


}
