package com.casadocodigo.shoppingapi.service;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.casadocodigo.shoppingapi.dto.ShopDTO;
import com.casadocodigo.shoppingapi.dto.ShopReportDTO;
import com.casadocodigo.shoppingapi.model.Shop;
import com.casadocodigo.shoppingapi.repository.ReportRepository;
import com.casadocodigo.shoppingapi.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ReportRepository reportRepository;

    public List<ShopDTO> getAll() {
        return shopRepository.findAll()
            .stream()
            .map(ShopDTO::convert)
            .collect(toList());
    }

    public List<ShopDTO> getByUserIdentifier(String userIdentifier) {
        return shopRepository.findAllByUserIdentifier(userIdentifier)
            .stream()
            .map(ShopDTO::convert)
            .collect(toList());
    }

    public List<ShopDTO> getByDate(ShopDTO shopDTO) {
        return shopRepository.findAllByDateGreaterThanEqual(shopDTO.getDate())
            .stream()
            .map(ShopDTO::convert)
            .collect(toList());
    }

    public ShopDTO findById(Long productId) {
        return shopRepository.findById(productId)
            .map(ShopDTO::convert)
            .orElseThrow(() -> 
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Shop with identifier: %s not found", productId)
                )
            );
    }

    public Shop save(ShopDTO dto) {
        dto.setTotal(
            dto.getItems()
                .stream()
                .map(item -> item.getPrice())
                .reduce((float) 0, (a, b) -> Float.sum(a, b))
        );

        Shop shopEntity = Shop.convert(dto);
        shopEntity.setDate(new Date());
        return shopRepository.save(shopEntity);
    }

    public List<ShopDTO> getShopsByFilter(
        Date dataInicio,
        Date dataFim,
        Float valorMinimo
    ) {
        return reportRepository.getShopByFilters(dataInicio, dataFim, valorMinimo)
                    .stream()
                    .map(ShopDTO::convert)
                    .collect(toList());
    }

    public ShopReportDTO getReportByDate(
        Date dataInicio,
        Date dataFim
    ) {
        return reportRepository.getReportByDate(dataInicio, dataFim);
    }
    
}
