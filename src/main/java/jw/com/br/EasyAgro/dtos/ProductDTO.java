package jw.com.br.EasyAgro.dtos;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

public record ProductDTO(
         @NotBlank
         String name,

         BigDecimal price,

         String category,

         String description,

         String code,


         int quantityInStock,

         List<String>images) {
}
