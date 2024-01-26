create table balance
(
    id      serial primary key,
    user_id bigint,
    balance numeric(10, 2)
);

create table balance_history
(
    id             serial primary key,
    date_time      timestamp(6),
    balance_id     bigint references balance,
    operation_type varchar(255),
    amount         numeric(10, 2)
);
