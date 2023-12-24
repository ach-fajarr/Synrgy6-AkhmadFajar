INSERT INTO public.roles (id,"name") VALUES
	 (1,'ROLE_BUYER'),
	 (2,'ROLE_SELLER'),
	 (3,'ROLE_ADMIN'),
	 (4,'DELETED');
INSERT INTO public.users (id,created_date,deleted_date,is_deleted,updated_date,email,first_name,last_name,"password",username,subscribed) VALUES
	 ('39b1a944-98ee-4e72-b86f-4494af19cf31','2023-12-19 18:31:28.39',NULL,false,NULL,'akhmad@gmail.com','Akhmad','Fajar','$2a$10$KxuQAUDgjujAYsjB74eXG.NAhFgqXCy.QvClG6lEQak81d89NzRhW','akhmad123',NULL),
	 ('bf26e27e-fe0f-4eeb-98b4-b7c9f05dff9c','2023-12-19 18:33:44.357',NULL,false,NULL,'firman@gmail.com','Akhmad','Fajar','$2a$10$GsAaeswB8oLech/.cb1msOXZVUFaRFi3lRTVoD74ZMevus0EAZhXC','firman123',NULL),
	 ('468db582-9967-4ef6-bc75-14fa1c104c8d','2023-12-19 18:24:51.744',NULL,false,NULL,'fa314270@gmail.com','Akhmad','Fajar','$2a$10$BQr47wFrGKVAlKOXlr8IbeWPap.NRD0aqzsv1tFx.LpweuCTPJvfe','fajar123',true);
INSERT INTO public.merchant (id,created_date,deleted_date,is_deleted,updated_date,merchant_location,merchant_name,"open",users_id) VALUES
	 ('d9ae4ca0-2286-404a-860e-39d261107fc8','2023-12-19 18:36:39.747',NULL,false,NULL,'Surabaya','Resto Sukses',true,'39b1a944-98ee-4e72-b86f-4494af19cf31'),
	 ('56857804-9be4-41a7-99bd-4cee9a5f9fed','2023-12-19 18:37:19.609',NULL,false,NULL,'Sidoarjo','Caffe Ceria',true,'39b1a944-98ee-4e72-b86f-4494af19cf31');
INSERT INTO public.orders (id,completed,destination_address,order_time,users_id) VALUES
	 ('05851771-edd5-4048-928c-63eb364e49b2',true,'gubeng','2023-12-19 23:38:44.478144','468db582-9967-4ef6-bc75-14fa1c104c8d'),
	 ('00863095-1baa-4566-ae9f-ea38eefb8c0e',true,'sby','2023-12-21 13:39:46.858095','468db582-9967-4ef6-bc75-14fa1c104c8d'),
	 ('015026c7-9591-4dc8-8ada-b1fee13dcf5e',true,'sby','2023-12-21 13:47:24.714679','468db582-9967-4ef6-bc75-14fa1c104c8d'),
	 ('9d278dd1-f8e2-4a7d-ac27-677e9772d34b',true,'sby','2023-12-22 18:24:00.062948','468db582-9967-4ef6-bc75-14fa1c104c8d');
INSERT INTO public.product (id,created_date,deleted_date,is_deleted,updated_date,price,product_name,stock,merchant_id,discount) VALUES
	 ('df1e8efa-4b09-4edb-ba26-e49949591b6d','2023-12-19 18:40:18.303',NULL,false,'2023-12-21 13:39:46.979',10000,'Matcha Small',4,'56857804-9be4-41a7-99bd-4cee9a5f9fed',10),
	 ('ee7bec11-99de-4404-9875-9f5b51d73848','2023-12-19 18:50:09.371',NULL,false,'2023-12-21 13:39:46.984',15000,'Cappuccino Latte',27,'56857804-9be4-41a7-99bd-4cee9a5f9fed',40),
	 ('a6a9ec53-2bb3-48dc-b91d-3f8eac589374','2023-12-19 18:49:23.826',NULL,false,'2023-12-21 13:47:24.771',13000,'Matcha Medium',6,'56857804-9be4-41a7-99bd-4cee9a5f9fed',30),
	 ('2559ca04-a24f-41c7-a3e8-3038aa255d85','2023-12-19 18:50:40.583',NULL,false,'2023-12-22 00:27:34.799',15000,'Americano',33,'56857804-9be4-41a7-99bd-4cee9a5f9fed',NULL),
	 ('9e6ea6d6-29e3-4350-8082-da85b0b87a18','2023-12-19 18:49:37.321',NULL,false,'2023-12-22 18:24:00.09',14000,'Matcha Jumbo',30,'56857804-9be4-41a7-99bd-4cee9a5f9fed',NULL);
