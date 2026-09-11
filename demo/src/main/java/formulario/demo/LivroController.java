package formulario.demo;


import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/livros")
@CrossOrigin(origins = "*")
public class LivroController {

    private final JdbcTemplate jdbcTemplate;

    public LivroController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

        @PostMapping
        public ResponseEntity<Livro> cadastrarLivro(@RequestBody Livro livro) {

            if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty() ||
                    livro.getAutor() == null || livro.getAutor().trim().isEmpty() ||
                    livro.getEditora() == null || livro.getEditora().trim().isEmpty() ||
                    livro.getDataPublicacao() == null ||
                    livro.getDataPublicacao().isAfter(LocalDate.now())||
                    livro.getQuantidadePaginas() == null || livro.getQuantidadePaginas() <= 0 ||
                    livro.getGeneroId() == null) {

                return ResponseEntity.status(400)
                        .build();
            }

            String sql = "INSERT INTO livro (titulo, autor, editora, data_publicacao, quantidade_paginas, genero_id, formato, edicao_especial) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            boolean edicaoEspecial = false;

            if (livro.getEdicaoEspecial() != null) {
                edicaoEspecial = livro.getEdicaoEspecial();
            }

            jdbcTemplate.update(sql,
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getEditora(),
                    livro.getDataPublicacao(),
                    livro.getQuantidadePaginas(),
                    livro.getGeneroId(),
                    livro.getFormato(),
                    edicaoEspecial
            );

            return ResponseEntity.status(201).body(livro);
        }

        @GetMapping
        public ResponseEntity<List<Livro>> listarLivros() {
            String sql = "SELECT * FROM livro";

            List<Livro> livros = jdbcTemplate.query(sql, (rs, colunas) -> {
                Livro livrosListados = new Livro();
                livrosListados.setId(rs.getInt("id"));
                livrosListados.setTitulo(rs.getString("titulo"));
                livrosListados.setAutor(rs.getString("autor"));
                livrosListados.setEditora(rs.getString("editora"));
                livrosListados.setDataPublicacao(rs.getDate("data_publicacao").toLocalDate());
                livrosListados.setQuantidadePaginas(rs.getInt("quantidade_paginas"));
                livrosListados.setGeneroId(rs.getInt("genero_id"));
                livrosListados.setFormato(rs.getString("formato"));
                livrosListados.setEdicaoEspecial(rs.getBoolean("edicao_especial"));
                return livrosListados;
            });

            if (livros.isEmpty()) {
                return ResponseEntity.status(204).build();
            }

            return ResponseEntity.status(200).body(livros);
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Integer id) {
        String sql = "DELETE FROM livro WHERE id = ?";

        int linhasAfetadas = jdbcTemplate.update(sql, id);

        if (linhasAfetadas == 0) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(204).build();
    }


    }

