package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.Quantity;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class QuantityServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private QuantityService quantityService;
	
	//Tests
	
	@Test
	public void testCreateQuantity(){
		Quantity quantity;
		
		quantity = quantityService.create();
		
		System.out.println("Quantity" + quantity.getId() + "created");
	}
	
	@Test
	public void testSaveQuantity(){
		Quantity quantity, saved;
		
		quantity = quantityService.findOne(155);
		saved = quantityService.save(quantity);
		quantityService.flush();
		
		System.out.println("Quantity" + saved.getId() + "saved");
	}
	
	@Test
	public void testDeleteQuantity(){
		Quantity quantity;
		
		quantity = quantityService.findOne(155);
		
		quantityService.delete(quantity);
		
		System.out.println("Quantity deleted");
	}
	
	@Test
	public void testFindOneQuantity(){
		Quantity quantity;
		
		quantity = quantityService.findOne(155);
		
		System.out.println("Quantity" + quantity.getId() + "found");
	}

}
