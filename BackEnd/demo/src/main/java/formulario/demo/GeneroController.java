package formulario.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/generos")
@CrossOrigin(origins = "*")

public class GeneroController {


    private final JdbcTemplate jdbcTemplate;

    public GeneroController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public List<Genero> listarGeneros() {
        String sql = "SELECT id, nome FROM genero ORDER BY nome";
        return jdbcTemplate.query(sql, (rs, colunas) ->
                new Genero(rs.getInt("id"), rs.getString("nome"))
        );
    }

}