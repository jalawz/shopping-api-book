package com.casadocodigo.shoppingapi.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.casadocodigo.shoppingapi.dto.ShopDTO;
import com.casadocodigo.shoppingapi.model.Shop;
import com.casadocodigo.shoppingapi.service.ShopService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shopping")
@RequiredArgsConstructor
public class ShopController {
    
    private final ShopService shopService;

    @GetMapping
    public ResponseEntity<List<ShopDTO>> getShops() {
        return ResponseEntity.ok(shopService.getAll());
    }

    @GetMapping("/shopByUser/{userIdentifier}")
    public ResponseEntity<List<ShopDTO>> getShopsByUserId(@PathVariable String userIdentifier) {
        return ResponseEntity
            .ok(shopService.getByUserIdentifier(userIdentifier));
    }

    @GetMapping("/shopByDate")
    public ResponseEntity<List<ShopDTO>> getShopsByDate(@RequestBody ShopDTO request) {
        return ResponseEntity.ok(shopService.getByDate(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(shopService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> newShop(@Valid @RequestBody ShopDTO request) {
        Shop shop = shopService.save(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(shop.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
