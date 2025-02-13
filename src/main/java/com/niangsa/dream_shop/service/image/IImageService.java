package com.niangsa.dream_shop.service.image;
import  java.util.List;
import com.niangsa.dream_shop.dto.ImageDto;
import com.niangsa.dream_shop.entities.Image;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImage(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long id);
    void updateImage(MultipartFile file, Long idProduct);
}
