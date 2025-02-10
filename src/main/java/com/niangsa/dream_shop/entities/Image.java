package com.niangsa.dream_shop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String fileName;
    private String fileType;
    @Lob()
    private Blob images;
    private String downloadUrl;
    @ManyToOne(  )
    @JoinColumn(name = "product_id")
    private Product product;
}
