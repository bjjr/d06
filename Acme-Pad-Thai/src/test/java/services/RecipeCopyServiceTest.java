package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Contest;
import domain.LikeSA;
import domain.Recipe;
import domain.RecipeCopy;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class RecipeCopyServiceTest extends AbstractTest {

	// Service under test -------------------------
	@Autowired
	private RecipeCopyService recipeCopyService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private ContestService contestService;
	@Autowired
	private UserService userService;
	
	// Test ---------------------------------------
	@Test
	public void testCreateRecipeCopy() {
		RecipeCopy recipeCopy;
		
		authenticate("user1");
		recipeCopy = recipeCopyService.create();

		Assert.isTrue(recipeCopy.getContest()==null);
		Assert.isTrue(recipeCopy.getDislikesRC() == 0);
		Assert.isTrue(recipeCopy.getHints()==null);
		Assert.isTrue(recipeCopy.getLikesRC() == 0);
		Assert.isTrue(recipeCopy.getMomentAuthored() == null);
		Assert.isTrue(recipeCopy.getMomentLastUpdated()== null);
		Assert.isTrue(recipeCopy.getNameUser()== null);
		Assert.isTrue(recipeCopy.getPictures()== null);
		Assert.isTrue(recipeCopy.getSummary()== null);
		Assert.isTrue(recipeCopy.getTicker()== null);
		Assert.isTrue(recipeCopy.getTitle()== null);
		
		unauthenticate();
	}

	// Test ---------------------------------------
	@Test
	public void testSaveRecipeCopy() {
		authenticate("user1");
		Contest contest= contestService.findOne(255);
		Recipe recipe= recipeService.findByKeyword("Recipe1");
		
		
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
		
		RecipeCopy saved = recipeCopyService.save(recipeCopy);
		contestService.save(contest);
		
		Assert.isTrue(recipeCopyService.exists(saved.getId()));

		unauthenticate();
	}


}
