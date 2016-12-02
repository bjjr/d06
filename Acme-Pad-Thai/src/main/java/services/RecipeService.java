package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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

@Service
@Transactional
public class RecipeService {
	
	//Managed repository
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
	
	//Constructors
	public RecipeService(){
		super();
	}
	
	// Simple CRUD methods
	public Recipe create(){
		Assert.isTrue(actorService.checkAuthority("USER"));
		
		Recipe result;
		User owner;
		
		owner = userService.findByPrincipal();
		result = new Recipe();
		result.setUser(owner);
		userService.save(owner);
		
		return result;
	}
	
	public Recipe save(Recipe recipe){
		Assert.isTrue(actorService.checkAuthority("USER") || actorService.checkAuthority("NUTRITIONIST"));
		Assert.notNull(recipe);
		
		Recipe result;
		Date momentCreated;
		Date momentUpdated;
		
		momentCreated = new Date(System.currentTimeMillis()-1000);
		momentUpdated = new Date(System.currentTimeMillis()-1000);
		recipe.setMomentAuthored(momentCreated);
		recipe.setMomentLastUpdated(momentUpdated);
		
		result = recipeRepository.save(recipe);
		
		return result;
	}
	
	public void flush() {
		recipeRepository.flush();
	}
	
	public void delete(Recipe recipe){
		Assert.isTrue(actorService.checkAuthority("USER"));
		Assert.notNull(recipe);
		Assert.isTrue(recipe.getId()!=0);
		Assert.isTrue(recipeRepository.exists(recipe.getId()));
		
		User owner;
		Collection<Category> categories;
		
		owner = recipe.getUser();
		categories = categoryService.findAll();
		
		for(Step s : recipe.getSteps()){
			stepService.delete(s);
		}
		
		for(Quantity q : recipe.getQuantities()){
			quantityService.delete(q);
		}
		
		for(LikeSA l : recipe.getLikesSA()){
			likeSAService.delete(l);
		}
		
		for(Comment c : recipe.getComments()){
			commentService.delete(c);
		}
		
		for(Category c : categories){
			if(c.getRecipes().contains(recipe)){
				c.removeRecipe(recipe);
				categoryService.save(c);
			}
		}
		
		recipe.setUser(null);
		owner.removeRecipe(recipe);
		userService.save(owner);
		
		recipeRepository.delete(recipe);
			
	}
	
	//Other business methods
	public Double findMinRecipesUser(){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		
		Double result;
		
		result = recipeRepository.findMinRecipesUser();
		Assert.notNull(result);
		
		return result;
	}
	
	public Double findAvgRecipesUser(){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		
		Double result;
		
		result = recipeRepository.findAvgRecipesUser();
		Assert.notNull(result);
		
		return result;
	}
	
	public Double findMaxRecipesUser(){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		
		Double result;
		
		result = recipeRepository.findMaxRecipesUser();
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Recipe> findAllRecipesGroupByCategory(){
		Collection<Recipe> result;
		
		result = recipeRepository.findAllRecipesGroupByCategory();
		Assert.notNull(result);
		
		return result;
	}
	
	public Recipe findByKeyword(String keyword){
		Assert.notNull(keyword);
		
		Recipe result;
		
		result = recipeRepository.findByKeyword(keyword);
		Assert.notNull(result);
		
		return result;
		
	}
	
	public void qualifyRecipe(Recipe recipe, Contest contest){
		Assert.isTrue(actorService.checkAuthority("USER"));
		Assert.notNull(recipe);
		Assert.notNull(contest);
		
		int countLikes;
		int countDislikes;
		RecipeCopy recipeCopy;
		
		countLikes = 0;
		countDislikes = 0;
		recipeCopy = recipeCopyService.create();
		
		for(LikeSA l : recipe.getLikesSA()){
			if(l.isLikeSA()==true){
				countLikes++;
			}
			else{
				countDislikes++;
			}
		}
		
		User u = userService.findByPrincipal();
		Assert.isTrue(u.getRecipes().contains(recipe),"An user only could copy his recipes");

		Assert.isTrue(countLikes >=5 && countDislikes == 0);
		
		recipeCopy.setTicker(recipe.getTicker());
		recipeCopy.setTitle(recipe.getTitle());
		recipeCopy.setSummary(recipe.getSummary());
		recipeCopy.setMomentAuthored(recipe.getMomentAuthored());
		recipeCopy.setMomentLastUpdated(recipe.getMomentLastUpdated());
		recipeCopy.setPictures(recipe.getPictures());
		recipeCopy.setHints(recipe.getHints());
		recipeCopy.setWinner(false);
		recipeCopy.setNameUser(recipe.getUser().getName() + recipe.getUser().getSurname());
		recipeCopy.setLikesRC(countLikes);
		recipeCopy.setDislikesRC(countDislikes);
		recipeCopy.setContest(contest);
		
		recipeCopyService.save(recipeCopy);
		contestService.save(contest);
		
		
	}
	
	public Collection<Recipe> recipesFollows(){
		Assert.isTrue(actorService.checkAuthority("USER") || 
				actorService.checkAuthority("NUTRITIONIST"));
		
		Collection<Recipe> result;
		SocialActor sa;
		List<Recipe> userRecipes;
		
		result = new ArrayList<Recipe>();
		sa = socialActorService.findByPrincipal();
		userRecipes = new ArrayList<Recipe>();

		for(SocialActor sc : sa.getFollows()){
			if(userService.findByUserAccount(sc.getUserAccount()) != null){
				User u;
				
				u = userService.findByUserAccount(sc.getUserAccount());
				userRecipes = (List<Recipe>) u.getRecipes();
				
				result.add(userRecipes.get(userRecipes.size()-1));
			}
		}
		
		return result;
	}
	
	public Collection<LikeSA> findLikes(Recipe recipe){
		Collection<LikeSA> result;
		
		result = new ArrayList<LikeSA>();
		
		for(LikeSA l : recipe.getLikesSA()){
			if(l.isLikeSA()){
				result.add(l);
			}
		}
		
		return result;
		
	}
	
	public Collection<LikeSA> findDislikes(Recipe recipe){
		Collection<LikeSA> result;
		
		result = new ArrayList<LikeSA>();
		
		for(LikeSA l : recipe.getLikesSA()){
			if(!l.isLikeSA()){
				result.add(l);
			}
		}
		
		return result;
		
	}

}
