package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Comment;
import domain.Recipe;

import services.CommentService;
import services.RecipeService;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	// Services

	@Autowired
	private CommentService commentService;

	@Autowired
	private RecipeService recipeService;

	// Constructors

	public CommentController() {
		super();
	}

	// Write comments

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int recipeId) {
		ModelAndView result;
		Recipe recipe;
		Comment comment;

		recipe = recipeService.findOne(recipeId);
		comment = commentService.create(recipe);
		
		result = new ModelAndView("comment/create");
		result.addObject("comment", comment);

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params= "save")
	public ModelAndView save(@Valid Comment comment, BindingResult binding) {
		ModelAndView result;
		Collection<Recipe> recipes;

		if (binding.hasErrors()) {
			result = new ModelAndView("comment/create");
			result.addObject("comment", comment);
		} else {
			try {
				recipes = recipeService.findAllRecipesGroupByCategory();
				commentService.save(comment);
				result = new ModelAndView("recipe/list");
				result.addObject("requestURI", "recipe/list.do");
				result.addObject("recipes", recipes);
				result.addObject("message", "comment.commit.ok");
			} catch (Throwable oops) {
				result = new ModelAndView("comment/create");
				result.addObject("comment", comment);
				result.addObject("message", "comment.commit.error");
			}
		}

		return result;
	}

}
