package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Category;
import domain.Comment;
import domain.Contest;
import domain.LikeSA;
import domain.Quantity;
import domain.Recipe;
import domain.RecipeCopy;
import domain.SocialActor;
import domain.Step;
import domain.User;

import repositories.RecipeRepository;
import security.Authority;

@Service
@Transactional
public class RecipeService {

	// Managed repository
	@Autowired
	private RecipeRepository recipeRepository;

	// Supporting services
	@Autowired
	private ActorService actorService;

	@Autowired
	private UserService userService;

	@Autowired
	private StepService stepService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private LikeSAService likeSAService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private QuantityService quantityService;

	@Autowired
	private SocialActorService socialActorService;

	@Autowired
	private RecipeCopyService recipeCopyService;

	@Autowired
	private ContestService contestService;

	// Constructors
	public RecipeService() {
		super();
	}

	// Simple CRUD methods
	public Recipe create() {
		Assert.isTrue(actorService.checkAuthority("USER"));

		Recipe result;
		User owner;

		owner = userService.findByPrincipal();
		result = new Recipe();
		result.setUser(owner);
		userService.save(owner);

		return result;
	}

	public Recipe save(Recipe recipe) {
		Assert.isTrue(actorService.checkAuthority("USER")
				|| actorService.checkAuthority("NUTRITIONIST"));
		Assert.notNull(recipe);

		Recipe result;
		Date momentCreated;
		Date momentUpdated;

		momentCreated = new Date(System.currentTimeMillis() - 1000);
		momentUpdated = new Date(System.currentTimeMillis() - 1000);
		recipe.setMomentAuthored(momentCreated);
		recipe.setMomentLastUpdated(momentUpdated);

		result = recipeRepository.save(recipe);

		return result;
	}

	public void flush() {
		recipeRepository.flush();
	}

	public Recipe findOne(int recipeID) {
		Recipe result;

		result = recipeRepository.findOne(recipeID);
		Assert.notNull(result);

		return result;
	}

	public void delete(Recipe recipe) {
		Assert.isTrue(actorService.checkAuthority("USER"));
		Assert.notNull(recipe);
		Assert.isTrue(recipe.getId() != 0);
		Assert.isTrue(recipeRepository.exists(recipe.getId()));

		User owner;
		Collection<Category> categories;

		owner = recipe.getUser();
		categories = categoryService.findAll();

		for (Step s : recipe.getSteps()) {
			stepService.delete(s);
		}

		for (Quantity q : recipe.getQuantities()) {
			quantityService.delete(q);
		}

		for (LikeSA l : recipe.getLikesSA()) {
			likeSAService.delete(l);
		}

		for (Comment c : recipe.getComments()) {
			commentService.delete(c);
		}

		for (Category c : categories) {
			if (c.getRecipes().contains(recipe)) {
				c.removeRecipe(recipe);
				categoryService.save(c);
			}
		}

		recipe.setUser(null);
		owner.removeRecipe(recipe);
		userService.save(owner);

		recipeRepository.delete(recipe);

	}

