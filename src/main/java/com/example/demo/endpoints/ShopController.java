package com.example.demo.endpoints;


import com.example.demo.entities.Shop;
import com.example.demo.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by damz on 6/1/2017.
 */
@RestController
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("/api/v1/shop")
    public ResponseEntity<Shop> saveShop(@RequestBody Shop shop){
        Assert.notNull(shop, "No shop object found in the request body");
        Shop shopReceive = null;
        Shop shopFound = shopService.findOneByName(shop.getShopName());

        if (shopFound == null) {
            shopReceive = shopService.create(shop);
        } else {
            shop.setShopId(shopFound.getShopId());
            shopReceive = shopService.update(shop);
        }

        if(shopReceive == null)
            return new ResponseEntity<Shop>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(shopReceive, HttpStatus.CREATED);
    }
}