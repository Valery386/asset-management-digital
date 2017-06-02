package com.example.demo.service;


import com.example.demo.entities.Shop;
import com.example.demo.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
    public Shop findOneByName(String shopName) {
        Shop shopFound = shopRepository.findOneByShopName(shopName);

        return shopFound;
    }

    @Override
    public Shop create(Shop shop) {
        Number shopId = null;
        shop.setInformationAboutVersion("This shop is new, so it was created");
        shopId = shopRepository.createShop(shop);
        shop.setShopId(shopId.intValue());

        return shop;
    }

    @Override
    public Shop update(Shop shop) {
        shop.setInformationAboutVersion("This shop already exist, so it was updated");
        shopRepository.updateShop(shop);

        return shop;
    }

    @Override
    public Shop save(Shop shop) {
        Assert.notNull(shop, "No shop object found in the request body");
        Shop shopFound = this.findOneByName(shop.getShopName());
        if (shopFound == null) {
            return this.create(shop);
        } else {
            shop.setShopId(shopFound.getShopId());
            return this.update(shop);
        }
    }
}