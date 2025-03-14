INSERT INTO pcparts_jpa (price, weight, id, name, producer, type) VALUES (100, 100, 1, 'Intel Core i5-9400F', 'Intel', 'CPU');
INSERT INTO pcparts_jpa (price, weight, id, name, producer, type) VALUES (100, 200, 2, 'Intel Core i7-9700K', 'Intel', 'CPU');
INSERT INTO pcparts_jpa (price, weight, id, name, producer, type) VALUES (100, 300, 3, 'NVIDIA GeForce RTX 2060', 'NVIDIA', 'GPU');
INSERT INTO pcparts_jpa (price, weight, id, name, producer, type) VALUES (100, 400, 4, 'NVIDIA GeForce RTX 2070', 'NVIDIA', 'GPU');
INSERT INTO pcparts_jpa (price, weight, id, name, producer, type) VALUES (100, 500, 5, 'ASUS ROG Strix Z390-E Gaming', 'ASUS', 'Motherboard');
INSERT INTO pcparts_jpa (price, weight, id, name, producer, type) VALUES (100, 600, 6, 'ASUS ROG Strix Z390-F Gaming', 'ASUS', 'Motherboard');
INSERT INTO pcparts_jpa (price, weight, id, name, producer, type) VALUES (100, 700, 7, 'Corsair Vengeance LPX 16GB', 'Corsair', 'RAM');
INSERT INTO pcparts_jpa (price, weight, id, name, producer, type) VALUES (100, 800, 8, 'Corsair Vengeance LPX 32GB', 'Corsair', 'RAM');
INSERT INTO pcparts_jpa (price, weight, id, name, producer, type) VALUES (100, 900, 9, 'Corsair RM750x', 'Corsair', 'PSU');
INSERT INTO pcparts_jpa (price, weight, id, name, producer, type) VALUES (100, 1000, 10, 'Corsair RM850x', 'Corsair', 'PSU');
INSERT INTO pcparts_jpa (price, weight, id, name, producer, type) VALUES (100, 1100, 11, 'Samsung 970 EVO Plus 500GB', 'Samsung', 'Storage');
INSERT INTO pcparts_jpa (price, weight, id, name, producer, type) VALUES (100, 1200, 12, 'Samsung 970 EVO Plus 1TB', 'Samsung', 'Storage');



INSERT INTO prebuiltpcs_jpa (price, cpu_id, gpu_id, id, motherboard_id, psu_id, ram_id, storage_id) VALUES (100, 1, 3, 1, 5, 9, 7, 11);
INSERT INTO prebuiltpcs_jpa (price, cpu_id, gpu_id, id, motherboard_id, psu_id, ram_id, storage_id) VALUES (100, 2, 4, 2, 6, 10, 8, 12);
INSERT INTO prebuiltpcs_jpa (price, cpu_id, gpu_id, id, motherboard_id, psu_id, ram_id, storage_id) VALUES (100, 1, 4, 3, 5, 9, 8, 11);

INSERT INTO promos (id, name, discount, price, pc_part_id, description) VALUES (1, 'CPU discount', 40, 60, 1, 'Christmas discount');