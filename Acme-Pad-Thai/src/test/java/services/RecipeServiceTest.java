package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.Contest;
import domain.LikeSA;
import domain.Recipe;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class RecipeServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private RecipeService recipeService;
	
	//Supported services
	@Autowired
	private ContestService contestService;

	// Tests

	@Test
	public void testCreateRecipe() {
		Recipe recipe;

		super.authenticate("User4");

		recipe = recipeService.create();

		super.authenticate(null);

		System.out.println("Recipe" + recipe.getId() + "created");
	}

	@Test
	public void testSaveRecipe() {
		Recipe recipe, saved;

		super.authenticate("User1");

		recipe = recipeService.findByKeyword("123456-abCD");
		saved = recipeService.save(recipe);
		recipeService.flush();

		super.authenticate(null);

		System.out.println("Recipe" + saved.getId() + "saved");
	}

	@Test
	public void testDeleteRecipe() {
		Recipe recipe;

		super.authenticate("User1");

		recipe = recipeService.findByKeyword("123456-abCD");

		recipeService.delete(recipe);

		super.authenticate(null);

		System.out.println("Recipe deleted");
	}
	
	@Test
	public void testFindMinRecipesUser(){
		Double d;
		
		super.authenticate("Administrator1");
		
		d = recipeService.findMinRecipesUser();
		
		super.authenticate(null);
		
		System.out.println("The minimun is"+ d);
	}
	
	@Test
	public void testFindAvgRecipesUser(){
		Double d;
		
		super.authenticate("Administrator1");
		
		d = recipeService.findAvgRecipesUser();
		
		super.authenticate(null);
		
		System.out.println("The average is"+ d);
	}
	
	@Test
	public void testFindMaxRecipesUser(){
		Double d;
		
		super.authenticate("Administrator1");
		
		d = recipeService.findMaxRecipesUser();
		
		super.authenticate(null);
		
		System.out.println("The maximun is"+ d);
	}
	
	@Test
	public void testFindAllRecipesGroupByCategory(){
		Collection<Recipe> recipes;
		
		recipes = recipeService.findAllRecipesGroupByCategory();
		
		for(Recipe r : recipes){
			System.out.println("Recipe" + r.getId() + "found");
		}
	}
	
	@Test
	public void testFindRecipeByKeyword(){
		Recipe recipe;
		
		recipe = recipeService.findByKeyword("123456-abCD");
		
		System.out.println("Recipe" + recipe.getId() + "found");
	}
	
	@Test
	public void testQualifyRecipe(){
		Recipe recipe;
		Contest contest;
		
		super.authenticate("User4");
		
		recipe = recipeService.findByKeyword("152677-gHsd");
		contest = contestService.findOne(254);
		
		recipeService.qualifyRecipe(recipe, contest);
		
		System.out.println("The recipe has been qualified for the contest");
		
		
	}
	
	@Test
	public void testRecipesFollows(){
		Collection<Recipe> recipes;
		
		super.authenticate("User1");
		
		recipes = recipeService.recipesFollows();
		
		super.authenticate(null);
		
		for(Recipe r : recipes){
			System.out.println("Recipe" + r.getId() + "found");
		}
	}
	
	@Test
	public void testFindLikes(){
		Collection<LikeSA> likes;
		Recipe recipe;
		
		recipe = recipeService.findByKeyword("152677-gHsd");
		likes = recipeService.findLikes(recipe);
		
		System.out.println("This recipe has " + likes.size() + " likes");
	}
	
	@Test
	public void testFindDislikes(){
		Collection<LikeSA> dislikes;
		Recipe recipe;
		
		recipe = recipeService.findByKeyword("152677-gHsd");
		dislikes = recipeService.findDislikes(recipe);
		
		System.out.println("This recipe has " + dislikes.size() + " dislikes");
	}

}