	// Other business methods
	public Double findMinRecipesUser() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));

		Double result;

		result = recipeRepository.findMinRecipesUser();
		Assert.notNull(result);

		return result;
	}

	public Double findAvgRecipesUser() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));

		Double result;

		result = recipeRepository.findAvgRecipesUser();
		Assert.notNull(result);

		return result;
	}

	public Double findMaxRecipesUser() {
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));

		Double result;

		result = recipeRepository.findMaxRecipesUser();
		Assert.notNull(result);

		return result;
	}

	public Collection<Recipe> findAllRecipesGroupByCategory() {
		Collection<Recipe> result;

		result = recipeRepository.findAllRecipesGroupByCategory();
		Assert.notNull(result);

		return result;
	}

	public Collection<Recipe> findByKeyword(String keyword) {
		Assert.notNull(keyword);

		Collection<Recipe> result;

		result = recipeRepository.findByKeyword(keyword);
		Assert.notNull(result);

		return result;

	}

	public RecipeCopy copyRecipe(Recipe recipe) {
		Assert.isTrue(actorService.checkAuthority("USER"));
		Assert.notNull(recipe);

		int countLikes;
		int countDislikes;
		RecipeCopy recipeCopy;
		User u;

		u = userService.findByPrincipal();
		countLikes = 0;
		countDislikes = 0;

		Assert.isTrue(u.getRecipes().contains(recipe),
				"An user only could qualify his recipes");

		for (LikeSA l : recipe.getLikesSA()) {
			if (l.isLikeSA() == true) {
				countLikes++;
			} else {
				countDislikes++;
			}
		}

		Assert.isTrue(countLikes >= 5 && countDislikes == 0);

		recipeCopy = recipeCopyService.create();

		recipeCopy.setTicker(recipe.getTicker());
		recipeCopy.setTitle(recipe.getTitle());
		recipeCopy.setSummary(recipe.getSummary());
		recipeCopy.setMomentAuthored(recipe.getMomentAuthored());
		recipeCopy.setMomentLastUpdated(recipe.getMomentLastUpdated());
		recipeCopy.setPictures(recipe.getPictures());
		recipeCopy.setHints(recipe.getHints());
		recipeCopy.setWinner(false);
		recipeCopy.setNameUser(recipe.getUser().getName()
				+ recipe.getUser().getSurname());
		recipeCopy.setLikesRC(countLikes);
		recipeCopy.setDislikesRC(countDislikes);

		return recipeCopy;

	}

	public void qualifyRecipe(RecipeCopy recipeCopy, Contest contest) {
		Assert.isTrue(actorService.checkAuthority("USER"));
		Assert.notNull(contest);

		for (RecipeCopy rc : contest.getRecipeCopies()) {
			Assert.isTrue(!recipeCopy.getTicker().equals(rc.getTicker()),
					"An user cannot qualify the same recipe in the same contest");
			Assert.isTrue(!recipeCopy.getNameUser().equals(rc.getNameUser()),
					"An user cannot qualify two recipes in the same contest");
		}

		recipeCopy.setContest(contest);
		recipeCopyService.save(recipeCopy);
		contest.addRecipeCopy(recipeCopy);
		contestService.save(contest);
	}

	public Collection<Recipe> recipesFollows() {
		Assert.isTrue(actorService.checkAuthority("USER")
				|| actorService.checkAuthority("NUTRITIONIST"));

		Collection<Recipe> result;
		SocialActor sa;
		List<Recipe> userRecipes;
		Authority user;

		result = new ArrayList<Recipe>();
		sa = socialActorService.findByPrincipal();
		userRecipes = new ArrayList<Recipe>();
		user = new Authority();
		user.setAuthority("USER");

		for (SocialActor sc : sa.getFollows()) {
			if (sc.getUserAccount().getAuthorities().contains(user)) {
				User u;

				u = userService.findByUserAccount(sc.getUserAccount());
				if (!u.getRecipes().isEmpty()) {

					userRecipes = (List<Recipe>) u.getRecipes();

					result.add(userRecipes.get(userRecipes.size() - 1));
				}

			}
		}

		return result;
	}

	public Integer findLikes(Recipe recipe) {
		Integer result;

		result = 0;

		for (LikeSA l : recipe.getLikesSA()) {
			if (l.isLikeSA()) {
				result++;
			}
		}

		return result;

	}

	public Integer findDislikes(Recipe recipe) {
		Integer result;

		result = 0;

		for (LikeSA l : recipe.getLikesSA()) {
			if (!l.isLikeSA()) {
				result++;
			}
		}

		return result;

	}

	public String createTicker() {
		String caracteresEspeciales;
		String result;
		List<Recipe> recipes;
		Random random;
		char[] r;
		int z;
		char c;

		caracteresEspeciales = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		result = "";
		recipes = (List<Recipe>) findAllRecipesGroupByCategory();
		random = new Random();
		result += Calendar.YEAR + Calendar.MONTH + Calendar.DAY_OF_MONTH;

		result += "-";

		for (int i = 0; i <= 3; i++) {
			r = new char[4];
			z = random.nextInt(caracteresEspeciales.length() - 1);
			c = caracteresEspeciales.charAt(z);
			r[i] = c;
			result += r[i];
		}

		for (Recipe recipe : recipes) {
			if (result.equals(recipe.getTicker())) {
				result = createTicker();
			}
		}
		return result;
	}

}
