-- Users with real avatar URLs
INSERT INTO users (id, username, password, subtitle, avatar_url, url, admin, friend, banned) VALUES
('user-1', 'ana.cocina', 'password124', 'Chef de cocina', '/api/assets/avatars/avatar-ana-cocina.jpg', 'https://example.com/ana', FALSE, FALSE, FALSE),
('user-2', 'osman.chef', 'password124', 'Sous Chef', '/api/assets/avatars/avatar-osman-chef.jpg', 'https://example.com/osman', TRUE, FALSE, FALSE),
('user-3', 'pabloCo02', 'password124', 'Amante de los bollitossss', '/api/assets/avatars/avatar-pabloco02.jpg', 'https://example.com/pablo', FALSE, FALSE, FALSE),
('user-4', 'kaleBurguers', 'password124', 'CHIPS', '/api/assets/avatars/avatar-kaleburguers.jpg', 'https://example.com/kale', FALSE, FALSE, FALSE),
('user-5', 'carlitosss', 'password124', 'Keep rocking', '/api/assets/avatars/avatar-carlitosss.jpg', 'https://example.com/carlitos', FALSE, FALSE, FALSE),
('user-6', 'frank33', 'password124', 'I like beer', '/api/assets/avatars/avatar-frank33.jpg', 'https://example.com/frank', FALSE, FALSE, FALSE);

-- Posts with illustrative photos
INSERT INTO posts (id, author_id, title, description, caption, image_url, likes, comments, saves, banned) VALUES
('post-1', 'user-1', 'Pasta fresca', 'La auténtica pasta fresca italiana, hecha a mano con harina de sémola y huevos de corral. Servida con una salsa de tomate San Marzano cocinada a fuego lento.', 'Pasta fresca con salsaaaaa', '/api/assets/posts/post-1-pasta.jpg', 120, 18, 15, FALSE),
('post-2', 'user-2', 'Receta de pan casero', 'Nada supera el olor a pan recién horneado. Esta receta de masa madre lleva 48 horas de fermentación para lograr esa corteza crujiente y miga aireada.', 'Receta de pan casero', '/api/assets/posts/post-2-pan.jpg', 85, 12, 8, FALSE),
('post-3', 'user-1', 'Desayuno saludable', 'Empieza el día con energía: bowl de acai con granola casera, frutas del bosque frescas y un toque de miel orgánica.', 'Desayuno saludable', '/api/assets/posts/post-3-desayuno.jpg', 45, 5, 3, FALSE),
('post-4', 'user-2', 'Curry suave', 'Un viaje a Tailandia en cada bocado. Curry amarillo con leche de coco, garbanzos tiernos y especias aromáticas.', 'Curry suave con garbanzos y leche de coco', '/api/assets/posts/post-4-curry.jpg', 32, 6, 4, FALSE),
('post-5', 'user-2', 'Wraps crujientes', 'Almuerzo rápido pero gourmet: wraps de pollo rebozado en panko con una salsa especial de sriracha y miel.', 'Wraps de pollo crujiente con sriracha miel', '/api/assets/posts/post-5-wraps.jpg', 51, 9, 7, FALSE),
('post-6', 'user-2', 'Cheesecake sin horno', 'El postre perfecto para el verano. Base de galleta, crema de queso suave y una cobertura generosa de frutos rojos frescos.', 'Cheesecake sin horno con frutos rojos', '/api/assets/posts/post-6-cheesecake.jpg', 76, 11, 10, FALSE),
('post-7', 'user-4', 'Tacos veganos', 'Tacos al pastor veganos hechos con soja texturizada, piña asada y cilantro fresco. ¡No notarás la diferencia!', 'Tacos veganos crujientes', '/api/assets/posts/post-7-tacos.jpg', 63, 4, 2, FALSE),
('post-8', 'user-5', 'Donas rellenas', 'Capricho de fin de semana: donas esponjosas rellenas de crema pastelera de vainilla y glaseado de chocolate.', 'Donas rellenas con glaseado', '/api/assets/posts/post-8-donas.jpg', 41, 7, 5, FALSE),
('post-9', 'user-6', 'Cerveza artesanal', 'Desvelando el secreto de mi Stout casera. Notas de chocolate, café y un toque de vainilla. Fermentada durante 3 semanas.', 'Receta secreta de cerveza artesanal', '/api/assets/posts/post-9-cerveza.jpg', 28, 1, 0, FALSE);

