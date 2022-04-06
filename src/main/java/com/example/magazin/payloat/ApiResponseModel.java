package com.example.magazin.payloat;
import com.example.magazin.entity.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponseModel {
   private Status status;
   private Object data;

   public void setStatus(Message message) {
      this.status =new Status(message.getCode(),message.getMessage(),message.getMessage_ru(), message.getMessage_uz());
   }



}
