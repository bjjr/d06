package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.SocialIdentity;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class SocialIdentityServiceTest extends AbstractTest{
	
	// Service under test --------------------------------
	
	@Autowired
	private SocialIdentityService socialIdentityService;
	
	@Autowired
	private ActorService actorService;
			
	// Tests ---------------------------------------------
			
	@Test
	public void testCreateSocialIdentity(){
		authenticate("user1");
				
		SocialIdentity res;
				
		res = socialIdentityService.create();
				
		Assert.isTrue(res.getLink() == null);
		Assert.isTrue(res.getNameSN() == null);
		Assert.isTrue(res.getNick() == null);
		Assert.isTrue(res.getPicture() == null);
		Assert.isTrue(res.getActor() == null);
				
		unauthenticate();
	}
			
	@Test
	public void testFindOneSocialIdentity(){
		authenticate("user1");
				
		SocialIdentity socialIdentity;
		int id = 242;

		socialIdentity = socialIdentityService.findOne(id);
		System.out.println("SocialIdentity whose id is " + id + " found: " + socialIdentity);
				
		unauthenticate();
	}
			
	@Test
	public void testFindAllSocialIdentity(){
		authenticate("user1");
		
		Collection<SocialIdentity> socialIdentities;
				
		socialIdentities = socialIdentityService.findAll();
				
		System.out.println(socialIdentities.size() + " social identities found");
				
		unauthenticate();
	}
			
	@Test
	public void testSaveSocialIdentity(){
		authenticate("user1");
			
		SocialIdentity socialIdentity;
		SocialIdentity saved;
		Collection<SocialIdentity> socialIdentities;
		Actor actor;
		
		actor = actorService.findByPrincipal();
		socialIdentity = socialIdentityService.create();
				
		socialIdentity.setLink("http://www.linkTest.com");
		socialIdentity.setNameSN("NameSNTest");
		socialIdentity.setNick("NickTest");
		socialIdentity.setPicture("http://www.pictureTest.com");
		socialIdentity.setActor(actor);
				
		saved = socialIdentityService.save(socialIdentity);
		socialIdentityService.flush();
		socialIdentities = socialIdentityService.findAll();
				
		Assert.isTrue(socialIdentities.contains(saved));
				
		System.out.println("SocialIdentity saved");
				
		unauthenticate();
	}
			
	@Test
	public void testDeleteSocialIdentity(){
		authenticate("user1");
				
		SocialIdentity socialIdentity;

		socialIdentity = socialIdentityService.findOne(242);
				
		socialIdentityService.delete(socialIdentity);
				
		System.out.println("SocialIdentity deleted correctly");
				
		unauthenticate();
	}
}
