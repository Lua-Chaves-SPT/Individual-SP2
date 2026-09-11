package formulario.demo;

import java.time.LocalDate;

public class Livro {
    private Integer id;
    private String titulo;
    private String autor;
    private String editora;
    private LocalDate dataPublicacao;
    private Integer quantidadePaginas;
    private Integer generoId;
    private String formato;
    private Boolean edicaoEspecial;

    public Livro() {}

    public Livro(Integer id, String titulo, String autor, String editora, LocalDate dataPublicacao, Integer quantidadePaginas, Integer generoId, String formato, Boolean edicaoEspecial) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.dataPublicacao = dataPublicacao;
        this.quantidadePaginas = quantidadePaginas;
        this.generoId = generoId;
        this.formato = formato;
        this.edicaoEspecial = edicaoEspecial;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }

    public LocalDate getDataPublicacao() { return dataPublicacao; }
    public void setDataPublicacao(LocalDate dataPublicacao) { this.dataPublicacao = dataPublicacao; }

    public Integer getQuantidadePaginas() { return quantidadePaginas; }
    public void setQuantidadePaginas(Integer quantidadePaginas) { this.quantidadePaginas = quantidadePaginas; }

    public Integer getGeneroId() { return generoId; }
    public void setGeneroId(Integer generoId) { this.generoId = generoId; }

    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }

    public Boolean getEdicaoEspecial() { return edicaoEspecial; }
    public void setEdicaoEspecial(Boolean edicaoEspecial) { this.edicaoEspecial = edicaoEspecial; }
}