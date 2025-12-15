
INSERT INTO users (id, username, password, subtitle, avatar_url, url, admin, friend, banned) VALUES
('user-1', 'ana.cocina', 'password124', 'Chef de cocina', '/api/assets/avatars/avatar-ana-cocina.jpg', 'https://example.com/ana', FALSE, FALSE, FALSE),
('user-2', 'osman.chef', 'password124', 'Sous Chef', '/api/assets/avatars/avatar-osman-chef.jpg', 'https://example.com/osman', TRUE, FALSE, FALSE),
('user-3', 'pabloCo02', 'password124', 'Amante de los bollitossss', '/api/assets/avatars/avatar-pabloco02.jpg', 'https://example.com/pablo', FALSE, FALSE, FALSE),
('user-4', 'kaleBurguers', 'password124', 'CHIPS', '/api/assets/avatars/avatar-kaleburguers.jpg', 'https://example.com/kale', FALSE, FALSE, FALSE),
('user-5', 'carlitosss', 'password124', 'Keep rocking', '/api/assets/avatars/avatar-carlitosss.jpg', 'https://example.com/carlitos', FALSE, FALSE, FALSE),
('user-6', 'frank33', 'password124', 'I like beer', '/api/assets/avatars/avatar-frank33.jpg', 'https://example.com/frank', FALSE, FALSE, FALSE),
('user-7', 'laura.eats', 'password124', 'Food Blogger', '/api/assets/avatars/avatar-ana-cocina.jpg', 'https://example.com/laura', FALSE, FALSE, FALSE),
('user-8', 'dev_juan', 'password124', 'Tech & Tacos', '/api/assets/avatars/avatar-carlitosss.jpg', 'https://example.com/juan', FALSE, FALSE, FALSE),
('user-9', 'sofi_foodie', 'password124', 'Sweet Tooth', '/api/assets/avatars/avatar-kaleburguers.jpg', 'https://example.com/sofi', FALSE, FALSE, FALSE),
('user-10', 'chef_mario', 'password124', 'Pizza Lover', '/api/assets/avatars/avatar-osman-chef.jpg', 'https://example.com/mario', FALSE, FALSE, FALSE);



INSERT INTO posts (id, author_id, title, description, image_url, likes, comments, saves, banned) VALUES
('post-1', 'user-1', '🍝 Pasta Fresca al Pomodoro: Sabor de Italia', 'La auténtica pasta fresca italiana, hecha a mano con harina de sémola y huevos de corral. Servida con una salsa de tomate San Marzano cocinada a fuego lento.', '/api/assets/posts/post-1-pasta.jpg', 120, 18, 15, FALSE),
('post-2', 'user-1', '🍞 Pan de Masa Madre Perfecto', 'Nada supera el olor a pan recién horneado. Esta receta de masa madre lleva 48 horas de fermentación para lograr esa corteza crujiente y miga aireada.', '/api/assets/posts/post-2-pan.jpg', 85, 12, 8, FALSE),
('post-3', 'user-2', '🥣 Bowl Energético de Acai y Frutas', 'Empieza el día con energía: bowl de acai con granola casera, frutas del bosque frescas y un toque de miel orgánica.', '/api/assets/posts/post-3-desayuno.jpg', 45, 5, 3, FALSE),
('post-4', 'user-2', '🍛 Curry Tailandés Amarillo (Vegano)', 'Un viaje a Tailandia en cada bocado. Curry amarillo con leche de coco, garbanzos tiernos y especias aromáticas.', '/api/assets/posts/post-4-curry.jpg', 32, 6, 4, FALSE),
('post-5', 'user-2', '🌯 Wraps Crunchy de Pollo y Miel', 'Almuerzo rápido pero gourmet: wraps de pollo rebozado en panko con una salsa especial de sriracha y miel.', '/api/assets/posts/post-5-wraps.jpg', 51, 9, 7, FALSE),
('post-6', 'user-2', '🍰 Cheesecake de Frutos Rojos (Sin Horno)', 'El postre perfecto para el verano. Base de galleta, crema de queso suave y una cobertura generosa de frutos rojos frescos.', '/api/assets/posts/post-6-cheesecake.jpg', 76, 11, 10, FALSE),
('post-7', 'user-4', '🌮 Tacos al Pastor Veganos (Soja)', 'Tacos al pastor veganos hechos con soja texturizada, piña asada y cilantro fresco. ¡No notarás la diferencia!', '/api/assets/posts/post-7-tacos.jpg', 63, 4, 2, FALSE),
('post-8', 'user-5', '🍩 Donas Glaseadas Rellenas', 'Capricho de fin de semana: donas esponjosas rellenas de crema pastelera de vainilla y glaseado de chocolate.', '/api/assets/posts/post-8-donas.jpg', 41, 7, 5, FALSE),
('post-9', 'user-5', '🍺 Stout Casera: "La Noche Oscura"', 'Desvelando el secreto de mi Stout casera. Notas de chocolate, café y un toque de vainilla. Fermentada durante 3 semanas.', '/api/assets/posts/post-9-cerveza.jpg', 28, 1, 0, FALSE);

