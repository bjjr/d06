package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Nutritionist;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class NutritionistServiceTest extends AbstractTest{
	
	// Service under test --------------------------------
	
	@Autowired
	private NutritionistService nutritionistService;
	
	// Tests ---------------------------------------------
	
	@Test
	public void testCreateNutritionist(){
		Nutritionist res;
		
		res = nutritionistService.create();
		
		System.out.println("Nutritionist " + res.getId() + " created");
	}
	
	@Test
	public void testFindOneNutritionist(){
		authenticate("administrator1");
		
		Nutritionist nutritionist;
		int id = 52;
		
		nutritionist = nutritionistService.findOne(id);
		System.out.println("Nutritionist whose id is " + id + " found: " + nutritionist);
		
		unauthenticate();
	}
	
	@Test
	public void testFindAllNutritionist(){
		authenticate("administrator1");
		
		Collection<Nutritionist> nutritionists;
		
		nutritionists = nutritionistService.findAll();
		
		System.out.println(nutritionists.size() + " nutritionists found");
		
		unauthenticate();
	}
	
	@Test
	public void testSaveNutritionist(){
		Nutritionist nutritionist;
		Nutritionist saved;
		Collection<Nutritionist> nutritionists;
		
		nutritionist = nutritionistService.findOne(52);
		
		saved = nutritionistService.save(nutritionist);
		nutritionistService.flush();
		nutritionists = nutritionistService.findAll();
		
		Assert.isTrue(nutritionists.contains(saved));
		
		System.out.println("Nutritionist saved");
	}

}
