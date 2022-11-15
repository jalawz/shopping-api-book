package com.casadocodigo.shoppingapi.service;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.casadocodigo.shoppingapi.dto.ShopDTO;
import com.casadocodigo.shoppingapi.model.Shop;
import com.casadocodigo.shoppingapi.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

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
        return shopRepository.findAllByDateGreaterThanEquals(shopDTO.getDate())
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

    public ShopDTO save(ShopDTO dto) {
        dto.setTotal(
            dto.getItems()
                .stream()
                .map(item -> item.getPrice())
                .reduce((float) 0, Float::sum)
        );

        Shop shopEntity = Shop.convert(dto);
        shopEntity.setDate(new Date());
        return ShopDTO.convert(shopRepository.save(shopEntity));
    }
    
}