INSERT INTO post_details (id, title, description, caption, author_id, image_url, likes, comments, saves, banned) VALUES
('post-2', '🍞 Pan de Masa Madre Perfecto', 'Nada supera el olor a pan recién horneado. Esta receta de masa madre lleva 48 horas de fermentación para lograr esa corteza crujiente y miga aireada.', 'El arte de la paciencia hecho pan.

**Ingredientes:**
- 500g harina de fuerza
- 350g agua (70% hidratación)
- 100g masa madre activa
- 10g sal

**Instrucciones:**
1. Autólisis: Mezcla harina y agua, deja reposar 1h.
2. Añade la masa madre y la sal. Realiza pliegues cada 30 min (4 veces).
3. Fermentación en bloque: deja crecer 4-5h a temperatura ambiente.
4. Formado: da forma de hogaza y pásalo al banetón. Fermenta en nevera 12h.
5. Hornea a 250°C con vapor 20 min, luego baja a 220°C sin vapor por 20 min más.', 'user-2', '/api/assets/posts/post-2-pan.jpg', 85, 12, 8, FALSE);

INSERT INTO post_details (id, title, description, caption, author_id, image_url, likes, comments, saves, banned) VALUES
('post-3', '🥣 Bowl Energético de Acai y Frutas', 'Empieza el día con energía: bowl de acai con granola casera, frutas del bosque frescas y un toque de miel orgánica.', 'Empieza tu mañana con un boost de vitaminas.

**Ingredientes:**
- 100g pulpa de acai congelada
- 1 plátano congelado
- 50ml leche de almendras o avena
- Toppings: Granola, fresas, arándanos, coco rallado

**Instrucciones:**
1. Coloca el acai, el plátano y la leche en una batidora potente.
2. Tritura hasta conseguir una textura cremosa similar a un helado.
3. Sirve inmediatamente en un bowl.
4. Decora generosamente con la granola crujiente y las frutas frescas. ¡Disfruta frío!', 'user-1', '/api/assets/posts/post-3-desayuno.jpg', 45, 5, 3, FALSE);

INSERT INTO post_details (id, title, description, caption, author_id, image_url, likes, comments, saves, banned) VALUES
('post-4', '🍛 Curry Tailandés Amarillo (Vegano)', 'Un viaje a Tailandia en cada bocado. Curry amarillo con leche de coco, garbanzos tiernos y especias aromáticas.', 'Un viaje de sabores exóticos y reconfortantes.

**Ingredientes:**
- 400ml leche de coco (full fat)
- 2 cdas pasta de curry amarillo
- 1 bote de garbanzos cocidos
- Verduras: Espinacas, pimiento rojo, zanahoria
- Arroz jazmín para acompañar

**Instrucciones:**
1. Calienta la parte sólida de la leche de coco en una sartén hasta que se separe el aceite.
2. Añade la pasta de curry y fríe hasta que suelte aroma.
3. Incorpora el resto de la leche, los garbanzos y las verduras duras. Cocina 15 min.
4. Añade las espinacas al final.
5. Sirve caliente sobre una cama de arroz jazmín.', 'user-2', '/api/assets/posts/post-4-curry.jpg', 32, 6, 4, FALSE);

INSERT INTO post_details (id, title, description, caption, author_id, image_url, likes, comments, saves, banned) VALUES
('post-5', '🌯 Wraps Crunchy de Pollo y Miel', 'Almuerzo rápido pero gourmet: wraps de pollo rebozado en panko con una salsa especial de sriracha y miel.', 'Mejor que cualquier fast food y hecho en casa.

**Ingredientes:**
- Tortillas de trigo grandes
- Pechuga de pollo en tiras
- Empanado: Huevo, harina y Panko
- Salsa: Sriracha + Miel + Mayonesa
- Lechuga fresca

**Instrucciones:**
1. Pasa las tiras de pollo por harina, huevo y finalmente panko.
2. Fríe en aceite abundante hasta que estén doradas y crujientes.
3. Prepara la salsa mezclando los ingredientes a gusto.
4. Monta el wrap: lechuga, pollo crujiente y mucha salsa.
5. Cierra y tuesta ligeramente en sartén.', 'user-2', '/api/assets/posts/post-5-wraps.jpg', 51, 9, 7, FALSE);

INSERT INTO post_details (id, title, description, caption, author_id, image_url, likes, comments, saves, banned) VALUES
('post-6', '🍰 Cheesecake de Frutos Rojos (Sin Horno)', 'El postre perfecto para el verano. Base de galleta, crema de queso suave y una cobertura generosa de frutos rojos frescos.', 'Postre elegante sin complicaciones.

**Ingredientes:**
- 200g galletas tipo María
- 100g mantequilla derretida
- 500g queso crema tipo Philadelphia
- 200ml nata para montar
- Mermelada de frutos rojos de calidad

**Instrucciones:**
1. **Base:** Tritura las galletas y mezcla con la mantequilla. Presiona en el fondo del molde y enfría.
2. **Relleno:** Bate el queso con azúcar glass. Monta la nata y mezcla con movimientos envolventes.
3. Vierte sobre la base y alisa la superficie.
4. Refrigera mínimo 4h (mejor toda la noche).
5. Antes de servir, cubre con la mermelada y frutas frescas.', 'user-2', '/api/assets/posts/post-6-cheesecake.jpg', 76, 11, 10, FALSE);

INSERT INTO post_details (id, title, description, caption, author_id, image_url, likes, comments, saves, banned) VALUES
('post-7', '🌮 Tacos al Pastor Veganos (Soja)', 'Tacos al pastor veganos hechos con soja texturizada, piña asada y cilantro fresco. ¡No notarás la diferencia!', '¡No creerás que no es carne!

**Ingredientes:**
- 200g soja texturizada gruesa
- Adobo: Pasta de achiote, vinagre, zumo de piña, especias varias
- Tortillas de maíz
- Cilantro, cebolla picada y piña asada

**Instrucciones:**
1. Hidrata la soja en agua caliente o caldo vegetal. Escurre muy bien.
2. Mezcla los ingredientes del adobo y marina la soja durante 1h.
3. Saltea la soja marinada a fuego fuerte hasta que dore.
4. Sirve sobre tortillas calientes con trozos de piña, cilantro, cebolla y un chorrito de lima.', 'user-4', '/api/assets/posts/post-7-tacos.jpg', 63, 4, 2, FALSE);

INSERT INTO post_details (id, title, description, caption, author_id, image_url, likes, comments, saves, banned) VALUES
('post-8', '🍩 Donas Glaseadas Rellenas', 'Capricho de fin de semana: donas esponjosas rellenas de crema pastelera de vainilla y glaseado de chocolate.', 'El capricho definitivo para el fin de semana.

**Ingredientes:**
- 500g harina de fuerza
- 10g levadura seca de panadero
- 250ml leche tibia
- 1 huevo
- 50g azúcar
- Relleno: Crema pastelera

**Instrucciones:**
1. Amasa todos los ingredientes hasta obtener una masa elástica y lisa. Fermenta 1.5h.
2. Estira la masa, corta círculos y deja fermentar otros 30 min.
3. Fríe en aceite a 170°C, dando la vuelta para dorar ambos lados.
4. Deja enfriar, haz un agujero y rellena con manga pastelera.
5. Baña la parte superior en glaseado de chocolate o azúcar.', 'user-5', '/api/assets/posts/post-8-donas.jpg', 41, 7, 5, FALSE);

INSERT INTO post_details (id, title, description, caption, author_id, image_url, likes, comments, saves, banned) VALUES
('post-9', '🍺 Stout Casera: "La Noche Oscura"', 'Desvelando el secreto de mi Stout casera. Notas de chocolate, café y un toque de vainilla. Fermentada durante 3 semanas.', 'Mi orgullo de homebrewer. Una cerveza con cuerpo y alma.

**Perfil:**
- Estilo: Oatmeal Stout
- ABV: 6.5%
- Notas: Chocolate amargo, café tostado, vainilla

**Proceso:**
1. **Macerado:** Simple a 67°C durante 60 minutos para obtener cuerpo medio.
2. **Hervido:** 60 minutos. Adición de lúpulo Fuggles al inicio (amargor) y al final (aroma).
3. **Fermentación:** Levadura inglesa (S-04) a 19°C controlados durante 2 semanas.
4. **Maduración:** Embotellado con priming de azúcar. Maduración mínima de 3 semanas antes de consumir.
¡Salud!', 'user-6', '/api/assets/posts/post-9-cerveza.jpg', 28, 1, 0, FALSE);

INSERT INTO post_images (id, post_id, url, position) VALUES
('img-1', 'post-1', '/api/assets/posts/post-1-pasta.jpg', 0),
('img-2', 'post-2', '/api/assets/posts/post-2-pan.jpg', 0),
('img-3', 'post-3', '/api/assets/posts/post-3-desayuno.jpg', 0),
('img-4', 'post-4', '/api/assets/posts/post-4-curry.jpg', 0),
('img-5', 'post-5', '/api/assets/posts/post-5-wraps.jpg', 0),
('img-6', 'post-6', '/api/assets/posts/post-6-cheesecake.jpg', 0),
('img-7', 'post-7', '/api/assets/posts/post-7-tacos.jpg', 0),
('img-8', 'post-8', '/api/assets/posts/post-8-donas.jpg', 0),
('img-9', 'post-9', '/api/assets/posts/post-9-cerveza.jpg', 0),
('img-10', 'post-9', '/api/assets/posts/post-9-cerveza-2.jpg', 1),
('img-11', 'post-9', '/api/assets/posts/post-9-cerveza-3.jpg', 2);

INSERT INTO post_likes (post_id, user_id) VALUES
('post-1', 'user-1'),
('post-2', 'user-1'),
('post-3', 'user-2'),
('post-7', 'user-5'),
('post-8', 'user-4');

INSERT INTO comments (id, post_id, author_id, text) VALUES
('comment-1', 'post-1', 'user-2', '¡Qué pinta tiene esa salsa! ¿Cuánto tiempo la dejaste reducir para que espesara así?'),
('comment-2', 'post-1', 'user-3', 'Mamma mia! Me recuerda a la que hacía mi nonna en Napoles. Auténtica delicia.'),
('comment-101', 'post-1', 'user-4', 'Se ve increíble, Ana. ¿Usaste tomate natural o de lata?'),
('comment-102', 'post-1', 'user-5', '¡Guárdame un plato! Llego en 10 minutos.'),
('comment-103', 'post-1', 'user-6', 'Nada mejor que pasta fresca y una cerveza bien fría.'),
('comment-104', 'post-1', 'user-7', 'La foto es espectacular. ¿Qué cámara usas?'),
('comment-105', 'post-1', 'user-8', '¿Tienes la receta de la masa? Siempre me queda dura.'),
('comment-106', 'post-1', 'user-9', '¡Qué hambre me ha entrado de repente!'),
('comment-107', 'post-1', 'user-10', 'Doy fe de que estaba buenísimo. ¡Eres una crack!'),
('comment-108', 'post-1', 'user-2', 'El toque de albahaca fresca es clave.'),
('comment-109', 'post-1', 'user-3', 'Este finde intento hacerlo. Deséame suerte.'),
('comment-110', 'post-1', 'user-5', 'Brutal. Simplemente brutal.'),
('comment-111', 'post-1', 'user-7', '¿Para cuántas personas es esa ración?'),
('comment-112', 'post-1', 'user-1', '¡Gracias a todos! La receta de la masa la subiré pronto en otro post.'),
('comment-113', 'post-1', 'user-4', 'Ansioso por ver esa receta.'),
('comment-114', 'post-1', 'user-6', 'Maridaje perfecto con una Pale Ale.'),
('comment-115', 'post-1', 'user-8', '¿Se puede congelar la pasta fresca?'),
('comment-116', 'post-1', 'user-1', 'Sí Juan, mejor congelarla antes de cocer.'),

('comment-3', 'post-2', 'user-1', 'Esa miga se ve espectacular. ¿Usaste harina de fuerza o mezcla?'),
('comment-201', 'post-2', 'user-3', 'El rey del pan ataca de nuevo. ¡Menuda corteza!'),
('comment-202', 'post-2', 'user-4', '¿Cuánto tiempo de autolisis le diste?'),
('comment-203', 'post-2', 'user-5', 'Me cruje solo de verlo.'),
('comment-204', 'post-2', 'user-6', 'Ideal para unas tostadas con mi Stout.'),
('comment-205', 'post-2', 'user-7', '¡Qué envidia de alveolos! Los míos salen compactos.'),
('comment-206', 'post-2', 'user-8', '¿Horno de leña o eléctrico?'),
('comment-207', 'post-2', 'user-9', 'Me comería la hogaza entera yo sola.'),
('comment-208', 'post-2', 'user-10', 'Maestro panadero. Enséñanos tus secretos.'),
('comment-209', 'post-2', 'user-2', 'Gracias! Sí, harina de fuerza W300 y 1h de autolisis.'),
('comment-210', 'post-2', 'user-3', 'Apuntado queda el dato de la harina.'),
('comment-211', 'post-2', 'user-7', 'Intentaré copiarte la técnica este domingo.'),

('comment-301', 'post-3', 'user-3', '¡Wow! Qué colores más vivos.'),
('comment-302', 'post-3', 'user-4', '¿Dónde compras la pulpa de acai? No la encuentro.'),
('comment-303', 'post-3', 'user-5', 'Desayuno de campeones.'),
('comment-304', 'post-3', 'user-7', 'Se ve súper refrescante para después de entrenar.'),
('comment-305', 'post-3', 'user-1', 'Queda genial también con un poco de mantequilla de cacahuete por encima.'),

('comment-401', 'post-4', 'user-1', 'Amo el curry amarillo. ¿Pica mucho?'),
('comment-402', 'post-4', 'user-3', 'La leche de coco le da una textura increíble seguro.'),
('comment-403', 'post-4', 'user-5', '¡Me apunto la receta para la cena de hoy!'),
('comment-404', 'post-4', 'user-6', '¿Marida bien con una IPA?'),
('comment-405', 'post-4', 'user-7', 'Soy fan de los garbanzos en curry. ¡Qué buena idea!'),
('comment-406', 'post-4', 'user-2', 'No pica demasiado, es bastante suave. ¡Pruébalo!'),

('comment-501', 'post-5', 'user-1', 'Uff, ese pollo se ve súper crujiente.'),
('comment-502', 'post-5', 'user-3', 'La combinación sriracha-miel es adictiva.'),
('comment-503', 'post-5', 'user-4', '¿El wrap es integral o normal?'),
('comment-504', 'post-5', 'user-6', 'Perfecto para ver el partido.'),
('comment-505', 'post-5', 'user-8', '¿Se puede hacer en airfryer para que sea más light?'),
('comment-506', 'post-5', 'user-9', '¡Que aproveche!'),
('comment-507', 'post-5', 'user-10', 'Guardadme uno, que voy.'),
('comment-508', 'post-5', 'user-2', 'Sí Juan, en airfryer a 200ºC unos 12-15 min queda genial.'),
('comment-509', 'post-5', 'user-7', 'Gracias por el tip de la airfryer!'),

('comment-601', 'post-6', 'user-1', '¡Mi postre favorito! Y sin horno mejor.'),
('comment-602', 'post-6', 'user-3', 'Qué cremosidad se le nota.'),
('comment-603', 'post-6', 'user-4', '¿Gelatina o cuajada para que monte?'),
('comment-604', 'post-6', 'user-5', 'Espectacular presentación.'),
('comment-605', 'post-6', 'user-7', 'Los frutos rojos le dan el toque perfecto.'),
('comment-606', 'post-6', 'user-8', '¿Aguanta bien fuera de la nevera?'),
('comment-607', 'post-6', 'user-9', 'Quiero un trozo AHORA.'),
('comment-608', 'post-6', 'user-10', 'La probaré este finde.'),
('comment-609', 'post-6', 'user-2', 'Solo queso y nata bien montada, sin gelatina si se refrigera bien.'),
('comment-610', 'post-6', 'user-6', 'Postre de 10.'),
('comment-611', 'post-6', 'user-3', 'Me encanta que no lleve gelatina, textura más natural.'),

('comment-4', 'post-7', 'user-5', '¿Es soja texturizada fina o gruesa? Jamás diría que no es carne al pastor... ¡Increíble!'),
('comment-701', 'post-7', 'user-1', 'Tengo que probar esto. ¿Dónde compras el achiote?'),
('comment-702', 'post-7', 'user-2', 'Brutal. La piña asada es el toque maestro.'),
('comment-703', 'post-7', 'user-8', '¡Viva México y lo vegano!'),

('comment-5', 'post-8', 'user-4', 'Tienen una pinta brutal. ¿Crees que quedarían bien horneados en vez de fritos? Intento cuidarme un poco jaja'),
('comment-801', 'post-8', 'user-1', '¡Madre mía! Qué esponjosidad.'),
('comment-802', 'post-8', 'user-2', 'El relleno se sale, qué rico.'),
('comment-803', 'post-8', 'user-3', 'Pecado capital.'),
('comment-804', 'post-8', 'user-6', 'Cafe y donas, planazo.'),
('comment-805', 'post-8', 'user-7', '¿La masa lleva cardamomo?'),
('comment-806', 'post-8', 'user-5', 'Horneadas salen más tipo brioche, fritas son más auténticas. Pero prueba!'),

('comment-6', 'post-9', 'user-6', '¡Salud! ¿Qué lúpulos usaste para el aroma? ¿Fuggles o algo más cítrico?');

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
