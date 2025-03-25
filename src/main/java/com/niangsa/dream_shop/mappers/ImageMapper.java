package com.niangsa.dream_shop.mappers;

import com.niangsa.dream_shop.dto.ImageDto;
import com.niangsa.dream_shop.entities.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {Image.class})
public interface ImageMapper {
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "fileName", source = "entity.fileName")
    ImageDto toImagedDto(Image entity);
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "fileName", source = "dto.fileName")
    Image toImageEntity(ImageDto dto);
}
