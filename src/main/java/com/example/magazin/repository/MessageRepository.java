package com.example.magazin.repository;

import com.example.magazin.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message,Integer> {

    Message findByCode(Integer code);
}
