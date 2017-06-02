package com.example.demo.repository;

import com.example.demo.entities.Shop;

/**
 * Created by damz on 6/1/2017.
 */
public interface ShopRepository {

    Number createShop(Shop shop);

    Shop findOneByShopName(String shopName);

    void updateShop(Shop shopFound);
}