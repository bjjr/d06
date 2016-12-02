package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.LikeSA;
import domain.Recipe;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class LikeSAServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private LikeSAService likeSAService;

	// Supporting services
	@Autowired
	private RecipeService recipeService;

	// Tests
	@Test
	public void testCreateLikeSA() {
		LikeSA likeSA;
		Recipe recipe;

		super.authenticate("Nutritionist1");

		recipe = recipeService.findByKeyword("156897-TBtJ"); 
		likeSA = likeSAService.create(recipe);

		super.authenticate(null);

		System.out.println("LikeSA" + likeSA.getId() + "created");
	}

	@Test
	public void testFindOneLikeSA() {
		LikeSA likeSA;

		likeSA = likeSAService.findOne(120);

		System.out.println("LikeSA" + likeSA.getId() + "found");
	}

	@Test
	public void testSaveLikeSA() {
		LikeSA likeSA, saved;

		super.authenticate("Nutritionist2");

		likeSA = likeSAService.findOne(116);
		saved = likeSAService.save(likeSA);
		likeSAService.flush();

		super.authenticate(null);

		System.out.println("LikeSA" + saved.getId() + "saved");
	}

	@Test
	public void testDeleteLikeSA() {
		LikeSA likeSA;

		likeSA = likeSAService.findOne(116);

		likeSAService.delete(likeSA);

		System.out.println("LikeSA deleted");
	}

}
