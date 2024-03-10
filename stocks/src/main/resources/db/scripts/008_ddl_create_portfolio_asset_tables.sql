create table portfolio_asset
(
    id            serial primary key,
    last_update   timestamp(6),
    portfolio_id  bigint references portfolio,
    asset_id      bigint references asset,
    lots          bigint,
    average_price bigint
);