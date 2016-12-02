package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.Unit;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class UnitServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private UnitService unitService;
	
	//Tests
	
	@Test
	public void testCreateUnit(){
		Unit unit;
		
		super.authenticate("Administrator1");
		
		unit = unitService.create();
		
		super.authenticate(null);
		
		System.out.println("Unit" + unit.getId() + "created");
	}
	
	@Test
	public void testSaveUnit(){
		Unit unit, saved;
		
		super.authenticate("Administrator1");
		
		unit = unitService.findOne(34);
		saved = unitService.save(unit);
		unitService.flush();
		
		super.authenticate(null);
		
		System.out.println("Unit" + saved.getId() + "saved");
	}
	
	@Test
	public void testFindOneUnit(){
		Unit unit;
		
		unit = unitService.findOne(34);
		
		System.out.println("Unit" + unit.getId() + "found");
	}

}
