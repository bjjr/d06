package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.Step;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class StepServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private StepService stepService;
	
	//Tests
	
	@Test
	public void testCreateStep(){
		Step step;
		
		step = stepService.create();
		
		System.out.println("Step" + step.getId() + "created");
	}
	
	@Test
	public void testSaveStep(){
		Step step, saved;
		
		step = stepService.findOne(77);
		saved = stepService.save(step);
		stepService.flush();
		
		System.out.println("Step" + saved.getId() + "saved");
	}
	
	@Test
	public void testDeleteStep(){
		Step step;
		
		step = stepService.findOne(77);
		
		stepService.delete(step);
		
		System.out.println("Step deleted");
	}
	
	@Test
	public void testFindOneStep(){
		Step step;
		
		step = stepService.findOne(77);
		
		System.out.println("Step" + step.getId() + "found");
	}
	
	@Test
	public void testFindAvgStepsRecipe(){
		Double d;
		
		super.authenticate("Administrator1");
		
		d = stepService.findAvgStepsRecipe();
		
		super.authenticate(null);
		
		System.out.println("The average is" + d);
	}
	
	@Test
	public void testFindStandardDeviationStepsRecipe(){
		Double d;
		
		super.authenticate("Administrator1");
		
		d = stepService.findStandardDeviationStepsRecipe();
		
		super.authenticate(null);
		
		System.out.println("The standard deviation is" + d);
	}

}
