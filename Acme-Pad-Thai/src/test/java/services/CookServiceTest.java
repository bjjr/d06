package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Cook;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class CookServiceTest extends AbstractTest {
	
	//Service under test------------------------
	
	@Autowired
	private CookService cookService;
	
	// Tests -----------------------------------
	
	@Test
	public void testCreate() {
		authenticate("administrator1");
		
		Cook cook;
		cook = cookService.create();
		
		Assert.isNull(cook.getName());
		Assert.isNull(cook.getSurname());
		Assert.isNull(cook.getEmail());
		Assert.isNull(cook.getPhone());
		Assert.isNull(cook.getPostalAddress());
		Assert.notNull(cook.getFolders());
		Assert.notNull(cook.getUserAccount());
		
		unauthenticate();
	}
	
	@Test
	public void testSave1() {
		authenticate("administrator1");
		
		Cook cook;
		cook = cookService.create();
		
		String name = "cookname";
		String surname = "cooksurname";
		String email = "cook@email.com";
		
		cook.setName(name);
		cook.setSurname(surname);
		cook.setEmail(email);
		
		Cook saved;
		saved = cookService.save(cook);
		cookService.flush();
		
		Assert.isTrue(saved.getId() != 0);
		Assert.isTrue(saved.getName().equals(name));
		Assert.isTrue(saved.getSurname().equals(surname));
		Assert.isTrue(saved.getEmail().equals(email));
		
		unauthenticate();
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void testSave2() {
		authenticate("administrator1");
		
		Cook cook;
		cook = cookService.create();
		
		String name = "cookname";
		String surname = "cooksurname";
		
		cook.setName(name);
		cook.setSurname(surname);
		
		cookService.save(cook);
		cookService.flush();
		
		unauthenticate();
	}
	
	@Test
	public void testFindAllOrderByNumPromotedMasterClasses() {
		authenticate("administrator1");
		
		Collection<Cook> cooks;
		cooks = cookService.findAllOrderByNumPromotedMasterClasses();
		
		Cook c1 = cookService.findOne(65);
		Cook c2 = cookService.findOne(66);
		
		Assert.isTrue(cooks.contains(c1));
		Assert.isTrue(cooks.contains(c2));
		
		unauthenticate();
	}
	
	@Test
	public void testFindMinTeachedMasterClasses() {
		authenticate("administrator1");
		
		Integer res;
		res = cookService.findMinTeachedMasterClasses();
		
		Assert.isTrue(res == 0);
		
		unauthenticate();
	}
	
	@Test
	public void testFindAvgTeachedMasterClasses() {
		authenticate("administrator1");
		
		Double res;
		res = cookService.findAvgTeachedMasterClasses();
		
		Assert.isTrue(res == 1.3333);
		
		unauthenticate();
	}
	
	@Test
	public void testFindMaxTeachedMasterClasses() {
		authenticate("administrator1");
		
		Integer res;
		res = cookService.findMaxTeachedMasterClasses();
		
		Assert.isTrue(res == 3);
		
		unauthenticate();
	}
	
	@Test
	public void testStddevTeachedMasterClasses() {
		authenticate("administrator1");
		
		Double res;
		res = cookService.findStddevTeachedMasterClasses();
		
		Assert.isTrue(res == 1.2472);
		
		unauthenticate();
	}
	
	@Test
	public void testFindAvgPromotedMasterClasses() {
		authenticate("administrator1");
		
		Double res;
		res = cookService.findAvgPromotedMasterClasses();
		
		Assert.isTrue(res == 0.66667);
		
		unauthenticate();
	}
	
	@Test
	public void testFindAvgDemotedMasterClasses() {
		authenticate("administrator1");
		
		Double res;
		res = cookService.findAvgDemotedMasterClasses();
		
		Assert.isTrue(res == 0.66667);
		
		unauthenticate();
	}
}
