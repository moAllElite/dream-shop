package com.niangsa.dream_shop.mappers;

import com.niangsa.dream_shop.dto.ImageDto;
import com.niangsa.dream_shop.entities.Image;
import org.mapstruct.Mapper;

@Mapper()
public interface ImageMapper {
    ImageDto toImagedDto(Image image);
    Image toImageEntity(ImageDto imageDto);
}
