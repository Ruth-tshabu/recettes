package cuisine.com.recettes;

import java.util.List;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Recipe> recipeRowMapper;

    public RecipeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.recipeRowMapper = JdbcTemplateMapperFactory.newInstance()
            .addKeys("id", "category_id")
            .newRowMapper(Recipe.class);
    }

    public List<Recipe> findAll() {
        String sql = """
            SELECT
                r.id as id, r.name as name, r.difficulty as difficulty,
                c.id as category_id, c.name as category_name
            FROM recipe r
            JOIN category c ON r.category_id = c.id
            """;
        return jdbcTemplate.query(sql, recipeRowMapper);
    }
}
