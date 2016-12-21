package controllers.nutritionist;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RecipeService;

import controllers.AbstractController;
import domain.Recipe;

@Controller
@RequestMapping("/recipe/nutritionist")
public class RecipeNutritionistController extends AbstractController {

	// Services

	@Autowired
	private RecipeService recipeService;

	// Constructors

	public RecipeNutritionistController() {
		super();
	}
	
	// Listing
	
	@RequestMapping(value = "/listFollow", method = RequestMethod.GET)
	public ModelAndView listFollow() {
		ModelAndView result;
		Collection<Recipe> recipes;
		
		recipes = recipeService.recipesFollows();
		
		result = new ModelAndView("recipe/list");
		result.addObject("requestURI", "recipe/nutritionist/list.do");
		result.addObject("recipes", recipes);
		
		return result;
	}

}
