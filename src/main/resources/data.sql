-- Insert test users
INSERT INTO users (id, username, password, subtitle, avatar_url, friend, banned) VALUES
('user-1', 'ana.cocina', 'password123', 'Chef de cocina', 'https://avatar.url/ana', FALSE, FALSE),
('user-2', 'osman.chef', 'password124', 'Sous Chef', 'https://avatar.url/osman', FALSE, FALSE),
('user-3', 'test.user', 'password124', 'Test User', 'https://avatar.url/test', FALSE, FALSE);

-- Insert test posts
INSERT INTO posts (id, author_id, caption, likes, comments, saves, banned) VALUES
('post-1', 'user-1', 'Pasta fresca con salsaaaaa', 120, 18, 15, FALSE),
('post-2', 'user-2', 'Receta de pan casero', 85, 12, 8, FALSE),
('post-3', 'user-1', 'Desayuno saludable', 45, 5, 3, FALSE),
('post-4', 'user-2', 'Curry suave con garbanzos y leche de coco', 32, 6, 4, FALSE),
('post-5', 'user-2', 'Wraps de pollo crujiente con sriracha miel', 51, 9, 7, FALSE),
('post-6', 'user-2', 'Cheesecake sin horno con frutos rojos', 76, 11, 10, FALSE);

-- Insert test post_details
INSERT INTO post_details (id, caption, author_id, likes, comments, saves, banned) VALUES
('post-1', 'Pasta fresca con salsaaaa', 'user-1', 120, 18, 15, FALSE),
('post-2', 'Receta de pan casero', 'user-2', 85, 12, 8, FALSE),
('post-3', 'Desayuno saludable', 'user-1', 45, 5, 3, FALSE),
('post-4', 'Curry suave con garbanzos y leche de coco', 'user-2', 32, 6, 4, FALSE),
('post-5', 'Wraps de pollo crujiente con sriracha miel', 'user-2', 51, 9, 7, FALSE),
('post-6', 'Cheesecake sin horno con frutos rojos', 'user-2', 76, 11, 10, FALSE);

-- Insert test post likes (subset for demo)
INSERT INTO post_likes (post_id, user_id) VALUES
('post-1', 'user-1'),
('post-2', 'user-1'),
('post-3', 'user-2');

-- Insert test comments
INSERT INTO comments (id, post_id, author_id, text) VALUES
('comment-1', 'post-1', 'user-2', 'Tip anterior sobre la receta.'),
('comment-2', 'post-1', 'user-3', 'Se ve muy delicioso!'),
('comment-3', 'post-2', 'user-1', 'Me encanta tu estilo de panaderia.');

-- Insert test conversations
INSERT INTO conversations (id, participant_a, participant_b) VALUES
('conv-1', 'user-1', 'user-2'),
('conv-2', 'user-2', 'user-3');

-- Insert test messages
INSERT INTO messages (id, conversation_id, from_user, to_user, text) VALUES
('msg-1', 'conv-1', 'user-1', 'user-2', 'Tip anterior sobre la receta.'),
('msg-2', 'conv-1', 'user-2', 'user-1', 'Gracias, salio increible.'),
('msg-3', 'conv-2', 'user-2', 'user-3', 'Como estas?');

-- Insert test sessions
INSERT INTO sessions (token, user_id, expires_at) VALUES
('demo-token-123', 'user-1', CURRENT_TIMESTAMP),
('demo-token-456', 'user-2', CURRENT_TIMESTAMP);

-- Insert test friendships
INSERT INTO friends (id, user_id, friend_id) VALUES
('friend-1', 'user-1', 'user-2'),
('friend-2', 'user-2', 'user-3');

-- Insert test friend requests
INSERT INTO friend_requests (id, from_user_id, to_user_id, status) VALUES
('freq-1', 'user-1', 'user-3', 'PENDING'),
('freq-2', 'user-3', 'user-2', 'ACCEPTED');
