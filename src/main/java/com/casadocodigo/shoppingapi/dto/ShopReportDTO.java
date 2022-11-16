package com.casadocodigo.shoppingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ShopReportDTO {
    
    private Integer count;
    private Double total;
    private Double mean;
}
