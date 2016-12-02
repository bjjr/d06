package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Curriculum;
import domain.Endorser;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class EndorserServiceTest extends AbstractTest{
	
	// Service under test --------------------------------
	
	@Autowired
	private EndorserService endorserService;
	
	// Tests ---------------------------------------------
		
	@Test
	public void testCreateEndorser(){
		authenticate("administrator1");
			
		Endorser res;
			
		res = endorserService.create();
		
		Assert.isTrue(res.getName() == null);
		Assert.isTrue(res.getHomepage() == null);
		Assert.isTrue(res.getCurricula() == null);
			
		unauthenticate();
	}
		
	@Test
	public void testFindOneEndorser(){
		authenticate("administrator1");
			
		Endorser endorser;
		int id = 44;

		endorser = endorserService.findOne(id);
		System.out.println("Endorser whose id is " + id + " found: " + endorser);
		
		unauthenticate();
	}
		
	@Test
	public void testFindAllEndorser(){
		authenticate("administrator1");
		
		Collection<Endorser> endorsers;
			
		endorsers = endorserService.findAll();
			
		System.out.println(endorsers.size() + " endorsers found");
			
		unauthenticate();
	}
		
	@Test
	public void testSaveEndorser(){
		authenticate("administrator1");
		
		Endorser endorser;
		Endorser saved;
		Collection<Endorser> endorsers;
		Collection<Curriculum> curricula;
		
		endorser = endorserService.create();
		curricula = new ArrayList<Curriculum>();
			
		endorser.setName("NameEndorserTest");
		endorser.setHomepage("http://www.homePageEndorserTest.com");
		endorser.setCurricula(curricula);
			
		saved = endorserService.save(endorser);
		endorserService.flush();
		
		endorsers = endorserService.findAll();
		
		Assert.isTrue(endorsers.contains(saved));
			
		System.out.println("Endorser saved");
			
		unauthenticate();
	}
		
	@Test
	public void testDeleteEndorser(){
		authenticate("administrator1");
			
		Endorser endorser;

		endorser = endorserService.findOne(44);
		
		endorserService.delete(endorser);
		
		System.out.println("Endorser deleted correctly");
			
		unauthenticate();
	}
}
