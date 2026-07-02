package cuisine.com.recettes;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void createReview(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public void updateReview(Review review) {
        reviewRepository.update(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.delete(id);
    }
}
