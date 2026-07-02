package cuisine.com.recettes;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {

    private final ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id) {
        Review review = reviewService.getReviewById(id);
        if (review == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
        return review;
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        if (review.getRecipe() == null || review.getRecipe().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe ID is required");
        }
        reviewService.createReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        Review existing = reviewService.getReviewById(id);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
        if (review.getRecipe() == null || review.getRecipe().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe ID is required");
        }
        review.setId(id);
        reviewService.updateReview(review);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        Review existing = reviewService.getReviewById(id);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
