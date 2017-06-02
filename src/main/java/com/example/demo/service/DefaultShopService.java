package com.example.demo.service;


import com.example.demo.entities.Shop;
import com.example.demo.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by damz on 6/1/2017.
 */
@Service
public class DefaultShopService implements ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public DefaultShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public Shop save(Shop shop) {

        Shop shopFound = shopRepository.findOne(shop.getShopName());

        Number shopId = shopRepository.create(shop);

        shop.setShopId(shopId.intValue());

        return shop;
    }
}