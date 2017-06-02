package com.example.demo.service;

import com.example.demo.entities.Shop;

/**
 * Created by damz on 6/1/2017.
 */
public interface ShopService {

    Shop findOneByName(String shopName);

    Shop create(Shop shop);

    Shop update(Shop shop);

    Shop save(Shop shop);
}