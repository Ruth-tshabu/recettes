package cuisine.com.recettes;

import java.util.List;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Review> reviewRowMapper;

    public ReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        // Configuration SimpleFlatMapper (Identique pour MySQL et PostgreSQL)
        this.reviewRowMapper = JdbcTemplateMapperFactory.newInstance()
            .addKeys("id", "recipe_id", "recipe_category_id")
            .newRowMapper(Review.class);
    }

    // COMPATIBLE MYSQL : Insertion standard
    public int save(Review review) {
        String sql =
            "INSERT INTO review (reviewer_name, rating, comment, recipe_id) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            review.getReviewerName(),
            review.getRating(),
            review.getComment(),
            review.getRecipe().getId()
        );
    }

    // COMPATIBLE MYSQL : Jointures standards (Inner Join)
    public List<Review> findAll() {
        String sql = """
            SELECT
                r.id as id, r.reviewer_name as reviewer_name, r.rating as rating, r.comment as comment,
                rec.id as recipe_id, rec.name as recipe_name, rec.difficulty as recipe_difficulty,
                cat.id as recipe_category_id, cat.name as recipe_category_name
            FROM review r
            JOIN recipe rec ON r.recipe_id = rec.id
            JOIN category cat ON rec.category_id = cat.id
            """;
        return jdbcTemplate.query(sql, reviewRowMapper);
    }

    // COMPATIBLE MYSQL : Sélection par ID avec jointures
    public Review findById(Long id) {
        String sql = """
            SELECT
                r.id as id, r.reviewer_name as reviewer_name, r.rating as rating, r.comment as comment,
                rec.id as recipe_id, rec.name as recipe_name, rec.difficulty as recipe_difficulty,
                cat.id as recipe_category_id, cat.name as recipe_category_name
            FROM review r
            JOIN recipe rec ON r.recipe_id = rec.id
            JOIN category cat ON rec.category_id = cat.id
            WHERE r.id = ?
            """;
        List<Review> results = jdbcTemplate.query(sql, reviewRowMapper, id);
        return results.isEmpty() ? null : results.get(0);
    }

    // COMPATIBLE MYSQL : Mise à jour standard
    public int update(Review review) {
        String sql = """
            UPDATE review
            SET reviewer_name = ?, rating = ?, comment = ?, recipe_id = ?
            WHERE id = ?
            """;
        return jdbcTemplate.update(
            sql,
            review.getReviewerName(),
            review.getRating(),
            review.getComment(),
            review.getRecipe().getId(),
            review.getId()
        );
    }

    // COMPATIBLE MYSQL : Suppression standard
    public int delete(Long id) {
        String sql = "DELETE FROM review WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
