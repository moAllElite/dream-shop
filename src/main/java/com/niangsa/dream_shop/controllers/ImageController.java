package com.niangsa.dream_shop.controllers;


import com.niangsa.dream_shop.dto.ImageDto;
import com.niangsa.dream_shop.response.ApiResponse;
import com.niangsa.dream_shop.service.interfaces.IImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("images")
public class ImageController {
    private static final HttpStatus INTERNAL_ERROR_SERVER = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    //inject services & mappers
    private final IImageService imageService;

    /**
     *
     * @param files represenet images
     * @param idProduct for identifiant product long
     * @return ApiResponse
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestBody  List<MultipartFile> files,@RequestParam(name = "productId") Long idProduct){
        try {
            List<ImageDto> results= imageService.saveImages(files,idProduct);
            return ResponseEntity.ok(new ApiResponse("Upload successfull",results));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_ERROR_SERVER).body(new ApiResponse("Upload failed",e.getMessage()));
        }
    }


    /**
     *
     * @param idImage lond
     * @return API Message
     * @throws SQLException on error
     */
    @GetMapping("/image/download/{idImage}")
    public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long idImage) throws SQLException {
        ImageDto image = imageService.getImageById(idImage);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=\"" +image.getFileName() + "\"")
                .body(resource);
    }

    /**
     *
     * @param idImage LONG
     * @param file IMAGE
     * @return API MESSAGE & http status
     */
    @PutMapping("/image/{idImage}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long idImage, @RequestBody MultipartFile file) {
        try {
            ImageDto imageDto = imageService.getImageById(idImage);
            if (imageDto != null) {
                imageService.updateImage(file, idImage);
                return ResponseEntity.ok(new ApiResponse("Update successfull", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Update failed !", e.getMessage()));
        }
        return ResponseEntity.status(INTERNAL_ERROR_SERVER).body(new ApiResponse("Update failed!", INTERNAL_ERROR_SERVER));
    }

    /**
     *
     * @param idImage long
     * @return API MESSAGE & http status
     */
    @DeleteMapping("/image/{idImage}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long idImage) {
        try {
            ImageDto imageDto = imageService.getImageById(idImage);
            if (imageDto != null) {
                imageService.deleteImage(idImage);
                return  ResponseEntity.ok(new ApiResponse("Successfully deleted", null));
            }
        }catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Delete failed !", e.getMessage()));
        }
        return ResponseEntity.status(INTERNAL_ERROR_SERVER).body(new ApiResponse("Delete failed!", INTERNAL_ERROR_SERVER));
    }
}
