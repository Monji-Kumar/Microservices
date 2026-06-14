INSERT INTO orders(id, total_price, order_status) VALUES
(nextval('order_id_seq'),100.50, 'PENDING'),
(nextval('order_id_seq'),200.75, 'PENDING'),
(nextval('order_id_seq'),300.00, 'DELIVERED'),
(nextval('order_id_seq'),150.25, 'PENDING'),
(nextval('order_id_seq'),120.00, 'CANCELLED'),
(nextval('order_id_seq'),210.50, 'PENDING'),
(nextval('order_id_seq'),350.75, 'DELIVERED'),
(nextval('order_id_seq'),110.00, 'CONFIRMED'),
(nextval('order_id_seq'),180.20, 'PENDING'),
(nextval('order_id_seq'),250.40, 'CANCELLED');

INSERT INTO order_items(id, order_id, product_id, quantity) VALUES
(nextval('order_item_id_seq'),1, 1,2),
(nextval('order_item_id_seq'),1, 2,1),
(nextval('order_item_id_seq'),2, 3,1),
(nextval('order_item_id_seq'),2, 4,3),
(nextval('order_item_id_seq'),3, 5,1),
(nextval('order_item_id_seq'),3, 6,2),
(nextval('order_item_id_seq'),3, 7,2),
(nextval('order_item_id_seq'),4, 8,3),
(nextval('order_item_id_seq'),6, 9,2),
(nextval('order_item_id_seq'),7, 10,1),
(nextval('order_item_id_seq'),8, 11,2),
(nextval('order_item_id_seq'),9, 12,3),
(nextval('order_item_id_seq'),10, 13,2);