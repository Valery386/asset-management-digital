create table t_shop(
  shop_id bigint identity (100) not null primary key,
  shop_name varchar_ignorecase(100) not null,
  shop_address_number int,
  shop_address_post_code int,
  information_about_version varchar_ignorecase(100),
  constraint unique_uk_1 unique(shop_name)
);