package com.niangsa.dream_shop.service.image;

import com.niangsa.dream_shop.dto.ImageDto;
import com.niangsa.dream_shop.entities.Image;
import com.niangsa.dream_shop.entities.Product;
import com.niangsa.dream_shop.exceptions.ApiRequestException;
import com.niangsa.dream_shop.mappers.ImageMapper;
import com.niangsa.dream_shop.mappers.ProductMapper;
import com.niangsa.dream_shop.repositories.ImageRepository;
import com.niangsa.dream_shop.service.product.IProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements IImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final IProductService productService;
    private final ProductMapper productMapper;
    private static final String DOWNLOAD_PATH = "/api/v1/images/image/download/";
    /**
     * @param id long
     * @return image
     */
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                //.map(imageMapper::toImagedDto)
                .orElseThrow(()-> new EntityNotFoundException("Image not found provided id:"+ id));
    }

    /**
     * @param id long
     */
    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(
                        imageRepository::delete,
                        ()->{
                            throw  new EntityNotFoundException("Image not found");
                        }
                );
    }

    /**
     * find product and assign images created to product
     *
     * @param files List of image
     * @param idProduct current product's id
     * @return list of images
     */
    @Transactional
    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long idProduct) {
        List<ImageDto> imageDtos = new ArrayList<>();
        Product product = productMapper.toProductEntity(productService.getById(idProduct));
        for (MultipartFile file: files){
            try {
                List<Image> images = new ArrayList<>();
                Image image = new Image();
                image.setProduct(product);
                image.setImages(new SerialBlob(file.getBytes()));
                image.setFileType(file.getContentType());
                image.setFileName(file.getOriginalFilename());

                Image savedImage = imageRepository.save(image);
                String downloadUrl = DOWNLOAD_PATH + savedImage.getId();
                image.setDownloadUrl(downloadUrl);
                images.add(savedImage);
                product.setImages(images);
                imageDtos.add(imageMapper.toImagedDto(savedImage));
            } catch (SQLException | IOException e)  {
                throw new EntityNotFoundException(e.getMessage());
            }
        }
        return imageDtos;
    }

    /**
     *
     * @param file represent image file
     * @param id long
     */

    @Override
    public void updateImage(MultipartFile file, Long id) {
        Image image = getImageById(id);
        image.setFileName(file.getOriginalFilename());
        try {
            image.setImages(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }
}