INSERT INTO public.users_roles (users_id,roles_id) VALUES
	 ('468db582-9967-4ef6-bc75-14fa1c104c8d',1),
	 ('468db582-9967-4ef6-bc75-14fa1c104c8d',3),
	 ('468db582-9967-4ef6-bc75-14fa1c104c8d',2),
	 ('39b1a944-98ee-4e72-b86f-4494af19cf31',2),
	 ('bf26e27e-fe0f-4eeb-98b4-b7c9f05dff9c',1);
INSERT INTO public.order_detail (id,created_date,deleted_date,is_deleted,updated_date,quantity,total_price,order_id,product_id,info) VALUES
	 ('9d2e424e-3ff6-4474-9cd8-20c876469b99','2023-12-19 20:14:27.214',NULL,false,'2023-12-19 23:38:44.531',2,65000,'05851771-edd5-4048-928c-63eb364e49b2','a6a9ec53-2bb3-48dc-b91d-3f8eac589374',NULL),
	 ('6bd7080a-0ef6-4bf7-9d05-66d6e7b281a1','2023-12-19 22:45:32.733',NULL,false,'2023-12-19 23:38:44.532',4,40000,'05851771-edd5-4048-928c-63eb364e49b2','df1e8efa-4b09-4edb-ba26-e49949591b6d',NULL),
	 ('02e4e187-eb9e-4c1b-9abc-aababe8500bf','2023-12-19 23:52:02.16',NULL,false,'2023-12-21 13:39:46.981',2,20000,'00863095-1baa-4566-ae9f-ea38eefb8c0e','df1e8efa-4b09-4edb-ba26-e49949591b6d',NULL),
	 ('50d5dfff-adaf-49bb-abe4-d3d81467b150','2023-12-21 13:25:21.831',NULL,false,'2023-12-21 13:39:46.988',1,12000,'00863095-1baa-4566-ae9f-ea38eefb8c0e','2559ca04-a24f-41c7-a3e8-3038aa255d85',NULL),
	 ('088a3804-de4c-4048-99a0-1bfcdd57af47','2023-12-19 23:52:28.186',NULL,false,'2023-12-21 13:39:46.988',3,45000,'00863095-1baa-4566-ae9f-ea38eefb8c0e','ee7bec11-99de-4404-9875-9f5b51d73848',NULL),
	 ('3b5d8078-f374-477d-b158-23262e8cd0ce','2023-12-21 13:34:24.818',NULL,false,'2023-12-21 13:39:46.989',1,13000,'00863095-1baa-4566-ae9f-ea38eefb8c0e','a6a9ec53-2bb3-48dc-b91d-3f8eac589374',NULL),
	 ('c9e4750b-ba8b-447e-9a49-7540cfbb6362','2023-12-21 13:41:58.72',NULL,false,'2023-12-21 13:47:24.768',1,12000,'015026c7-9591-4dc8-8ada-b1fee13dcf5e','2559ca04-a24f-41c7-a3e8-3038aa255d85','Diskon produk: 20%'),
	 ('d7c610b6-85fc-4765-a777-b46f2775e52b','2023-12-21 13:44:26.675',NULL,false,'2023-12-21 13:47:24.772',1,13000,'015026c7-9591-4dc8-8ada-b1fee13dcf5e','a6a9ec53-2bb3-48dc-b91d-3f8eac589374','Diskon produk: null%'),
	 ('9668a344-0295-48a0-8ac2-7d23290d032a','2023-12-22 18:22:49.455',NULL,false,'2023-12-22 18:24:00.09',10,140000,'9d278dd1-f8e2-4a7d-ac27-677e9772d34b','9e6ea6d6-29e3-4350-8082-da85b0b87a18','Diskon produk: tidak ada');
