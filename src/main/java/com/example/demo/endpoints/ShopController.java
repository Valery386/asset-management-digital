package com.example.demo.endpoints;


import com.example.demo.entities.Shop;
import com.example.demo.service.ShopService;
import com.google.maps.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
    public ResponseEntity<Shop> saveShop(@RequestBody Shop shop) throws InterruptedException, ApiException, IOException {

        Shop shopReceive = shopService.save(shop);

        if(shopReceive == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(shopReceive, HttpStatus.CREATED);
    }


}