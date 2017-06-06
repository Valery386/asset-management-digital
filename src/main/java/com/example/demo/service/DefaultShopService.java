package com.example.demo.service;


import com.example.demo.entities.Shop;
import com.example.demo.repository.ShopRepository;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.io.IOException;

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

    public void address(Shop shop) throws InterruptedException, ApiException, IOException {
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDRU7REviNXgRgYNGirMqEXHeNXxMoAsQs");
        GeocodingResult[] results = GeocodingApi.geocode(context, "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
        shop.setAddress(results[0].formattedAddress);
        System.out.println(results[0].formattedAddress);
    }


    @Override
    public Shop findOneByName(String shopName) {
        Shop shopFound = shopRepository.findOneByShopName(shopName);

        return shopFound;
    }

    @Override
    public Shop create(Shop shop) {
        Number shopId;
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
    public Shop save(Shop shop) throws InterruptedException, ApiException, IOException {
        Assert.notNull(shop, "No shop object found in the request body");
        Shop shopFound = this.findOneByName(shop.getShopName());
        if (shopFound == null) {
            this.address(shop);
            return this.create(shop);
        } else {
            shop.setShopId(shopFound.getShopId());
            shopFound.setInformationAboutVersion("This shop already exist, so it was updated. This information is from the replaced shop.");
            this.update(shop);
            return shopFound;
        }
    }
}