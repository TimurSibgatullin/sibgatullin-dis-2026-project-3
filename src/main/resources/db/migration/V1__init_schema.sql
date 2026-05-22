CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE authors (
                         id BIGSERIAL PRIMARY KEY,
                         nickname VARCHAR(100) NOT NULL UNIQUE,
                         bio TEXT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_accounts (
                               id BIGSERIAL PRIMARY KEY,
                               login VARCHAR(50) NOT NULL UNIQUE,
                               password_hash VARCHAR(255) NOT NULL,
                               role VARCHAR(20) NOT NULL CHECK (role IN ('ROLE_READER', 'ROLE_AUTHOR', 'ROLE_ADMIN')),
                               author_id BIGINT UNIQUE REFERENCES authors(id) ON DELETE SET NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE genres (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(50) NOT NULL UNIQUE,
                        description TEXT
);

CREATE TABLE books (
                       id BIGSERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       file_path VARCHAR(255) NOT NULL,
                       views BIGINT DEFAULT 0,
                       author_id BIGINT NOT NULL REFERENCES authors(id) ON DELETE CASCADE,
                       embedding_vector vector(768),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE book_genres (
                             book_id BIGINT REFERENCES books(id) ON DELETE CASCADE,
                             genre_id BIGINT REFERENCES genres(id) ON DELETE CASCADE,
                             PRIMARY KEY (book_id, genre_id)
);

CREATE TABLE comments (
                          id BIGSERIAL PRIMARY KEY,
                          book_id BIGINT NOT NULL REFERENCES books(id) ON DELETE CASCADE,
                          user_id BIGINT NOT NULL REFERENCES user_accounts(id) ON DELETE CASCADE,
                          text TEXT NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_book_favorites (
                                     id BIGSERIAL PRIMARY KEY,
                                     user_id BIGINT NOT NULL REFERENCES user_accounts(id) ON DELETE CASCADE,
                                     book_id BIGINT NOT NULL REFERENCES books(id) ON DELETE CASCADE,
                                     added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     UNIQUE (user_id, book_id)
);

CREATE INDEX idx_books_author ON books(author_id);
CREATE INDEX idx_comments_book ON comments(book_id);
CREATE INDEX idx_comments_user ON comments(user_id);

CREATE INDEX idx_books_embedding ON books USING hnsw (embedding_vector vector_cosine_ops);