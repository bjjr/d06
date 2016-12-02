package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Cook;
import domain.MasterClass;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class MasterClassServiceTest extends AbstractTest {

	// Service under test --------------------------

	@Autowired
	private MasterClassService masterClassService;

	@Autowired
	private CookService cookService;

	@Autowired
	private ActorService actorService;

	// Tests ---------------------------------------

	@Test
	public void testCreate1() {
		authenticate("cook1");
		Cook c;
		c = cookService.findByPrincipal();
		MasterClass m;
		m = masterClassService.create();
		Assert.isTrue(m.getCook().equals(c));
		Assert.isTrue(m.getTitle() == null);
		unauthenticate();
	}

	@Test
	public void testFindAllByCook() {
		authenticate("cook1");

		Collection<MasterClass> expected;
		expected = new ArrayList<>();

		expected.add(masterClassService.findOne(206));
		expected.add(masterClassService.findOne(208));
		expected.add(masterClassService.findOne(209));

		Collection<MasterClass> res;
		res = new ArrayList<>();

		res = masterClassService.findAllByCook();

		Assert.isTrue(expected.equals(res));

		unauthenticate();
	}

	@Test
	public void testFindAllForNonUsers() {
		System.out.println("------ TEST findAllForNonUsers ------\n");

		Collection<String> res;
		res = masterClassService.findAllForNonUsers();

		for (String s : res) {
			System.out.println(s + "\n");
		}
	}

	@Test
	public void testSave() {
		authenticate("cook1");

		MasterClass m;
		m = masterClassService.create();

		m.setTitle("title");
		m.setDescription("description");
		m.setPromoted(false);

		MasterClass saved;
		saved = masterClassService.save(m);
		masterClassService.flush();

		Assert.isTrue(masterClassService.exists(saved.getId()));
	}

	@Test
	public void testDelete() {
		authenticate("cook1");

		MasterClass m;
		m = masterClassService.findOne(206);

		Collection<Actor> attenders;
		attenders = m.getActors();

		List<Integer> numMsgs = new ArrayList<>();
		Integer i = 0;

		for (Actor a : attenders) {
			numMsgs.add(a.getReceivedMessages().size());
		}

		masterClassService.delete(m);

		for (Actor a : attenders) {
			Assert.isTrue(!a.getMasterClasses().contains(m));
			Assert.isTrue(a.getReceivedMessages().size() == (numMsgs.get(i) + 1));
			i++;
		}

		unauthenticate();
	}

	@Test
	public void testRegisterActor1() {
		authenticate("user1");

		MasterClass m;
		m = masterClassService.findOne(207);

		masterClassService.registerActor(m);

		Assert.isTrue(actorService.findByPrincipal().getMasterClasses()
				.contains(m));
		
		unauthenticate();
	}
	
	@Test
	public void testRegisterActor2() {
		authenticate("cook1");

		MasterClass m;
		m = masterClassService.findOne(207);

		masterClassService.registerActor(m);

		Assert.isTrue(actorService.findByPrincipal().getMasterClasses()
				.contains(m));
		
		unauthenticate();
	}
	
	@Test
	public void testFindAttenders() {
		MasterClass m;
		m = masterClassService.findOne(206);
		
		Collection<Actor> attenders;
		attenders = m.getActors();
		
		Assert.isTrue(masterClassService.findAttenders(m).equals(attenders));
	}
	
	@Test
	public void testFindNumPromotedMasterClasses() {
		authenticate("administrator1");
		
		Long expected;
		expected = 2L;		
		Long res;
		res = masterClassService.findNumPromotedMasterClasses();
		
		Assert.isTrue(expected.equals(res));
		
		unauthenticate();
	}
	
	@Test
	public void testFindMinNumMasterClassesPerCook() {
		authenticate("administrator1");
		
		Integer expected;
		expected = 0;		
		
		Integer res;
		res = masterClassService.findMinNumMasterClassesPerCook();
		
		Assert.isTrue(expected.equals(res));
		
		unauthenticate();
	}
	
	@Test
	public void testFindMaxNumMasterClassesPerCook() {
		authenticate("administrator1");
		
		Integer expected;
		expected = 3;
		
		Integer res;
		res = masterClassService.findMaxNumMasterClassesPerCook();	
		
		Assert.isTrue(expected.equals(res));
		
		unauthenticate();
	}
	
	@Test
	public void testFindAvgNumMasterClassesPerCook() {
		authenticate("administrator1");
		
		Double expected;
		expected = 1.3333;
		
		Double res;
		res = masterClassService.findAvgNumMasterClassesPerCook();	
		
		Assert.isTrue(expected.equals(res));
		
		unauthenticate();
	}
	
	@Test
	public void testFindStddevNumMasterClassesPerCook() {
		authenticate("administrator1");
		
		Double expected;
		expected = 1.2472;
		
		Double res;
		res = masterClassService.findStddevNumMasterClassesPerCook();
		
		Assert.isTrue(expected.equals(res));
		
		unauthenticate();
		
	}
	
	@Test
	public void testPromoteMasterClass() {
		authenticate("administrator1");
		
		MasterClass m;
		m = masterClassService.findOne(208);
		
		masterClassService.promoteMasterClass(m);
		
		Assert.isTrue(m.isPromoted() == true);
		
		unauthenticate();
	}
	
	@Test
	public void testDemoteMasterClass() {
		authenticate("administrator1");
		
		MasterClass m;
		m = masterClassService.findOne(206);
		
		masterClassService.demoteMasterClass(m);
		
		Assert.isTrue(m.isPromoted() == false);
		
		unauthenticate();
	}
	
}
