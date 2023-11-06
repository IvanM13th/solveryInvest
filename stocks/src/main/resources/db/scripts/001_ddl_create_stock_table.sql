create table stock (
    id serial primary key,
    full_name varchar(255),
    short_name varchar(255),
    current_price real,
    last_update timestamp(6)
);