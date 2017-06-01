package com.example.demo.repository;

import com.example.demo.entities.Shop;
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
    public Number create(Shop shop) {
        final String SQL = "insert into t_shop ("
                + " shop_name, "
                + " shop_address_number, "
                + " shop_address_post_code "
                + " ) values ( "
                + " :shopName, "
                + " :shopAddressNumber, "
                + " :shopAddressPostCode"
                + " )";

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("shopName", shop.getShopName())
                .addValue("shopAddressNumber", shop.getShopAddressNumber())
                .addValue("shopAddressPostCode", shop.getShopAddressPostCode());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = jdbcOperations.update(SQL, namedParameters, keyHolder);

        if (rows != 1)
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(
                    String.format("Insertion of Shop %s returned %d rows: Should be 1.", shop, rows),
                    1, rows);

        return keyHolder.getKey();
    }
}