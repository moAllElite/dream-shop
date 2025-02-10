package com.niangsa.dream_shop.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class ImageDto {
    private Long id;
    private String fileName;
    private String fileType;
    private String downloadUrl;
}
