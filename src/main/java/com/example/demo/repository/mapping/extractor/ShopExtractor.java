package com.example.demo.repository.mapping.extractor;

import com.example.demo.entities.Shop;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by damz on 6/1/2017.
 */
public class ShopExtractor implements ResultSetExtractor<Shop> {

    private Shop shop = null;
    private String shopName = null;
    private int shopId;
    
    @Override
    public Shop extractData(ResultSet rs) throws SQLException, DataAccessException {
       int rowNum = 0;
       while (rs.next()) {
           mapShopRows(rs, rowNum++);
       }
       return shop;
    }

    private Shop mapShopRows(ResultSet rs, int rowNum) throws SQLException {
        if (rowNum == 0) {
           shop = new Shop();
           shop.setShopId(rs.getInt("shop_id"));
           shop.setShopName(rs.getString("shop_name"));
           shop.setShopAddressNumber(rs.getInt("shop_address_number"));
           shop.setShopAddressPostCode(rs.getInt("shop_address_post_code"));
        } else {
            if (rs.getInt("shop_id") != shop.getShopId())
                throw (this.shopName != null ? new IncorrectResultSizeDataAccessException("findOneByShopName " + shopName, 1, 2) : new IncorrectResultSizeDataAccessException("findById " + shopId, 1, 2));
        }
        return shop;
    }
}