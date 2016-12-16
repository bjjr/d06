package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ContestService;
import services.RecipeService;
import services.UserService;
import controllers.AbstractController;
import domain.Contest;
import domain.Recipe;
import domain.RecipeCopy;
import domain.User;

@Controller
@RequestMapping("/recipe/user")
public class RecipeUserController extends AbstractController {

	// Services --------------------------------------------
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ContestService contestService;
	
	// Constructor -----------------------------------------
	
	public RecipeUserController() {
		super();
	}
	
	// Listing ---------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Recipe> recipes;
		User user;
		Boolean owner;
		
		owner = true;
		
		user = userService.findByPrincipal();
		recipes = user.getRecipes();
		
		for(Recipe r : recipes){
			if(!userService.findByPrincipal().getRecipes().contains(r)){
				owner = false;
				break;
			}
		}
		
		result = new ModelAndView("recipe/list");
		result.addObject("requestURI", "recipe/user/list.do");
		result.addObject("recipes", recipes);
		result.addObject("owner", owner);
		
		return result;
	}
	
	// Qualifying ------------------------------------------
	
	@RequestMapping(value = "/qualify", method = RequestMethod.GET)
	public ModelAndView copyRecipe(@RequestParam int recipeId) {
		ModelAndView result;
		Recipe recipe;
		RecipeCopy recipeCopy;
		Collection<Contest> contests;
		
		recipe = recipeService.findOne(recipeId);
		Assert.notNull(recipe);
		
		try{
			recipeCopy = recipeService.copyRecipe(recipe);
			contests = contestService.findAll();
			result = new ModelAndView("recipe/qualify");
			result.addObject("recipeCopy", recipeCopy);
			result.addObject("contests", contests);
		}
		catch (Throwable oops) {
			result = list();
			result.addObject("message", "recipe.commit.error");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/qualify", method = RequestMethod.POST, params = "qualify")
	public ModelAndView qualify(@Valid RecipeCopy recipeCopy, BindingResult binding) {
		ModelAndView result;
		Contest contest;
		Collection<Contest> contests;
		
		contests = contestService.findAll();

		if (binding.hasErrors()) {
			result = new ModelAndView("recipe/qualify");
			result.addObject("recipeCopy", recipeCopy);
		} else {
			try {
				contest = recipeCopy.getContest();
				recipeService.qualifyRecipe(recipeCopy, contest);
				result = list();
				result.addObject("messageStatus", "recipe.commit.ok");
			} catch (Throwable oops) {
				result = new ModelAndView("recipe/qualify");
				result.addObject("recipeCopy", recipeCopy);
				result.addObject("message", "recipe.commit.error");
				result.addObject("contests", contests);
			}
		}

		return result;
	}
	
	// Create ----------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Recipe recipe;
		
		recipe = recipeService.create();
		
		res = createEditModelAndView(recipe);
		
		return res;
	}
	
	// Editing ---------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int recipeId) {
		ModelAndView res;
		Recipe recipe;
		
		recipe = recipeService.findOne(recipeId);
		res = createEditModelAndView(recipe);
		
		return res;
	}
	
	// Saving -----------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Recipe recipe, BindingResult binding) {
		ModelAndView res;
		
		if (binding.hasErrors()) {
			res = createEditModelAndView(recipe);
		} else {
			try {
				recipeService.save(recipe);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable th) {
				res = createEditModelAndView(recipe, "recipe.commit.error");
			}
		}
		
		return res;
	}
	
	// Deleting ----------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Recipe recipe, BindingResult binding) {
		ModelAndView res;

		try {
			recipeService.delete(recipe);
			res = new ModelAndView("redirect:list.do");
		} catch (Throwable th) {
			res = createEditModelAndView(recipe, "recipe.commit.error");
		}

		return res;
	}
	
	// Ancillary methods -------------------------------------

	protected ModelAndView createEditModelAndView(Recipe recipe) {
		ModelAndView res;

		res = createEditModelAndView(recipe, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Recipe recipe, String message) {
		ModelAndView res;

		res = new ModelAndView("recipe/edit");
		res.addObject("recipe", recipe);
		res.addObject("message", message);

		return res;
	}
}
