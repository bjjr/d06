package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.RecipeService;
import services.UserService;
import domain.Comment;
import domain.Quantity;
import domain.Recipe;
import domain.Step;

@Controller
@RequestMapping("/recipe")
public class RecipeController extends AbstractController {

	// Services

	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActorService actorService;

	// Constructors

	public RecipeController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Recipe> recipes;

		recipes = recipeService.findAllRecipesGroupByCategory();
		
		result = new ModelAndView("recipe/list");
		result.addObject("requestURI", "recipe/list.do");
		result.addObject("recipes", recipes);

		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, params = "search")
	public ModelAndView search(@RequestParam String recipe) {
		ModelAndView result;
		Collection<Recipe> recipes;
		Collection<Recipe> recipesWanted;

		recipes = recipeService.findAllRecipesGroupByCategory();

		if (recipe == "") {
			result = new ModelAndView("recipe/list");
			result.addObject("requestURI", "recipe/list.do");
			result.addObject("recipes", recipes);
		} else {
			recipesWanted = recipeService.findByKeyword(recipe);

			result = new ModelAndView("recipe/list");
			result.addObject("requestURI", "recipe/list.do");
			result.addObject("recipes", recipesWanted);
		}

		return result;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int recipeId) {
		ModelAndView result;
		Recipe recipe;
		Collection<Quantity> quantities;
		Collection<Step> steps;
		Collection<Comment> comments;
		Integer likes;
		Integer dislikes;
		Boolean owner;

		recipe = recipeService.findOne(recipeId);
		quantities = recipe.getQuantities();
		steps = recipe.getSteps();
		comments = recipe.getComments();
		likes = recipeService.findLikes(recipe);
		dislikes = recipeService.findDislikes(recipe);
		owner = false;
		
		if (actorService.checkAuthority("USER"))
			owner = userService.findByPrincipal().equals(recipe.getUser());
		
		result = new ModelAndView("recipe/display");
		result.addObject("requestURI", "recipe/display.do");
		result.addObject("recipe", recipe);
		result.addObject("quantities", quantities);
		result.addObject("steps", steps);
		result.addObject("comments", comments);
		result.addObject("likesSA", likes);
		result.addObject("dislikesSA", dislikes);
		result.addObject("owner", owner);

		return result;
	}	

}
