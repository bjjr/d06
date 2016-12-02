package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccount;
import security.UserAccountService;

import utilities.AbstractTest;
import domain.Actor;
import domain.Cook;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class ActorServiceTest extends AbstractTest {

	// Service under test -----------------------------

	@Autowired
	private ActorService actorService;

	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private CookService cookService;

	// Tests ------------------------------------------

	/*
	 * Simulate the login of nutritionist1 and check that the principal it's
	 * that nutritionist by comparing the name.
	 */

	@Test
	public void testFindByPrincipal() {
		authenticate("nutritionist1");
		Actor a;
		a = actorService.findByPrincipal();
		Assert.isTrue(a.getName().equals("nameNutritionist1"));
		unauthenticate();
	}

	/*
	 * Simulate the login of the administrator and check if
	 * checkAuthority("ADMINISTRATOR") returns true
	 */

	@Test
	public void testCheckAuthority1() {
		authenticate("administrator1");
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		unauthenticate();
	}

	/*
	 * Simulate that a non registered person is using the system
	 */

	@Test
	public void testCheckAuthority2() {
		Assert.isTrue(!(actorService.checkAuthority("ADMINISTRATOR")
				|| actorService.checkAuthority("USER")
				|| actorService.checkAuthority("NUTRITIONIST")
				|| actorService.checkAuthority("SPONSOR") 
				|| actorService.checkAuthority("COOK")));
	}

	/*
	 * Simulate the login of the administrator and check if
	 * checkAuthority("COOK") returns false
	 */

	@Test
	public void testCheckAuthority3() {
		authenticate("administrator1");
		Assert.isTrue(!actorService.checkAuthority("COOK"));
		unauthenticate();
	}

	/*
	 * Given the ids of an actor an his user account check if
	 * findByUserAccountId returns the same actor
	 */

	@Test
	public void testFindByUserAccount() {
		UserAccount ua;
		ua = userAccountService.findByUsername("cook1");
		Actor a;
		a = actorService.findOne(65);
		Assert.isTrue(actorService.findByUserAccount(ua).equals(a));
	}

	@Test
	public void testSave() {
		authenticate("cook1");
		
		Cook c;
		c = cookService.findByPrincipal();
		
		c.setName("newCook1");
		
		Actor saved;
		saved = actorService.save(c);
		actorService.flush();
		
		Assert.isTrue(saved.getName().equals("newCook1"));
		
		unauthenticate();
	}
}
