package com.casadocodigo.shoppingapi.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casadocodigo.shoppingapi.model.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    
    List<Shop> findAllByUserIdentifier(String userIdentifier);

    List<Shop> findAllByTotalGreaterThan(Float total);

    List<Shop> findAllByDateGreaterThanEqual(Date date);
}
