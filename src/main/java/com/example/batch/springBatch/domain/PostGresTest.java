package com.example.batch.springBatch.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
public class PostGresTest {


    @Id
    private int id;
    private String Url;
    private String password;
}