INSERT INTO post_details (id, title, description, caption, author_id, image_url, likes, comments, saves, banned) VALUES
('post-1', 'Pasta fresca', 'La auténtica pasta fresca italiana, hecha a mano con harina de sémola y huevos de corral. Servida con una salsa de tomate San Marzano cocinada a fuego lento.', 'Pasta fresca con salsaaaa', 'user-1', '/api/assets/posts/post-1-pasta.jpg', 120, 18, 15, FALSE),
('post-2', 'Receta de pan casero', 'Nada supera el olor a pan recién horneado. Esta receta de masa madre lleva 48 horas de fermentación para lograr esa corteza crujiente y miga aireada.', 'Receta de pan casero', 'user-2', '/api/assets/posts/post-2-pan.jpg', 85, 12, 8, FALSE),
('post-3', 'Desayuno saludable', 'Empieza el día con energía: bowl de acai con granola casera, frutas del bosque frescas y un toque de miel orgánica.', 'Desayuno saludable', 'user-1', '/api/assets/posts/post-3-desayuno.jpg', 45, 5, 3, FALSE),
('post-4', 'Curry suave', 'Un viaje a Tailandia en cada bocado. Curry amarillo con leche de coco, garbanzos tiernos y especias aromáticas.', 'Curry suave con garbanzos y leche de coco', 'user-2', '/api/assets/posts/post-4-curry.jpg', 32, 6, 4, FALSE),
('post-5', 'Wraps crujientes', 'Almuerzo rápido pero gourmet: wraps de pollo rebozado en panko con una salsa especial de sriracha y miel.', 'Wraps de pollo crujiente con sriracha miel', 'user-2', '/api/assets/posts/post-5-wraps.jpg', 51, 9, 7, FALSE),
('post-6', 'Cheesecake sin horno', 'El postre perfecto para el verano. Base de galleta, crema de queso suave y una cobertura generosa de frutos rojos frescos.', 'Cheesecake sin horno con frutos rojos', 'user-2', '/api/assets/posts/post-6-cheesecake.jpg', 76, 11, 10, FALSE),
('post-7', 'Tacos veganos', 'Tacos al pastor veganos hechos con soja texturizada, piña asada y cilantro fresco. ¡No notarás la diferencia!', 'Tacos veganos crujientes', 'user-4', '/api/assets/posts/post-7-tacos.jpg', 63, 4, 2, FALSE),
('post-8', 'Donas rellenas', 'Capricho de fin de semana: donas esponjosas rellenas de crema pastelera de vainilla y glaseado de chocolate.', 'Donas rellenas con glaseado', 'user-5', '/api/assets/posts/post-8-donas.jpg', 41, 7, 5, FALSE),
('post-9', 'Cerveza artesanal', 'Desvelando el secreto de mi Stout casera. Notas de chocolate, café y un toque de vainilla. Fermentada durante 3 semanas.', 'Receta secreta de cerveza artesanal', 'user-6', '/api/assets/posts/post-9-cerveza.jpg', 28, 1, 0, FALSE);

INSERT INTO post_images (id, post_id, url, position) VALUES
('img-1', 'post-1', '/api/assets/posts/post-1-pasta.jpg', 0),
('img-2', 'post-2', '/api/assets/posts/post-2-pan.jpg', 0),
('img-3', 'post-3', '/api/assets/posts/post-3-desayuno.jpg', 0),
('img-4', 'post-4', '/api/assets/posts/post-4-curry.jpg', 0),
('img-5', 'post-5', '/api/assets/posts/post-5-wraps.jpg', 0),
('img-6', 'post-6', '/api/assets/posts/post-6-cheesecake.jpg', 0),
('img-7', 'post-7', '/api/assets/posts/post-7-tacos.jpg', 0),
('img-8', 'post-8', '/api/assets/posts/post-8-donas.jpg', 0),
('img-9', 'post-9', '/api/assets/posts/post-9-cerveza.jpg', 0);

