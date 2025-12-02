-- Users table
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    subtitle VARCHAR(500),
    avatar_url VARCHAR(500),
    friend BOOLEAN DEFAULT FALSE,
    banned BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Posts table
CREATE TABLE IF NOT EXISTS posts (
    id VARCHAR(36) PRIMARY KEY,
    author_id VARCHAR(36) NOT NULL,
    caption TEXT NOT NULL,
    likes INT DEFAULT 0,
    comments INT DEFAULT 0,
    saves INT DEFAULT 0,
    banned BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(id)
);
CREATE INDEX IF NOT EXISTS idx_posts_author ON posts(author_id);
CREATE INDEX IF NOT EXISTS idx_posts_created ON posts(created_at);

-- Comments table
CREATE TABLE IF NOT EXISTS comments (
    id VARCHAR(36) PRIMARY KEY,
    post_id VARCHAR(36) NOT NULL,
    author_id VARCHAR(36) NOT NULL,
    text TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (author_id) REFERENCES users(id)
);
CREATE INDEX IF NOT EXISTS idx_comments_post ON comments(post_id);
CREATE INDEX IF NOT EXISTS idx_comments_author ON comments(author_id);

-- Conversations table
CREATE TABLE IF NOT EXISTS conversations (
    id VARCHAR(36) PRIMARY KEY,
    participant_a VARCHAR(36) NOT NULL,
    participant_b VARCHAR(36) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (participant_a) REFERENCES users(id),
    FOREIGN KEY (participant_b) REFERENCES users(id)
);
CREATE INDEX IF NOT EXISTS idx_conversations_participants ON conversations(participant_a, participant_b);

-- Messages table
CREATE TABLE IF NOT EXISTS messages (
    id VARCHAR(36) PRIMARY KEY,
    conversation_id VARCHAR(36) NOT NULL,
    from_user VARCHAR(36) NOT NULL,
    to_user VARCHAR(36) NOT NULL,
    text TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (conversation_id) REFERENCES conversations(id),
    FOREIGN KEY (from_user) REFERENCES users(id),
    FOREIGN KEY (to_user) REFERENCES users(id)
);
CREATE INDEX IF NOT EXISTS idx_messages_conversation ON messages(conversation_id);
CREATE INDEX IF NOT EXISTS idx_messages_from_user ON messages(from_user);

-- Sessions table
CREATE TABLE IF NOT EXISTS sessions (
    token VARCHAR(500) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE INDEX IF NOT EXISTS idx_sessions_user ON sessions(user_id);

-- Friends table
CREATE TABLE IF NOT EXISTS friends (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    friend_id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (friend_id) REFERENCES users(id)
);
CREATE INDEX IF NOT EXISTS idx_friends_user ON friends(user_id);

-- Friend Requests table
CREATE TABLE IF NOT EXISTS friend_requests (
    id VARCHAR(36) PRIMARY KEY,
    from_user_id VARCHAR(36) NOT NULL,
    to_user_id VARCHAR(36) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_user_id) REFERENCES users(id),
    FOREIGN KEY (to_user_id) REFERENCES users(id)
);
CREATE INDEX IF NOT EXISTS idx_friend_requests_from_user ON friend_requests(from_user_id);
CREATE INDEX IF NOT EXISTS idx_friend_requests_to_user ON friend_requests(to_user_id);
CREATE INDEX IF NOT EXISTS idx_friend_requests_status ON friend_requests(status);

-- Post Details (materialized view-like table for performance)
CREATE TABLE IF NOT EXISTS post_details (
    id VARCHAR(36) PRIMARY KEY,
    caption TEXT NOT NULL,
    author_id VARCHAR(36) NOT NULL,
    likes INT DEFAULT 0,
    comments INT DEFAULT 0,
    saves INT DEFAULT 0,
    banned BOOLEAN DEFAULT FALSE,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(id)
);
CREATE INDEX IF NOT EXISTS idx_post_details_author ON post_details(author_id);
