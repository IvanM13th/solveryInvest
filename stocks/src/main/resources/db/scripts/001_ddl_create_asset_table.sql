create table asset
(
    id       serial primary key,
    figi     varchar(255),
    ticker   varchar(255),
    lot      bigint,
    currency varchar(255),
    name     varchar(255)
);