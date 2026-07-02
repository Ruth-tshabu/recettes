package cuisine.com.recettes;

import lombok.Data;

@Data
public class Recipe {

    private Long id;
    private String name;
    private String difficulty;
    private Category category;
}
