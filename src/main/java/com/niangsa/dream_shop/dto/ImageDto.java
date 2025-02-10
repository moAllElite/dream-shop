package com.niangsa.dream_shop.dto;

import lombok.*;

import java.sql.Blob;

@Getter
@Setter
@RequiredArgsConstructor
public class ImageDto {
    private Long id;
    private String fileName;
    private Blob images;
    private String fileType;
    private String downloadUrl;
}
