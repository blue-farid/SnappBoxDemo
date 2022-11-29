INSERT INTO client (id, full_name, phone_number, email)
VALUES (1, 'Farid Masjedi', '09339018022', 'blue_farid@hotmail.com');
INSERT INTO address (id, base, house_number, home_unit)
VALUES (1, 'Tehran', '1', '1');
INSERT INTO address (id, base, house_number, home_unit)
VALUES (2, 'Tehran', '1', '1');
INSERT INTO source_node (id, full_name, phone_number, address_id, comment)
VALUES (1, 'Farid Masjedi', '09339018022', 1, 'sample comment');
INSERT INTO box_order (id, client_id, source_node_id, order_type, creation_date, last_modified_date)
VALUES (1, 1, 1, 'BIKE', NOW(), NOW());
INSERT INTO destination_node (id, full_name, phone_number, address_id, comment, price_range, box_order_id)
VALUES (1, 'Neda Masjedi', '091233456789', 2, '"sample comment dest"', 'UP_TO_ONE', 1);
INSERT INTO address (id, base, house_number, home_unit)
VALUES (3, 'Mashhad', '1', '1');
INSERT INTO address (id, base, house_number, home_unit)
VALUES (4, 'Mashhad', '1', '1');
INSERT INTO source_node (id, full_name, phone_number, address_id, comment)
VALUES (2, 'Navid Masjedi', '09339018022', 1, 'sample comment');
INSERT INTO box_order (id, client_id, source_node_id, order_type, creation_date, last_modified_date)
VALUES (2, 1, 2, 'CAR', NOW(), NOW());
INSERT INTO destination_node (id, full_name, phone_number, address_id, comment, price_range, box_order_id)
VALUES (2, 'Neda Masjedi', '091233456789', 2, '"sample comment dest"', 'UP_TO_ONE', 2);
INSERT INTO client (id, full_name, phone_number, email)
VALUES (2, 'Neda Masjedi', '09123456789', 'neda.masjedi@hotmail.com');