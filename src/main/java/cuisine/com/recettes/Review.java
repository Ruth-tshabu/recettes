package cuisine.com.recettes;

import lombok.Data;

@Data
public class Review {

    private Long id;
    private String reviewerName;
    private Integer rating;
    private String comment;
    private Recipe recipe;
}
