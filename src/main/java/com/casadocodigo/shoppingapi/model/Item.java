package com.casadocodigo.shoppingapi.model;

import javax.persistence.Embeddable;

import com.casadocodigo.shoppingapi.dto.ItemDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    private String productIdentifier;
    private Float price;

    public static Item convert(ItemDTO dto) {
        return Item.builder()
            .productIdentifier(dto.getProductIdentifier())
            .price(dto.getPrice())
            .build();
    }

}
