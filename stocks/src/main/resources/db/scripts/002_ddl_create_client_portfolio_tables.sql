/*
(
    id   serial primary key,
    name varchar(255)
);*/

create table portfolio
(
    id            serial primary key,
    name          varchar(255),
    /*client_id     bigint references client,*/
    client_id     bigint,
    creation_date timestamp(6)
);

create table portfolio_assets
(
    id            serial primary key,
    portfolio_id  bigint references portfolio,
    assets_id     bigint references asset,
    creation_date timestamp(6)
);
