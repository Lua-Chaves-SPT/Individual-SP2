# Projeto Individual - Cadastro e Listagem de Livros

Projetivo Individual com estudos baseados (HTML, CSS e JavaScript) e Back-end em Java com Spring Boot e JdbcTemplate, integrada ao banco de dados relacional H2.

## 🚀 Como Executar o Projeto

### Back-end (API Java)
1. Certifique-se de ter o Java 17+ instalado.
2. Abra a pasta `backend` ou a raiz do projeto no seu ambiente de desenvolvimento (VS Code / IntelliJ).
3. Execute a classe principal `DemoApplication.java`.
4. O servidor iniciará por padrão em `http://localhost:8080`.
5. O console H2 estará acessível em `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:livrodb`).

### Front-end
1. Abra a pasta do projeto no VS Code.
2. Inicie o arquivo `index.html` utilizando a extensão **Live Server** (para rodar em `http://127.0.0.1:5500`).

---

## 📖 Documentação dos Endpoints e Exemplos de Requisições

### 1. Listar Gêneros
* **URL:** `/api/generos`
* **Método:** `GET`
* **Descrição:** Retorna a lista de gêneros cadastrados para popular o select do formulário.
* **Status HTTP:** `200 OK`

**Exemplo de Resposta (JSON):**
```json
[
  { "id": 1, "nome": "Ficção Científica" },
  { "id": 2, "nome": "Romance" },
  { "id": 3, "nome": "Fantasia" }
]