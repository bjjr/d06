package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.Nutritionist;
import domain.SocialActor;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class SocialActorServiceTest extends AbstractTest{
	
	// Service under test --------------------------------
	
	@Autowired
	private SocialActorService socialActorService;
	
	@Autowired
	private NutritionistService nutritionistService;
	
	// Tests ---------------------------------------------
	
	@Test
	public void testFollowSocialActor(){
		authenticate("user1");
		
		SocialActor current;
		Nutritionist nutritionist1;
		
		nutritionist1 = nutritionistService.findOne(54);
		current = socialActorService.findByPrincipal();
		socialActorService.followSocialActor(current, nutritionist1);
		
		System.out.println("------- TEST FOLLOW SOCIALACTOR -------");
		
		if(current.getFollows().contains(nutritionist1)){
			System.out.println(current.getName() + " follows " + nutritionist1.getName() + "\n");
		}
		
		unauthenticate();
	}
	
	@Test
	public void testUnFollowSocialActor(){
		authenticate("user1");
		
		SocialActor current;
		Nutritionist nutritionist2;
		
		nutritionist2 = nutritionistService.findOne(52);
		current = socialActorService.findByPrincipal();
		socialActorService.unfollowSocialActor(current, nutritionist2);
		
		System.out.println("------- TEST UNFOLLOW SOCIALACTOR -------");
		
		if(!current.getFollows().contains(nutritionist2)){
			System.out.println(current.getName() + " unfollows " + nutritionist2.getName() + "\n");
		}
		
		unauthenticate();
	}
	
}
