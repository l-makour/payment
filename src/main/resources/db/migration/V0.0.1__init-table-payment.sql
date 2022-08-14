CREATE TABLE IF NOT EXISTS payment (
    id integer primary key,
    amount float,
    order_id integer,
    payment_date timestamp
);
insert into payment (id,amount,order_id, payment_date) values (1,100,1,'2020-01-01');
insert into payment (id,amount,order_id, payment_date) values (2,200,2,'2020-02-02');