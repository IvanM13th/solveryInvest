create table portfolio
(
    id             serial primary key,
    user_id        bigint,
    portfolio_name varchar(255)
);

create table portfolio_asset
(
    id           serial primary key,
    portfolio_id bigint references portfolio,
    asset_id     bigint references asset,
    lots         bigint,
    amount       bigint
);

create table portfolio_history
(
    id                   serial primary key,
    date_time            timestamp(6),
    portfolio_id         bigint references portfolio,
    asset_operation_type varchar(255),
    lots         bigint,
    volume               smallint
);
