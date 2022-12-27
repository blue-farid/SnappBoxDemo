INSERT INTO role_entity (id, name) VALUES (1, 'USER');
INSERT INTO role_entity (id, name) VALUES (2, 'ADMIN');
INSERT INTO client (id, full_name, phone_number, email)
VALUES (1, 'Farid Masjedi', '09339018022', 'blue_farid@hotmail.com');
INSERT INTO address (id, base, house_number, home_unit)
VALUES (1, 'Tehran', '1', '1');
INSERT INTO address (id, base, house_number, home_unit)
VALUES (2, 'Tehran', '1', '1');
INSERT INTO box_order (id, client_id, order_type, creation_date, last_modified_date, price)
VALUES (1, 1, 'BIKE', NOW(), NOW(), 40000.0);
INSERT INTO node (id, full_name, phone_number, address_id, comment, box_order_id ,dtype, x, y)
VALUES (1, 'Farid Masjedi', '09339018022', 1, 'sample comment', 1,'source_node', 1.0, 1.0);
INSERT INTO node (id, full_name, phone_number, address_id, comment, price_range, box_order_id, dtype, x , y)
VALUES (2, 'Neda Masjedi', '091233456789', 2, 'sample comment dest', 'UP_TO_ONE', 1, 'destination_node', 2.0, 2.0);
INSERT INTO address (id, base, house_number, home_unit)
VALUES (3, 'Mashhad', '1', '1');
INSERT INTO address (id, base, house_number, home_unit)
VALUES (4, 'Mashhad', '1', '1');
INSERT INTO box_order (id, client_id, order_type, creation_date, last_modified_date, price)
VALUES (2, 1, 'CAR', NOW(), NOW(), 40000.0);
INSERT INTO node (id, full_name, phone_number, address_id, comment, box_order_id ,dtype, x, y)
VALUES (3, 'Navid Masjedi', '09339018022', 3, 'sample comment', 2,'source_node', 3.0, 3.0);
INSERT INTO node (id, full_name, phone_number, address_id, comment, price_range, box_order_id, dtype, x , y)
VALUES (4, 'Neda Masjedi', '091233456789', 4, 'sample comment dest', 'UP_TO_ONE', 2, 'destination_node' , 4.0, 4.0);
INSERT INTO client (id, full_name, phone_number, email)
VALUES (2, 'Neda Masjedi', '09123456789', 'neda.masjedi@gmail.com');
INSERT INTO CLIENT_ROLES VALUES (1,1);
INSERT INTO CLIENT_ROLES VALUES (1,2);
INSERT INTO CLIENT_ROLES VALUES (2,1);