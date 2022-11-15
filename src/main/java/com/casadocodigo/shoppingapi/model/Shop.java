package com.casadocodigo.shoppingapi.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.casadocodigo.shoppingapi.dto.ShopDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "shop")
@Table(schema = "shopping")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userIdentifier;
    private Float total;
    private Date date;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "item", joinColumns = @JoinColumn(name = "shop_id"))
    private List<Item> items;

    public static Shop convert(ShopDTO dto) {
        return Shop.builder()
            .userIdentifier(dto.getUserIdentifier())
            .total(dto.getTotal())
            .date(dto.getDate())
            .items(
                dto.getItems()
                    .stream()
                    .map(Item::convert)
                    .collect(Collectors.toList())
            )
            .build();
    }
    
}
