package com.niangsa.dream_shop.service.interfaces;
import  java.util.List;
import com.niangsa.dream_shop.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    ImageDto getImageById(Long id);
    void deleteImage(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long id);
    void updateImage(MultipartFile file, Long idProduct);
}
