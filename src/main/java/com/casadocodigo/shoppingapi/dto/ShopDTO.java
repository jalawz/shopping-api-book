package com.casadocodigo.shoppingapi.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.casadocodigo.shoppingapi.model.Shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopDTO {
    
    @NotBlank
    private String userIdentifier;
    private Float total;
    private Date date;
    @NotNull
    private List<ItemDTO> items;

    public static ShopDTO convert(Shop shop) {
        return ShopDTO.builder()
            .userIdentifier(shop.getUserIdentifier())
            .total(shop.getTotal())
            .date(shop.getDate())
            .items(
                shop.getItems().isEmpty()
                    ? null
                    : shop.getItems().stream().map(ItemDTO::convert)
                        .collect(Collectors.toList())
            )
            .build();
    }
}
