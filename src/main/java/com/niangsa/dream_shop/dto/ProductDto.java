package com.niangsa.dream_shop.dto;

import com.niangsa.dream_shop.entities.Category;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
public class ProductDto {
    private  Long id;
    private String name;
    private String description;
    private BigDecimal price;
   // @NotNull(message = "L'inventory est obligatoire")
    private int inventory;
  //  @NotNull(message = "La marque est obligatoire")
    private String brand;
    private Category category;
    private List<ImageDto> images;
}
