package controllers.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.ContestService;
import services.RecipeService;
import services.UserService;
import controllers.AbstractController;
import domain.Category;
import domain.Contest;
import domain.LikeSA;
import domain.Recipe;
import domain.RecipeCopy;
import domain.User;

@Controller
@RequestMapping("/recipe/user")
public class RecipeUserController extends AbstractController {

	// Services

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private UserService userService;

	@Autowired
	private ContestService contestService;
	
	@Autowired
	private CategoryService categoryService;

	// Constructors

	public RecipeUserController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Recipe> recipes;
		Collection<Recipe> likes;
		Collection<Recipe> own;
		User user;
		Boolean owner;

		owner = true;
		likes = new ArrayList<Recipe>();
		own = new ArrayList<Recipe>();
		user = userService.findByPrincipal();
		recipes = user.getRecipes();
		
		for (LikeSA l : user.getLikesSA()) {
			likes.add(l.getRecipe());
		}
		own.addAll(user.getRecipes());

		for (Recipe r : recipes) {
			if (!userService.findByPrincipal().getRecipes().contains(r)) {
				owner = false;
				break;
			}
		}

		result = new ModelAndView("recipe/list");
		result.addObject("requestURI", "recipe/user/list.do");
		result.addObject("recipes", recipes);
		result.addObject("owner", owner);
		result.addObject("likes", likes);
		result.addObject("own", own);

		return result;
	}

	@RequestMapping(value = "/listFollow", method = RequestMethod.GET)
	public ModelAndView listFollow() {
		ModelAndView result;
		Collection<Recipe> recipes;
		Boolean owner;

		owner = true;

		recipes = recipeService.recipesFollows();

		for (Recipe r : recipes) {
			if (!userService.findByPrincipal().getRecipes().contains(r)) {
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

	@RequestMapping(value = "/qualify", method = RequestMethod.GET)
	public ModelAndView copyRecipe(@RequestParam int recipeId) {
		ModelAndView result;
		Recipe recipe;
		RecipeCopy recipeCopy;
		Collection<Contest> contests;

		recipe = recipeService.findOneToEdit(recipeId);

		try {
			recipeCopy = recipeService.copyRecipe(recipe);
			contests = contestService.findOpenContests();
			result = new ModelAndView("recipe/qualify");
			result.addObject("recipeCopy", recipeCopy);
			result.addObject("contests", contests);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "recipe.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/qualify", method = RequestMethod.POST, params = "qualify")
	public ModelAndView qualify(@Valid RecipeCopy recipeCopy,
			BindingResult binding) {
		ModelAndView result;
		Contest contest;
		Collection<Contest> contests;

		contests = contestService.findOpenContests();

		if (binding.hasErrors()) {
			result = new ModelAndView("recipe/qualify");
			result.addObject("recipeCopy", recipeCopy);
		} else {
			try {
				contest = recipeCopy.getContest();
				recipeService.qualifyRecipe(recipeCopy, contest);
				result = new ModelAndView("redirect:list.do");
				result.addObject("message", "recipe.commit.ok");
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

		recipe = recipeService.findOneToEdit(recipeId);
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

	// Add categories

	@RequestMapping(value = "/addCategory", method = RequestMethod.GET)
	public ModelAndView addProperty(@RequestParam int recipeId) {
		ModelAndView result;
		Recipe recipe;
		Collection<Category> categories;

		recipe = recipeService.findOne(recipeId);
		categories = recipeService.findAvailableCategories(recipe);

		result = new ModelAndView("recipe/addCategory");
		result.addObject("requestURI", "recipe/user/addCategory.do");
		result.addObject("recipe", recipe);
		result.addObject("categories", categories);

		return result;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@RequestParam int categoryId, @RequestParam int recipeId) {
		ModelAndView result;
		Recipe recipe;
		Category category;
		Collection<Category> categories;

		recipe = recipeService.findOne(recipeId);
		category = categoryService.findOne(categoryId);

		try {
			recipeService.addCategory(recipe, category);
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "recipe.commit.ok");
		} catch (Throwable oops) {
			categories = recipeService.findAvailableCategories(recipe);
			result = new ModelAndView("recipe/addCategory");
			result.addObject("requestURI", "recipe/user/addCategory.do");
			result.addObject("recipe", recipe);
			result.addObject("categories", categories);
			result.addObject("message", "recipe.commit.error");
		}

		return result;
	}

	// Remove properties

	@RequestMapping(value = "/removeCategory", method = RequestMethod.GET)
	public ModelAndView removeProperty(@RequestParam int recipeId) {
		ModelAndView result;
		Recipe recipe;
		Collection<Category> categories;

		recipe = recipeService.findOne(recipeId);
		categories = recipe.getCategories();
		result = new ModelAndView("recipe/removeCategory");
		result.addObject("requestURI", "recipe/user/removeCategory.do");
		result.addObject("recipe", recipe);
		result.addObject("categories", categories);

		return result;
	}

	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public ModelAndView remove(@RequestParam int categoryId, @RequestParam int recipeId) {
		ModelAndView result;
		Recipe recipe;
		Category category;
		Collection<Category> categories;

		recipe = recipeService.findOne(recipeId);
		category = categoryService.findOne(categoryId);
		try {
			recipeService.removeCategory(recipe, category);
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "recipe.commit.ok");
		} catch (Throwable oops) {
			categories = recipe.getCategories();
			result = new ModelAndView("recipe/removeCategory");
			result.addObject("requestURI", "recipe/user/removeCategory.do");
			result.addObject("recipe", recipe);
			result.addObject("categories", categories);
			result.addObject("message", "recipe.commit.error");
		}

		return result;
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