INSERT INTO post_likes (post_id, user_id) VALUES
('post-1', 'user-1'),
('post-2', 'user-1'),
('post-3', 'user-2'),
('post-7', 'user-5'),
('post-8', 'user-4');

INSERT INTO comments (id, post_id, author_id, text) VALUES
('comment-1', 'post-1', 'user-2', 'Tip anterior sobre la receta.'),
('comment-2', 'post-1', 'user-3', 'Se ve muy delicioso!'),
('comment-3', 'post-2', 'user-1', 'Me encanta tu estilo de panaderia.'),
('comment-4', 'post-7', 'user-5', 'Quiero esas recetas ya!'),
('comment-5', 'post-8', 'user-4', 'Necesito probar esto.'),
('comment-6', 'post-9', 'user-6', 'Salud por esta mezcla!');

INSERT INTO conversations (id, participant_a, participant_b) VALUES
('conv-1', 'user-1', 'user-2'),
('conv-2', 'user-2', 'user-3'),
('conv-3', 'user-2', 'user-4'),
('conv-4', 'user-1', 'user-5'),
('conv-5', 'user-5', 'user-6');

INSERT INTO messages (id, conversation_id, from_user, to_user, text) VALUES
('msg-1', 'conv-1', 'user-1', 'user-2', 'Tip anterior sobre la receta.'),
('msg-2', 'conv-1', 'user-2', 'user-1', 'Gracias, salio increible.'),
('msg-3', 'conv-2', 'user-2', 'user-3', 'Como estas?'),
('msg-4', 'conv-3', 'user-4', 'user-2', 'Tengo nuevas ideas veganas.'),
('msg-5', 'conv-4', 'user-5', 'user-1', 'Con ganas de colaborar.'),
('msg-6', 'conv-5', 'user-6', 'user-5', 'Brindemos por nuevas recetas!');

INSERT INTO sessions (token, user_id, expires_at) VALUES
('demo-token-123', 'user-1', CURRENT_TIMESTAMP),
('demo-token-456', 'user-2', CURRENT_TIMESTAMP);

INSERT INTO friends (id, user_id, friend_id) VALUES
('friend-1', 'user-1', 'user-2'),
('friend-2', 'user-2', 'user-3'),
('friend-3', 'user-2', 'user-4'),
('friend-4', 'user-5', 'user-1'),
('friend-5', 'user-5', 'user-6');

INSERT INTO friend_requests (id, from_user_id, to_user_id, status) VALUES
('freq-1', 'user-1', 'user-3', 'PENDING'),
('freq-2', 'user-3', 'user-2', 'ACCEPTED'),
('freq-3', 'user-4', 'user-2', 'PENDING'),
('freq-4', 'user-6', 'user-5', 'PENDING');

INSERT INTO post_tags (post_id, tag) VALUES
('post-1', 'pasta'), ('post-1', 'italiana'), ('post-1', 'casero'),
('post-2', 'pan'), ('post-2', 'baking'), ('post-2', 'sourdough'),
('post-3', 'saludable'), ('post-3', 'desayuno'), ('post-3', 'frutas'),
('post-4', 'curry'), ('post-4', 'thai'), ('post-4', 'spicy'),
('post-5', 'pollo'), ('post-5', 'crispy'), ('post-5', 'sriracha'),
('post-6', 'postre'), ('post-6', 'cheesecake'), ('post-6', 'verano'),
('post-7', 'vegano'), ('post-7', 'tacos'), ('post-7', 'mexicano'),
('post-8', 'donas'), ('post-8', 'chocolate'), ('post-8', 'dulce'),
('post-9', 'cerveza'), ('post-9', 'craftbeer'), ('post-9', 'homebrewing');
