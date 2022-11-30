INSERT INTO client (id, full_name, phone_number, email)
VALUES (1, 'Farid Masjedi', '09339018022', 'blue_farid@hotmail.com');
INSERT INTO address (id, base, house_number, home_unit)
VALUES (1, 'Tehran', '1', '1');
INSERT INTO address (id, base, house_number, home_unit)
VALUES (2, 'Tehran', '1', '1');
INSERT INTO node (id, full_name, phone_number, address_id, comment, dtype)
VALUES (1, 'Farid Masjedi', '09339018022', 1, 'sample comment', 'source_node');
INSERT INTO box_order (id, client_id, source_node_id, order_type, creation_date, last_modified_date)
VALUES (1, 1, 1, 'BIKE', NOW(), NOW());
INSERT INTO node (id, full_name, phone_number, address_id, comment, price_range, box_order_id, dtype)
VALUES (2, 'Neda Masjedi', '091233456789', 2, 'sample comment dest', 'UP_TO_ONE', 1, 'destination_node');
INSERT INTO address (id, base, house_number, home_unit)
VALUES (3, 'Mashhad', '1', '1');
INSERT INTO address (id, base, house_number, home_unit)
VALUES (4, 'Mashhad', '1', '1');
INSERT INTO node (id, full_name, phone_number, address_id, comment, dtype)
VALUES (3, 'Navid Masjedi', '09339018022', 3, 'sample comment', 'source_node');
INSERT INTO box_order (id, client_id, source_node_id, order_type, creation_date, last_modified_date)
VALUES (2, 1, 2, 'CAR', NOW(), NOW());
INSERT INTO node (id, full_name, phone_number, address_id, comment, price_range, box_order_id, dtype)
VALUES (4, 'Neda Masjedi', '091233456789', 4, 'sample comment dest', 'UP_TO_ONE', 2, 'destination_node');
INSERT INTO client (id, full_name, phone_number, email)
VALUES (2, 'Neda Masjedi', '09123456789', 'neda.masjedi@hotmail.com');