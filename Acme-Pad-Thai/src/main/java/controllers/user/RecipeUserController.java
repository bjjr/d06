package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RecipeService;
import controllers.AbstractController;
import domain.Recipe;

@Controller
@RequestMapping("/recipe/user")
public class RecipeUserController extends AbstractController {

	// Services --------------------------------------------
	@Autowired
	private RecipeService recipeService;
	
	// Constructor -----------------------------------------
	
	public RecipeUserController() {
		super();
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
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(@RequestParam int recipeId) {
		ModelAndView res;
		Recipe recipe;
		
		recipe = recipeService.findOne(recipeId);
		res = createEditModelAndView(recipe);
		
		return res;
	}
	
	// Saving ------------------------------------------
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
			res = createEditModelAndView(recipe, "masterClass.commit.error");
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
