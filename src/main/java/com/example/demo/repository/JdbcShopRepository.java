package com.example.demo.repository;

import com.example.demo.entities.Shop;
import com.example.demo.repository.mapping.extractor.ShopExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by damz on 6/1/2017.
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS)
public class JdbcShopRepository implements ShopRepository{

    private final NamedParameterJdbcOperations jdbcOperations;

    @Autowired
    public JdbcShopRepository(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Shop findOneByShopName(String shopName) {
        final String SQL = "select * from "
                + " t_shop "
                + " where shop_name = :shopName ";

        SqlParameterSource namedParameters = new MapSqlParameterSource("shopName", shopName);

        Shop shop = jdbcOperations.query(SQL, namedParameters, new ShopExtractor(shopName));

        return shop;
    }

    @Override
    public void updateShop(Shop shopFound) {
        String SQL = "update t_shop "
                + "set "
                + "   shop_address_number =:shopAddressNumber"
                + "   shop_address_post_code =:shopAddressPostCode"
                + "   information_about_version =:informationAboutVersion";
    }

    @Override
    public Number createShop(Shop shop) {
        final String SQL = "insert into t_shop ("
                + " shop_name, "
                + " shop_address_number, "
                + " shop_address_post_code, "
                + " information_about_version "
                + " ) values ( "
                + " :shopName, "
                + " :shopAddressNumber, "
                + " :shopAddressPostCode, "
                + " :informationAboutVersion "
                + " )";

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("shopName", shop.getShopName())
                .addValue("shopAddressNumber", shop.getShopAddressNumber())
                .addValue("shopAddressPostCode", shop.getShopAddressPostCode())
                .addValue("informationAboutVersion", shop.getInformationAboutVersion());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = jdbcOperations.update(SQL, namedParameters, keyHolder);

        if (rows != 1)
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
                    String.format("Insertion of Shop %s returned %d rows: Should be 1.", shop, rows),
                    1, rows);

        return keyHolder.getKey();
    }
}