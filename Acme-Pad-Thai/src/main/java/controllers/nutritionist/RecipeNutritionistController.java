package controllers.nutritionist;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.NutritionistService;
import services.RecipeService;
import controllers.AbstractController;
import domain.LikeSA;
import domain.Nutritionist;
import domain.Recipe;

@Controller
@RequestMapping("/recipe/nutritionist")
public class RecipeNutritionistController extends AbstractController {

	// Services

	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private NutritionistService nutritionistService;

	// Constructors

	public RecipeNutritionistController() {
		super();
	}
	
	// Listing
	
	@RequestMapping(value = "/listFollow", method = RequestMethod.GET)
	public ModelAndView listFollow() {
		ModelAndView result;
		Collection<Recipe> likes;
		Collection<Recipe> recipes;
		Nutritionist nutritionist;
		
		recipes = recipeService.recipesFollows();
		likes = new ArrayList<Recipe>();
		nutritionist = nutritionistService.findByPrincipal();
		
		for (LikeSA l : nutritionist.getLikesSA()) {
			likes.add(l.getRecipe());
		}
		
		result = new ModelAndView("recipe/list");
		result.addObject("requestURI", "recipe/nutritionist/list.do");
		result.addObject("recipes", recipes);
		result.addObject("likes", likes);
		
		return result;
	}

}
