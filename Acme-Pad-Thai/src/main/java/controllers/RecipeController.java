package controllers;

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

import security.Authority;
import services.ActorService;
import services.CommentService;
import services.LikeSAService;
import services.RecipeService;
import services.SocialActorService;
import services.UserService;

import domain.Comment;
import domain.LikeSA;
import domain.Quantity;
import domain.Recipe;
import domain.SocialActor;
import domain.Step;

@Controller
@RequestMapping("/recipe")
public class RecipeController extends AbstractController {

	// Services

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private LikeSAService likeSAService;

	@Autowired
	private SocialActorService socialActorService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private UserService userService;

	@Autowired
	private CommentService commentService;

	// Constructors

	public RecipeController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Recipe> recipes;
		Collection<Recipe> likes;
		Collection<Recipe> own;
		SocialActor principal;
		Authority authority;
		Authority authority2;

		authority = new Authority();
		authority2 = new Authority();
		authority.setAuthority(Authority.USER);
		authority2.setAuthority(Authority.NUTRITIONIST);
		likes = new ArrayList<Recipe>();
		own = new ArrayList<Recipe>();

		recipes = recipeService.findAllRecipesGroupByCategory();

		if (actorService.checkAuthority("USER")
				|| actorService.checkAuthority("ADMINISTRATOR")
				|| actorService.checkAuthority("COOK")
				|| actorService.checkAuthority("SPONSOR") || actorService
				.checkAuthority("NUTRITIONIST") ) {
			if (actorService.findByPrincipal().getUserAccount()
					.getAuthorities().contains(authority)
					|| actorService.findByPrincipal().getUserAccount()
							.getAuthorities().contains(authority2)) {

				principal = socialActorService.findByPrincipal();

				for (LikeSA l : principal.getLikesSA()) {
					likes.add(l.getRecipe());

				}

				if (principal.getUserAccount().getAuthorities()
						.contains(authority)) {
					own.addAll(userService.findByPrincipal().getRecipes());
				}
			}
		}

		result = new ModelAndView("recipe/list");
		result.addObject("requestURI", "recipe/list.do");
		result.addObject("recipes", recipes);
		result.addObject("likes", likes);
		result.addObject("own", own);

		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, params = "search")
	public ModelAndView search(@RequestParam String recipe) {
		ModelAndView result;
		Collection<Recipe> recipesWanted;

		if (recipe == "") {
			result = new ModelAndView("redirect:list.do");
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

		recipe = recipeService.findOne(recipeId);
		quantities = recipe.getQuantities();
		steps = recipe.getSteps();
		comments = recipe.getComments();
		likes = recipeService.findLikes(recipe);
		dislikes = recipeService.findDislikes(recipe);
		result = new ModelAndView("recipe/display");
		result.addObject("requestURI", "recipe/display.do");
		result.addObject("recipe", recipe);
		result.addObject("quantities", quantities);
		result.addObject("steps", steps);
		result.addObject("comments", comments);
		result.addObject("likesSA", likes);
		result.addObject("dislikesSA", dislikes);

		return result;
	}

	// Like

	@RequestMapping(value = "/like", method = RequestMethod.GET)
	public ModelAndView like(@RequestParam int recipeId) {
		ModelAndView result;
		Recipe recipe;
		LikeSA like;

		recipe = recipeService.findOne(recipeId);

		try {
			like = likeSAService.create(recipe);
			like.setLikeSA(true);
			likeSAService.save(like);
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "likeSA.commit.ok");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "likeSA.commit.error");
		}

		return result;
	}

	// Dislike

	@RequestMapping(value = "/dislike", method = RequestMethod.GET)
	public ModelAndView dislike(@RequestParam int recipeId) {
		ModelAndView result;
		Recipe recipe;
		LikeSA like;

		recipe = recipeService.findOne(recipeId);

		try {
			like = likeSAService.create(recipe);
			like.setLikeSA(false);
			likeSAService.save(like);
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "likeSA.commit.ok");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "likeSA.commit.error");
		}

		return result;
	}

	// Write comments

	@RequestMapping(value = "/createComment", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int recipeId) {
		ModelAndView result;
		Recipe recipe;
		Comment comment;

		recipe = recipeService.findOne(recipeId);
		comment = commentService.create(recipe);

		result = new ModelAndView("comment/createComment");
		result.addObject("comment", comment);

		return result;
	}

	@RequestMapping(value = "/createComment", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Comment comment, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("comment/createComment");
			result.addObject("comment", comment);
		} else {
			try {
				commentService.save(comment);
				result = new ModelAndView("redirect:list.do");
				result.addObject("message", "comment.commit.ok");
			} catch (Throwable oops) {
				result = new ModelAndView("comment/createComment");
				result.addObject("comment", comment);
				result.addObject("message", "comment.commit.error");
			}
		}

		return result;
	}

}
