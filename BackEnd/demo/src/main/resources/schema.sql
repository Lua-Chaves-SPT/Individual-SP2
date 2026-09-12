CREATE TABLE IF NOT EXISTS genero (
      id INT AUTO_INCREMENT PRIMARY KEY,
      nome VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS livro (
     id INT AUTO_INCREMENT PRIMARY KEY,
     titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    editora VARCHAR(255) NOT NULL,
    data_publicacao DATE NOT NULL,
    quantidade_paginas INT NOT NULL,
    genero_id INT NOT NULL,
    formato VARCHAR(20) NOT NULL,
    edicao_especial BOOLEAN NOT NULL,
    FOREIGN KEY (genero_id) REFERENCES genero(id)
    );

INSERT INTO genero (nome) VALUES
    ('Ficção Científica'),
    ('Romance'),
    ('Fantasia'),
    ('Terror'),
    ('História'),
    ('Biografia');