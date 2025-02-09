package com.niangsa.dream_shop.dto;

import com.niangsa.dream_shop.entities.Product;
import lombok.*;

import java.sql.Blob;

@Getter
@Setter
@RequiredArgsConstructor
public class ImageDto {
    private Long id;
    private String fileName;
    private Blob image;
    private String fileType;
    private String downloadUrl;
    private ProductDto productDto;
}
