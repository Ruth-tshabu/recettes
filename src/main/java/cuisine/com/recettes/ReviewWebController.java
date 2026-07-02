package cuisine.com.recettes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewWebController {

    private final ReviewService reviewService;
    private final RecipeService recipeService;

    public ReviewWebController(ReviewService reviewService, RecipeService recipeService) {
        this.reviewService = reviewService;
        this.recipeService = recipeService;
    }

    // Affichage de la liste des avis
    @GetMapping
    public String listReviews(Model model) {
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "reviews-list";
    }

    // Affichage du formulaire d'ajout avec la liste des recettes
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("review", new Review());
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "review-form";
    }

    // Validation du formulaire d'avis
    @PostMapping
    public String saveReview(@ModelAttribute("review") Review review) {
        reviewService.createReview(review);
        return "redirect:/reviews";
    }

    // Suppression d'un avis
    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }
}
