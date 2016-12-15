package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.LikeSA;
import domain.Recipe;

import services.LikeSAService;
import services.RecipeService;

@Controller
@RequestMapping("/likeSA")
public class LikeSAController extends AbstractController {
	
	//Services
	
	@Autowired
	private LikeSAService likeSAService;
	
	@Autowired
	private RecipeService recipeService;
	
	//Constructors
	
	public LikeSAController(){
		super();
	}
	
	//Like or dislike
	
	@RequestMapping(value = "/like", method = RequestMethod.GET)
	public ModelAndView like(@RequestParam int recipeId) {
		ModelAndView result;
		Recipe recipe;
		Collection<Recipe> recipes;
		LikeSA like;
		
		recipe = recipeService.findOne(recipeId);
		recipes = recipeService.findAllRecipesGroupByCategory();
		
		try{
			like = likeSAService.create(recipe);
			like.setLikeSA(true);
			likeSAService.save(like);
			result = new ModelAndView("recipe/list");
			result.addObject("requestURI", "recipe/list.do"); 
			result.addObject("recipes", recipes);
			result.addObject("message", "likeSA.commit.ok");
		}
		catch(Throwable oops){
			result = new ModelAndView("recipe/list");
			result.addObject("requestURI", "recipe/list.do"); 
			result.addObject("recipes", recipes);
			result.addObject("message", "likeSA.commit.error");
		}

		return result;
	}
	
	@RequestMapping(value = "/dislike", method = RequestMethod.GET)
	public ModelAndView dislike(@RequestParam int recipeId) {
		ModelAndView result;
		Recipe recipe;
		Collection<Recipe> recipes;
		LikeSA like;
		
		recipe = recipeService.findOne(recipeId);
		recipes = recipeService.findAllRecipesGroupByCategory();
		
		try{
			like = likeSAService.create(recipe);
			like.setLikeSA(false);
			likeSAService.save(like);
			result = new ModelAndView("recipe/list");
			result.addObject("requestURI", "recipe/list.do"); 
			result.addObject("recipes", recipes);
			result.addObject("message", "likeSA.commit.ok");
		}
		catch(Throwable oops){
			result = new ModelAndView("recipe/list");
			result.addObject("requestURI", "recipe/list.do"); 
			result.addObject("recipes", recipes);
			result.addObject("message", "likeSA.commit.error");
		}

		return result;
	}


}
