CREATE TABLE discount_card (id INTEGER not NULL , number VARCHAR(255),
discount NUMERIC NOT NULL, PRIMARY KEY ( id ));

CREATE TABLE product (id INTEGER not NULL, name VARCHAR(255),
price NUMERIC NOT NULL, promotional BOOLEAN NOT NULL, PRIMARY KEY ( id ));

CREATE TABLE product_warehouse (id INTEGER not NULL, product INTEGER REFERENCES product (id),
amount INTEGER NOT NULL, PRIMARY KEY ( id ));

CREATE TABLE shop_receipt (id INTEGER not NULL, discount_card INTEGER REFERENCES discount_card (id),
date DATE NOT NULL, PRIMARY KEY ( id ));

CREATE TABLE shop_receipt_product_warehouses (id INTEGER not NULL, shop_receipt INTEGER REFERENCES shop_receipt (id),
product_warehouse INTEGER REFERENCES product_warehouse (id), PRIMARY KEY ( id ));
